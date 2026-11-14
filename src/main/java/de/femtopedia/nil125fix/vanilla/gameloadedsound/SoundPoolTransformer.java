package de.femtopedia.nil125fix.vanilla.gameloadedsound;

import de.femtopedia.nil125fix.MiniPlusTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.SoundPool")
public class SoundPoolTransformer extends MiniPlusTransformer {

    @Patch.Method("addSound(Ljava/lang/String;Ljava/io/File;)Lnet/minecraft/src/SoundPoolEntry;")
    public void ding1(PatchContext ctx) {
        ctx.jumpToLastReturn();

        ctx.add(
                ALOAD(1),
                INVOKESTATIC(hooks(), "soundLoaded", "(Ljava/lang/String;)V")
        );
    }

    @Patch.Method.Optional
    @Patch.Method("addSound(Ljava/lang/String;Ljava/net/URL;)Lnet/minecraft/src/SoundPoolEntry;")
    public void ding2(PatchContext ctx) {
        ctx.jumpToLastReturn();

        ctx.add(
                ALOAD(1),
                INVOKESTATIC(hooks(), "soundLoaded", "(Ljava/lang/String;)V")
        );
    }

    public static class Hooks {

        public static void soundLoaded(String sound) {
            if (sound.equals("random.orb"))
                MinecraftTransformer.Hooks.orbSoundLoaded();
        }

    }

}
