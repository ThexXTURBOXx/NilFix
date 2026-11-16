package de.femtopedia.nilb173fix.matmos.usemirror;

import de.femtopedia.nilb173fix.MiniPlusTransformer;
import java.net.URL;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("MAtOnlineDatabaseFetcher")
public class MAtOnlineDatabaseFetcherTransformer extends MiniPlusTransformer {

    @Patch.Method("getDatabase(Ljava/net/URL;)V")
    public void useMirror(PatchContext ctx) {
        ctx.search(
                PUTFIELD("MAtOnlineDatabaseFetcher", "url", "Ljava/net/URL;")
        ).jumpAfter();

        ctx.add(
                ALOAD(0),
                ALOAD(0),
                GETFIELD("MAtOnlineDatabaseFetcher", "url", "Ljava/net/URL;"),
                INVOKESTATIC(hooks(), "useMirror", "(Ljava/net/URL;)Ljava/net/URL;"),
                PUTFIELD("MAtOnlineDatabaseFetcher", "url", "Ljava/net/URL;")
        );
    }

    public static class Hooks {

        public static URL useMirror(URL url) {
            String s = url.toString();
            if (s.contains("ha3extra.googlecode.com/svn/trunk/")) {
                s = s.replace("ha3extra.googlecode.com/svn/trunk/",
                        "raw.githubusercontent.com/NicosMinecraftArchive/ha3extra/refs/heads/master/");
                s = s.replace("http://", "https://");
            }

            try {
                return new URL(s);
            } catch (Throwable ignored) {
            }
            return url; // Fallback if something real bad happens
        }

    }

}
