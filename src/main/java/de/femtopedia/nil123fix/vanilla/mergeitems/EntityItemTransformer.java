package de.femtopedia.nil123fix.vanilla.mergeitems;

import de.femtopedia.nil123fix.MiniPlusTransformer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.EntityItem")
public class EntityItemTransformer extends MiniPlusTransformer {

    @Patch.Method("onUpdate()V")
    public void mergeDroppedItems(PatchContext ctx) {
        ctx.search(
                INVOKEVIRTUAL("net/minecraft/src/EntityItem", "moveEntity", "(DDD)V")
        ).jumpAfter();

        ctx.add(
                ALOAD(0),
                INVOKESTATIC(hooks(), "testMerge", "(Lnet/minecraft/src/EntityItem;)V")
        );
    }

    public static class Hooks {

        public static void testMerge(EntityItem thiz) {
            boolean moved = (int) thiz.prevPosX != (int) thiz.posX ||
                            (int) thiz.prevPosY != (int) thiz.posY ||
                            (int) thiz.prevPosZ != (int) thiz.posZ;
            if (moved || thiz.ticksExisted % 25 == 0) {
                if (!thiz.worldObj.isRemote) {
                    for (Object ei : thiz.worldObj.getEntitiesWithinAABB(EntityItem.class,
                            thiz.boundingBox.expand(0.5, 0.0, 0.5))) {
                        if (!(ei instanceof EntityItem)) continue;
                        combineItems(thiz, (EntityItem) ei);
                    }
                }
            }
        }

        public static boolean combineItems(EntityItem thiz, EntityItem other) {
            if (other == thiz) return false;
            if (!other.isEntityAlive() || !thiz.isEntityAlive()) return false;

            ItemStack thisItem = thiz.item;
            ItemStack otherItem = other.item;
            if (otherItem.getItem() != thisItem.getItem())
                return false;
            if (otherItem.hasTagCompound() ^ thisItem.hasTagCompound())
                return false;
            if (otherItem.hasTagCompound() && !otherItem.getTagCompound().equals(thisItem.getTagCompound()))
                return false;
            if (otherItem.getItem().getHasSubtypes() && otherItem.getItemDamage() != thisItem.getItemDamage())
                return false;
            if (otherItem.stackSize < thisItem.stackSize)
                return combineItems(other, thiz);
            if (otherItem.stackSize + thisItem.stackSize > otherItem.getMaxStackSize())
                return false;

            otherItem.stackSize = otherItem.stackSize + thisItem.stackSize;
            other.delayBeforeCanPickup = Math.max(other.delayBeforeCanPickup, thiz.delayBeforeCanPickup);
            other.age = Math.min(other.age, thiz.age);
            thiz.setEntityDead();
            return true;
        }

    }

}
