package de.femtopedia.nil125fix.morecreepsandweirdos.fixcavemanachievements;

import de.femtopedia.nil125fix.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("CREEPSEntityCaveman")
public class CREEPSEntityCavemanTransformer extends MiniPlusTransformer {

    @Patch.Method("a(Lnet/minecraft/src/DamageSource;I)Z")
    public void fixAchievements(PatchContext ctx) {
        ctx.jumpToLastReturn();

        // Fix 10 achievement
        PatchContext.SearchResult res = ctx.searchBackward(
                GETSTATIC("mod_MoreCreepsAndWeirdos", "achieve5cavemanice", "Lnet/minecraft/src/Achievement;")
        );

        LabelNode Lskip = new LabelNode();

        res.jumpBefore();
        ctx.add(
                GETSTATIC("mod_MoreCreepsAndWeirdos", "achieve10cavemanice", "Lnet/minecraft/src/Achievement;"),
                GOTO(Lskip)
        );

        res.jumpAfter();
        ctx.add(
                Lskip
        );

        // Fix 5 achievement
        res = ctx.searchBackward(
                GETSTATIC("mod_MoreCreepsAndWeirdos", "achieve1cavemanice", "Lnet/minecraft/src/Achievement;")
        );

        Lskip = new LabelNode();

        res.jumpBefore();
        ctx.add(
                GETSTATIC("mod_MoreCreepsAndWeirdos", "achieve5cavemanice", "Lnet/minecraft/src/Achievement;"),
                GOTO(Lskip)
        );

        res.jumpAfter();
        ctx.add(
                Lskip
        );
    }

}
