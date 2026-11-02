package de.femtopedia.nil125fix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import nilloader.api.lib.asm.tree.AbstractInsnNode;
import nilloader.api.lib.asm.tree.InsnList;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.asm.tree.MethodInsnNode;
import nilloader.api.lib.mini.InsnBuilder;
import nilloader.api.lib.mini.PatchContext;

public interface PlusTransformer {

    default String hooks() {
        return hooks("Hooks");
    }

    default String hooks(String subclass) {
        return getClass().getName().replace('.', '/') + "$" + subclass;
    }

    default InsnList toInsnList(AbstractInsnNode... insns) {
        return Utils.toInsnList(insns);
    }

    default void removePrintlns(PatchContext ctx) {
        removePrintlns(ctx, false);
    }

    default void removePrintlns(PatchContext ctx, boolean alsoStdErr) {
        Utils.removePrintlns((InsnBuilder) this, ctx, alsoStdErr);
    }

    static Object getMinecraftInstance() {
        return Utils.getMinecraftInstance();
    }

    final class Utils {

        static void removePrintlns(InsnBuilder builder, PatchContext ctx) {
            removePrintlns(builder, ctx, false);
        }

        static void removePrintlns(InsnBuilder builder, PatchContext ctx, boolean alsoStdErr) {
            List<String> streams = new ArrayList<>();
            streams.add("out");
            if (alsoStdErr) {
                streams.add("err");
            }

            for (String stream : streams) {
                while (true) {
                    PatchContext.SearchResult res = ctx.search(
                            builder.GETSTATIC("java/lang/System", stream, "Ljava/io/PrintStream;")
                    );
                    if (!res.isSuccessful()) break;

                    LabelNode Lskip = new LabelNode();

                    res.jumpBefore();
                    ctx.add(
                            builder.GOTO(Lskip)
                    );

                    boolean foundPrintln = false;
                    for (int k = ctx.getPointer(); k < ctx.getLength(); ++k) {
                        ctx.jumpForward(1);
                        AbstractInsnNode node = ctx.get();
                        if (!(node instanceof MethodInsnNode)) continue;
                        MethodInsnNode mn = (MethodInsnNode) node;
                        if (mn.owner.equals("java/io/PrintStream") && mn.name.equals("println")) {
                            foundPrintln = true;
                            break;
                        }
                    }

                    if (!foundPrintln) {
                        throw new IllegalArgumentException("Could not find matching println call!");
                    }
                    ctx.jumpForward(1);
                    ctx.add(
                            Lskip
                    );
                }
            }
        }

        static InsnList toInsnList(AbstractInsnNode... insns) {
            InsnList li = new InsnList();
            for (AbstractInsnNode ain : insns) li.add(ain);
            return li;
        }

        static Object getMinecraftInstance() {
            try {
                if (Container.getMinecraftInstance == null) {
                    Class<?> ModLoader = Class.forName("ModLoader");
                    Method m = ModLoader.getMethod("getMinecraftInstance");
                    MethodHandles.Lookup lookup = MethodHandles.lookup();
                    Container.getMinecraftInstance = lookup.unreflect(m);
                }
                return Container.getMinecraftInstance.invoke();
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }

    }

    final class Container {
        private static MethodHandle getMinecraftInstance;
    }

}
