package de.femtopedia.nil125fix.infernalmobs.fixbossbarflicker;

import de.femtopedia.nil125fix.MiniPlusTransformer;
import de.femtopedia.nil125fix.PlusTransformer.Prioritised;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.ClassNode;
import nilloader.api.lib.asm.tree.FieldNode;
import nilloader.api.lib.mini.annotation.Patch;

@Prioritised
@Patch.Class("net.minecraft.client.Minecraft")
public class MinecraftTransformer extends MiniPlusTransformer {

    @Override
    protected boolean modifyClassStructure(ClassNode clazz) {
        clazz.fields.add(new FieldNode(Opcodes.ASM5, Opcodes.ACC_PUBLIC,
                "nilfixInfMobsMOP", remapFieldDesc("Lnet/minecraft/src/MovingObjectPosition;"),
                null, null));
        return false;
    }

}
