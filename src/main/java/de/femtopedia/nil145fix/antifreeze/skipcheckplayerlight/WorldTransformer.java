package de.femtopedia.nil145fix.antifreeze.skipcheckplayerlight;

import de.femtopedia.nil145fix.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.World")
public class WorldTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("setActivePlayerChunksAndCheckLight()V")
    public void skipPlayerCheckLightSection(PatchContext ctx) {
        LabelNode Lskip = new LabelNode();

        ctx.search(
                LDC("playerCheckLight"),
                INVOKEVIRTUAL("net.minecraft.src.Profiler", "startSection", "(Ljava/lang/String;)V")
        ).jumpAfter();
        ctx.add(
                GOTO(Lskip)
        );

        ctx.search(
                ALOAD(0),
                GETFIELD("net/minecraft/src/World", "theProfiler", "Lnet/minecraft/src/Profiler;"),
                INVOKEVIRTUAL("net.minecraft.src.Profiler", "endSection", "()V")
        ).jumpBefore();
        ctx.add(
                Lskip
        );
    }

}
