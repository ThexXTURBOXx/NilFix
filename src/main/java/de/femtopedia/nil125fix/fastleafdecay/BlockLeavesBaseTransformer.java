package de.femtopedia.nil125fix.fastleafdecay;

import de.femtopedia.nil125fix.MiniPlusTransformer;
import java.util.Random;
import net.minecraft.src.World;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.ClassNode;
import nilloader.api.lib.asm.tree.MethodNode;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.BlockLeavesBase")
public class BlockLeavesBaseTransformer extends MiniPlusTransformer {

    @Override
    protected boolean modifyClassStructure(ClassNode clazz) {
        String desc = remapMethodDesc("(Lnet/minecraft/src/World;IIII)V");
        MethodNode onNeighborBlockChange = new MethodNode(Opcodes.ASM5, Opcodes.ACC_PUBLIC,
                "a", desc, null, null);

        onNeighborBlockChange.instructions = toInsnList(
                ALOAD(1),
                ILOAD(2),
                ILOAD(3),
                ILOAD(4),
                ALOAD(0),
                GETFIELD("net/minecraft/src/Block", "blockID", "I"),
                INVOKESTATIC(hooks(), "handleLeafDecay", "(Lnet/minecraft/src/World;IIII)V"),
                RETURN()
        );

        clazz.methods.add(onNeighborBlockChange);

        return true; // don't know if absolutely necessary, let's just do it...
    }

    public static class Hooks {

        private static Random rng = new Random();
        private static int baseDecayTime = 4;
        private static int maxDecayTime = 11;
        private static int randomizationTime = maxDecayTime - baseDecayTime;

        public static void handleLeafDecay(World worldObj, int posX, int posY, int posZ, int blockId) {
            worldObj.scheduleBlockUpdate(posX, posY, posZ, blockId, baseDecayTime + rng.nextInt(randomizationTime));
        }

        public static void setRandom(Random newRandom) {
            rng = newRandom;
        }

        public static void setBaseDecayTime(int newBaseDecayTime) {
            if (newBaseDecayTime >= maxDecayTime)
                throw new IllegalArgumentException("Base decay time cannot be greater than or equal to max decay time");
            baseDecayTime = newBaseDecayTime;
            randomizationTime = maxDecayTime - baseDecayTime;
        }

        public static void setMaxDecayTime(int newMaxDecayTime) {
            if (newMaxDecayTime <= baseDecayTime)
                throw new IllegalArgumentException("Max decay time cannot be less than or equal to base decay time");
            maxDecayTime = newMaxDecayTime;
            randomizationTime = maxDecayTime - baseDecayTime;
        }

    }

}
