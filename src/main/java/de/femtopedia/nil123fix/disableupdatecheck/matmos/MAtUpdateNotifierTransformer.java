package de.femtopedia.nil123fix.disableupdatecheck.matmos;

import de.femtopedia.nil123fix.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("MAtUpdateNotifier")
public class MAtUpdateNotifierTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("run()V")
    public void shortCircuit(PatchContext ctx) {
        ctx.jumpToStart();
        ctx.add(RETURN());
    }

}
