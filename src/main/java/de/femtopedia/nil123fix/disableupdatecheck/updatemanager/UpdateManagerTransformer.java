package de.femtopedia.nil123fix.disableupdatecheck.updatemanager;

import de.femtopedia.nil123fix.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("vazkii.um.UpdateManager")
public class UpdateManagerTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("isOnline()Z")
    public void shortCircuit(PatchContext ctx) {
        ctx.jumpToStart();

        ctx.add(
                ICONST_0(),
                IRETURN()
        );
    }

}
