package de.femtopedia.nilb173fix.vanilla.tooltipapi;

import de.femtopedia.nilb173fix.MiniPlusTransformer;
import de.femtopedia.nilb173fix.api.TooltipAPI;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.Slot;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.GuiContainer")
public class GuiContainerTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("drawScreen(IIF)V")
    public void drawTooltip(PatchContext ctx) {
        LabelNode Lskip = new LabelNode();

        ctx.search(
                LDC(32826),
                INVOKESTATIC("org/lwjgl/opengl/GL11", "glDisable", "(I)V")
        ).jumpBefore();
        ctx.add(
                ALOAD(0),
                ALOAD(6),
                ILOAD(1),
                ILOAD(2),
                INVOKESTATIC(hooks(), "drawTooltip",
                        "(Lnet/minecraft/src/GuiContainer;Lnet/minecraft/src/Slot;II)V"),
                GOTO(Lskip)
        );

        ctx.search(
                INVOKESTATIC("org/lwjgl/opengl/GL11", "glPopMatrix", "()V")
        ).jumpBefore();
        ctx.add(
                Lskip
        );
    }

    public static class Hooks {

        public static void drawTooltip(GuiContainer gui, Slot slot, int mouseX, int mouseY) {
            TooltipAPI.instance().renderToolTips(gui, slot, mouseX, mouseY);
        }

    }

}
