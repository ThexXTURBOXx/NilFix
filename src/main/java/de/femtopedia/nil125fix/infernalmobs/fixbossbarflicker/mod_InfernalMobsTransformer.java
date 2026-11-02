package de.femtopedia.nil125fix.infernalmobs.fixbossbarflicker;

import de.femtopedia.nil125fix.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("atomicstryker.infernalmobs.mod_InfernalMobs")
public class mod_InfernalMobsTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("getEntityCrosshairOver(FLnet/minecraft/client/Minecraft;)Lnet/minecraft/src/Entity;")
    public void saveRaytraceResultInOwnVar(PatchContext ctx) {
        while (true) {
            PatchContext.SearchResult res = ctx.search(
                    PUTFIELD("net/minecraft/client/Minecraft", "objectMouseOver",
                            "Lnet/minecraft/src/MovingObjectPosition;")
            );
            if (!res.isSuccessful()) break;

            LabelNode Lskip = new LabelNode();

            res.jumpBefore();
            ctx.add(
                    PUTFIELD("net/minecraft/client/Minecraft", "nilfixInfMobsMOP",
                            "Lnet/minecraft/src/MovingObjectPosition;"),
                    GOTO(Lskip)
            );

            res.jumpAfter();
            ctx.add(
                    Lskip
            );
        }

        while (true) {
            PatchContext.SearchResult res = ctx.search(
                    GETFIELD("net/minecraft/client/Minecraft", "objectMouseOver",
                            "Lnet/minecraft/src/MovingObjectPosition;")
            );
            if (!res.isSuccessful()) break;

            LabelNode Lskip = new LabelNode();

            res.jumpBefore();
            ctx.add(
                    GETFIELD("net/minecraft/client/Minecraft", "nilfixInfMobsMOP",
                            "Lnet/minecraft/src/MovingObjectPosition;"),
                    GOTO(Lskip)
            );

            res.jumpAfter();
            ctx.add(
                    Lskip
            );
        }
    }

}
