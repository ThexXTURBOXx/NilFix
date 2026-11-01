package de.femtopedia.nil125fix.infernalmobs.fixselfdamage;

import de.femtopedia.nil125fix.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("atomicstryker.infernalmobs.mods.MM_Vengeance")
public class MM_VengeanceTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("onHurt(Lnet/minecraft/src/DamageSource;I)I")
    public void fixSelfDamage(PatchContext ctx) {
        LabelNode Lskip = new LabelNode();

        ctx.search(
                IFNULL(null)
        ).jumpAfter();
        ctx.add(
                ALOAD(1),
                INVOKEVIRTUAL("net/minecraft/src/DamageSource", "getEntity",
                        "()Lnet/minecraft/src/Entity;"),
                ALOAD(0),
                GETFIELD("atomicstryker/infernalmobs/mods/MM_Vengeance", "mob",
                        "Lnet/minecraft/src/EntityLiving;"),
                INVOKEVIRTUAL("net/minecraft/src/Entity", "isEntityEqual",
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
