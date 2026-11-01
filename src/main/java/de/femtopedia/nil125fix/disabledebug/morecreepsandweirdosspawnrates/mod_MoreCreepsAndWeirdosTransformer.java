package de.femtopedia.nil125fix.disabledebug.morecreepsandweirdosspawnrates;

import de.femtopedia.nil125fix.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("mod_MoreCreepsAndWeirdos")
public class mod_MoreCreepsAndWeirdosTransformer extends MiniPlusTransformer {

    @Patch.Method("modsLoaded()V")
    public void skipDebugLines(PatchContext ctx) {
        removePrintlns(ctx, true);
    }

}
