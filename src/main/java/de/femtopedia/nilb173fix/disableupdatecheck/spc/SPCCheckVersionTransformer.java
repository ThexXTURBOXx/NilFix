package de.femtopedia.nilb173fix.disableupdatecheck.spc;

import de.femtopedia.nilb173fix.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("SPCCheckVersion")
public class SPCCheckVersionTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("run()V")
    public void shortCircuit(PatchContext ctx) {
        ctx.jumpToStart();
        ctx.add(RETURN());
    }

}
