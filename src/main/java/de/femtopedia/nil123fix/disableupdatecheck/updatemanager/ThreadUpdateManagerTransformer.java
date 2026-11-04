package de.femtopedia.nil123fix.disableupdatecheck.updatemanager;

import de.femtopedia.nil123fix.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("vazkii.um.ThreadUpdateManager")
public class ThreadUpdateManagerTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("run()V")
    public void shortCircuit(PatchContext ctx) {
        ctx.jumpToStart();

        ctx.add(
                RETURN()
        );
    }

}
