package de.femtopedia.nil145fix.vanilla.improveditemmerge;

import de.femtopedia.nil145fix.MiniPlusTransformer;
import net.minecraft.src.EntityItem;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.EntityItem")
public class EntityItemTransformer extends MiniPlusTransformer {

    @Patch.Method("onUpdate()V")
    public void mergeDroppedItems(PatchContext ctx) {
        ctx.search(
                ISTORE(1)
        ).jumpAfter();

        ctx.add(
                ALOAD(0),
                ILOAD(1),
                INVOKESTATIC(hooks(), "shouldTryMerge", "(Lnet/minecraft/src/EntityItem;Z)Z"),
                ISTORE(1)
        );
    }

    public static class Hooks {

        public static boolean shouldTryMerge(EntityItem thiz, boolean moved) {
            return moved || thiz.ticksExisted % 25 == 0;
        }

    }

}
