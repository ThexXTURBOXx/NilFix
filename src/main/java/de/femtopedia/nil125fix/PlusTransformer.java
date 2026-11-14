package de.femtopedia.nil125fix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
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

    /**
     * Utility class for static method access.
     */
    final class Utils {

        public static void printDebugInfo() {
            Nil125FixPremain.log.debug("=================== JAVA DEBUG INFO ===================");
            System.getProperties().entrySet().stream()
                    .map(e -> new AbstractMap.SimpleEntry<>(String.valueOf(e.getKey()), String.valueOf(e.getValue())))
                    .filter(e -> e.getKey().startsWith("java.") || e.getKey().startsWith("os."))
                    .filter(e -> !e.getKey().contains("path") && !e.getKey().contains("dir") &&
                                 !e.getKey().contains("home"))
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> Nil125FixPremain.log.debug(e.getKey() + " = " + e.getValue()));
            Nil125FixPremain.log.debug("=================== JAVA DEBUG INFO ===================");
        }

        public static void removePrintlns(InsnBuilder builder, PatchContext ctx) {
            removePrintlns(builder, ctx, false);
        }

        public static void removePrintlns(InsnBuilder builder, PatchContext ctx, boolean alsoStdErr) {
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

        public static InsnList toInsnList(AbstractInsnNode... insns) {
            InsnList li = new InsnList();
            for (AbstractInsnNode ain : insns) li.add(ain);
            return li;
        }

        public static Object getMinecraftInstance() {
            return DeferredUtils.getMinecraftInstance();
        }

    }

    /**
     * Class with utility methods that should be loaded a bit later
     * such that the remapper can kick in.
     * DO NOT CALL THESE FUNCTIONS BEFORE premain IS FINISHED!
     */
    final class DeferredUtils {

        private static Object getMinecraftInstance() {
            return Minecraft.theMinecraft;
        }

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Prioritised {
        int priority() default 100;
    }

}
