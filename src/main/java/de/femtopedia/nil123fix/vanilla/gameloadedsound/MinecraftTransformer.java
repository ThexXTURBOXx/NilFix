package de.femtopedia.nil123fix.vanilla.gameloadedsound;

import de.femtopedia.nil123fix.MiniPlusTransformer;
import de.femtopedia.nil123fix.PlusTransformer;
import net.minecraft.client.Minecraft;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@PlusTransformer.Prioritised
@Patch.Class("net.minecraft.client.Minecraft")
public class MinecraftTransformer extends MiniPlusTransformer {

    @Patch.Method("startGame()V")
    public void ding(PatchContext ctx) {
        ctx.jumpToLastReturn();

        ctx.add(
                INVOKESTATIC(hooks(), "gameLoaded", "()V")
        );
    }

    public static class Hooks {

        private static boolean gameLoaded = false;
        private static boolean orbSoundLoaded = false;
        private static boolean dinged = false;

        public static void gameLoaded() {
            gameLoaded = true;
            ding();
        }

        public static void orbSoundLoaded() {
            orbSoundLoaded = true;
            ding();
        }

        public static void ding() {
            if (!gameLoaded || !orbSoundLoaded || dinged) return;
            dinged = true;

            Minecraft mc = (Minecraft) Utils.getMinecraftInstance();
            if (mc.sndManager != null)
                mc.sndManager.playSoundFX("random.orb", 1.0F, 1.0F);
        }

    }

}
