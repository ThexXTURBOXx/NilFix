package de.femtopedia.nil147fix.infernalmobs.fixselfdamage;

import de.femtopedia.nil147fix.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("atomicstryker.infernalmobs.common.mods.MM_Vengeance")
public class MM_VengeanceTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("onHurt(Lnet/minecraft/src/EntityLiving;Lnet/minecraft/src/DamageSource;I)I")
    public void fixSelfDamage(PatchContext ctx) {
        LabelNode Lskip = new LabelNode();

        ctx.search(
                IFNULL(null)
        ).jumpAfter();
        ctx.add(
                ALOAD(1),
                ALOAD(0),
                GETFIELD("atomicstryker/infernalmobs/common/mods/MM_Vengeance", "mob",
                        "Lnet/minecraft/src/EntityLiving;"),
                INVOKEVIRTUAL("net/minecraft/src/EntityLiving", "isEntityEqual",
                        "(Lnet/minecraft/src/Entity;)Z"),
                IFNE(Lskip)
        );

        ctx.search(
                POP()
        ).jumpAfter();
        ctx.add(
                Lskip
        );
    }

}
