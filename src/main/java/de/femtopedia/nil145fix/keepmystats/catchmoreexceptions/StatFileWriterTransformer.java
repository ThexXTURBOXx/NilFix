package de.femtopedia.nil145fix.keepmystats.catchmoreexceptions;

import de.femtopedia.nil145fix.MiniPlusTransformer;
import nilloader.api.lib.asm.Type;
import nilloader.api.lib.asm.tree.ClassNode;
import nilloader.api.lib.asm.tree.MethodInsnNode;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.StatFileWriter")
public class StatFileWriterTransformer extends MiniPlusTransformer {

    @Override
    protected boolean modifyClassStructure(ClassNode clazz) {
        clazz.methods.stream()
                .filter(m -> {
                    Type t = Type.getMethodType(m.desc);
                    if (t.getArgumentCount() != 1) return false;
                    if (!t.getArgumentTypes()[0].getClassName().equals("java.lang.String")) return false;
                    return t.getReturnType().getClassName().equals("java.util.Map");
                })
                .forEach(m -> {
                    m.tryCatchBlocks.stream()
                            .filter(tcb -> tcb.type.equals("argo/saj/InvalidSyntaxException"))
                            .forEach(tcb -> tcb.type = "java/lang/Throwable");
                    m.instructions.forEach(n -> {
                        if (!(n instanceof MethodInsnNode)) return;
                        MethodInsnNode i = (MethodInsnNode) n;
                        if (!i.owner.equals("argo/saj/InvalidSyntaxException")) return;
                        i.owner = "java/lang/Throwable";
                    });
                    m.localVariables.stream()
                            .filter(lv -> lv.desc.equals("Largo/saj/InvalidSyntaxException;"))
                            .forEach(lv -> lv.desc = "Ljava/lang/Throwable;");
                });
        return true; // Just to be secure
    }

}
