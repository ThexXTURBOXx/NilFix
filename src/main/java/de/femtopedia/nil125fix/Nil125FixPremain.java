package de.femtopedia.nil125fix;

import de.femtopedia.nil125fix.PlusTransformer.Prioritised;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import nilloader.NilAgent;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;
import nilloader.impl.lib.lorenz.MappingSet;

public class Nil125FixPremain implements Runnable {

    public static final String MOD_ID = "nil125fix";
    public static final String MOD_NAME = "Nil125Fix";

    public static final NilLogger log = NilLogger.get(MOD_NAME);
    public static final Optional<MappingSet> mappings = Optional.ofNullable(NilAgent.getActiveMappings(MOD_ID));

    public static final Set<String> DISABLED_BY_DEFAULT = new HashSet<>(Arrays.asList(
            "fastleafdecay",
            "infinibows",
            "keepmystats.usededicatedusername",
            "vanilla.fixinfiniteoceans"
    ));

    @Override
    public void run() {
        Optional<NilMetadata> meta = NilModList.getById(MOD_ID);
        if (!meta.isPresent()) {
            log.error(MOD_NAME + " cannot find itself! AAAAHHHH!");
            return;
        }

        int pkgLength = getClass().getPackage().getName().length() + 1;
        try (ZipFile zip = new ZipFile(meta.get().source)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();

            Set<NilFixTransformer> classes = new TreeSet<>(NilFixTransformer.PRIORITY_COMPARATOR);
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (!name.endsWith("Transformer.class")) continue;

                name = name.substring(0, name.length() - ".class".length())
                        .replace('\\', '/')
                        .replace('/', '.');

                Class<?> clazz = Class.forName(name);
                if (!ClassTransformer.class.isAssignableFrom(clazz) ||
                    Modifier.isAbstract(clazz.getModifiers())) continue;

                String cfgKey = clazz.getPackage().getName().substring(pkgLength);
                int priority = clazz.isAnnotationPresent(Prioritised.class)
                        ? clazz.getAnnotation(Prioritised.class).priority() : 0;
                classes.add(new NilFixTransformer(clazz, cfgKey, priority));
            }

            Configuration config = new Configuration("config/" + MOD_NAME + ".cfg",
                    classes.stream().map(t -> t.cfgKey).collect(Collectors.toList()));
            config.saveIfChanged();

            for (NilFixTransformer t : classes) {
                if (t.cfgKey != null && !config.isEnabled(t.cfgKey)) {
                    log.info("Skipping disabled transformer " + t.clazz.getName());
                    continue;
                }
                ClassTransformer.register((ClassTransformer) t.clazz.getDeclaredConstructor().newInstance());
                log.debug("Registered transformer " + t.clazz.getName());
            }

            log.info("Applied a few miscellaneous fixes...");
        } catch (Throwable t) {
            log.error("Transformers could not be registered!", t);
        }
    }

    private static class NilFixTransformer {

        private static final Comparator<NilFixTransformer> PRIORITY_COMPARATOR =
                Comparator.<NilFixTransformer>comparingInt(t -> -t.priority)
                        .thenComparing(t -> t.cfgKey == null ? "" : t.cfgKey)
                        .thenComparing(t -> t.clazz.getName());

        private final Class<?> clazz;
        private final String cfgKey;
        private final int priority;

        public NilFixTransformer(Class<?> clazz, String cfgKey, int priority) {
            this.clazz = clazz;
            this.cfgKey = cfgKey;
            this.priority = priority;
        }

    }

}
