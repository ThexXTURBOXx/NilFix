package de.femtopedia.nil147fix.infinibows;

import de.femtopedia.nil147fix.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.ItemBow")
public class ItemBowTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("onItemRightClick(Lnet/minecraft/src/ItemStack;Lnet/minecraft/src/World;" +
                  "Lnet/minecraft/src/EntityPlayer;)Lnet/minecraft/src/ItemStack;")
    public void enableInfiniBows(PatchContext ctx) {
        LabelNode Lprocess = new LabelNode();

        ctx.search(
                INVOKEVIRTUAL("net/minecraft/src/InventoryPlayer", "hasItem", "(I)Z")
        ).jumpAfter();
        ctx.add(
                IFNE(Lprocess),
                GETSTATIC("net/minecraft/src/Enchantment", "infinity",
                        "Lnet/minecraft/src/Enchantment;"),
                GETFIELD("net/minecraft/src/Enchantment", "effectId", "I"),
                ALOAD(1),
                INVOKESTATIC("net/minecraft/src/EnchantmentHelper", "getEnchantmentLevel",
                        "(ILnet/minecraft/src/ItemStack;)I"),
                IFGT(Lprocess),
                ICONST_0() // dummy instruction to make avoid errors with IFEQ afterwards
        );

        ctx.jumpToLastReturn();
        ctx.searchBackward(
                ALOAD(3)
        ).jumpBefore();
        ctx.add(
                Lprocess
        );
    }

}
