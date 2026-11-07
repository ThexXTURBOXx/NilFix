package de.femtopedia.nil145fix.vanilla.fixinfiniteoceans;

import de.femtopedia.nil145fix.MiniPlusTransformer;
import net.minecraft.src.GenLayer;
import net.minecraft.src.GenLayerSmooth;
import net.minecraft.src.IntCache;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.GenLayer")
public class GenLayerTransformer extends MiniPlusTransformer {

    @Patch.Method("func_48425_a(JLnet/minecraft/src/WorldType;)[Lnet/minecraft/src/GenLayer;")
    public void removeTooMuchOcean(PatchContext ctx) {
        ctx.search(
                LDC(2L)
        ).jumpAfter();
        ctx.search(
                ALOAD(5)
        ).jumpAfter();

        ctx.add(
                INVOKESTATIC(hooks(), "removeTooMuchOcean",
                        "(Lnet/minecraft/src/GenLayer;)Lnet/minecraft/src/GenLayer;")
        );
    }

    public static class Hooks {

        public static GenLayer removeTooMuchOcean(GenLayer parent) {
            parent = new GenLayerSmooth(50L, parent);
            parent = new GenLayerSmooth(70L, parent);
            parent = new BiomeLayerRemoveTooMuchOcean(2L, parent);
            return parent;
        }

    }

    public static class BiomeLayerRemoveTooMuchOcean extends GenLayer {

        public BiomeLayerRemoveTooMuchOcean(long l, GenLayer genLayer) {
            super(l);
            parent = genLayer;
        }

        public int[] getInts(int i, int j, int k, int l) {
            int var5 = i - 1;
            int var6 = j - 1;
            int var7 = k + 2;
            int var8 = l + 2;
            int[] var9 = parent.getInts(var5, var6, var7, var8);
            int[] var10 = IntCache.getIntCache(k * l);

            for (int var11 = 0; var11 < l; ++var11) {
                for (int var12 = 0; var12 < k; ++var12) {
                    int var13 = var9[var12 + 1 + (var11 + 1 - 1) * (k + 2)];
                    int var14 = var9[var12 + 1 + 1 + (var11 + 1) * (k + 2)];
                    int var15 = var9[var12 + 1 - 1 + (var11 + 1) * (k + 2)];
                    int var16 = var9[var12 + 1 + (var11 + 1 + 1) * (k + 2)];
                    int var17 = var9[var12 + 1 + (var11 + 1) * var7];
                    var10[var12 + var11 * k] = var17;
                    initChunkSeed(var12 + i, var11 + j);
                    if (var17 == 0 && var13 == 0 && var14 == 0 && var15 == 0 && var16 == 0 && nextInt(2) == 0) {
                        var10[var12 + var11 * k] = 1;
                    }
                }
            }

            return var10;
        }

    }

}
