package de.femtopedia.nil145fix.vanilla.fixtheendachievement;

import de.femtopedia.nil145fix.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.EntityPlayerMP")
public class EntityPlayerMPTransformer extends MiniPlusTransformer {

    @Patch.Method("travelToDimension(I)V")
    public void fixTheEndAchievement(PatchContext ctx) {
        ctx.search(
                GETFIELD("net/minecraft/src/EntityPlayerMP", "dimension", "I")
        ).next().jumpAfter();
        ctx.add(
                ICONST_1(),
                IADD()
        );

        ctx.search(
                ILOAD(1)
        ).jumpAfter();
        ctx.add(
                ICONST_1(),
                ISUB()
        );
    }

}
