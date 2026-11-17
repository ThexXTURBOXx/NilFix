package de.femtopedia.nilb173fix.api;

import de.femtopedia.nilb173fix.PlusTransformer.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Slot;
import net.minecraft.src.StringTranslate;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TooltipAPI {

    public static int FONT_HEIGHT = 9;

    private static final TooltipAPI INSTANCE = new TooltipAPI();

    private final List<TooltipHandler> tooltipHandlers = new ArrayList<>();

    public static TooltipAPI instance() {
        return INSTANCE;
    }

    public void registerHandler(TooltipHandler handler) {
        tooltipHandlers.add(handler);
    }

    public void renderToolTips(GuiContainer gui, Slot slot, int mouseX, int mouseY) {
        List<String> tooltip = new LinkedList<>();

        for (TooltipHandler handler : tooltipHandlers) {
            tooltip = handler.handleTooltipFirst(gui, mouseX, mouseY, tooltip);
        }

        if (tooltip.isEmpty() && slot != null) {
            ItemStack stack = slot.getStack();
            if (stack != null) {
                tooltip = itemDisplayNameMultiline(stack, gui, true);
            }
        }

        this.drawMultilineTip(gui, slot, mouseX, mouseY, tooltip);
    }

    public void drawMultilineTip(GuiContainer gui, Slot slot, int x, int y, List<String> list) {
        InventoryPlayer inv = ((Minecraft) Utils.getMinecraftInstance()).thePlayer.inventory;
        int guiOffX = (gui.width - gui.xSize) / 2;
        int guiOffY = (gui.height - gui.ySize) / 2;

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        gui.drawGuiContainerForegroundLayer();
        if (inv.getItemStack() == null && slot != null && slot.getHasStack()) {
            int maxWidth = 0;
            for (String str : list) {
                if (!str.isEmpty()) {
                    maxWidth = Math.max(maxWidth, gui.fontRenderer.getStringWidth(str));
                }
            }

            int stride = FONT_HEIGHT + 1;
            int tx = x - guiOffX + 12;
            int ty = y - guiOffY - 12;
            gui.drawGradientRect(tx - 3, ty - 3, tx + maxWidth + 3, ty + stride * list.size() + 2,
                    0xC0000000, 0xC0000000);

            for (String str : list) {
                if (!str.isEmpty()) {
                    gui.fontRenderer.drawStringWithShadow(str, tx, ty, 0xFFFFFFFF);
                    ty += stride;
                }
            }
        }
    }

    public List<String> itemDisplayNameMultiline(ItemStack itemstack, GuiContainer gui,
                                                 boolean includeHandlers) {
        List<String> namelist = new ArrayList<>();

        try {
            String name = (StringTranslate.getInstance().translateNamedKey(itemstack.getItemName())).trim();
            namelist.add(name);
        } catch (Throwable ignored) {
        }

        if (namelist.isEmpty()) {
            namelist.add("Unnamed");
        }

        if (namelist.get(0) == null || namelist.get(0).isEmpty()) {
            namelist.set(0, "Unnamed");
        }

        if (includeHandlers) {
            for (TooltipHandler handler : tooltipHandlers) {
                namelist = handler.handleItemTooltip(gui, itemstack, namelist);
            }
        }

        namelist.set(0, "§f" + namelist.get(0));

        for (int i = 1; i < namelist.size(); ++i) {
            namelist.set(i, "§7" + namelist.get(i));
        }

        return namelist;
    }

}
