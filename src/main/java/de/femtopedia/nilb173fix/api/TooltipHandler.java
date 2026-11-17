package de.femtopedia.nilb173fix.api;

import java.util.List;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.ItemStack;

public interface TooltipHandler {

    List<String> handleTooltipFirst(GuiContainer gui, int mouseX, int mouseY, List<String> tooltip);

    List<String> handleItemTooltip(GuiContainer gui, ItemStack stack, List<String> tooltip);

}
