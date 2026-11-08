package de.femtopedia.nil147fix.betterdungeons.warniffolderismissing;

import cpw.mods.fml.common.Loader;
import de.femtopedia.nil147fix.MiniPlusTransformer;
import de.femtopedia.nil147fix.Nil147FixPremain;
import java.io.File;
import net.minecraft.client.Minecraft;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

import static de.femtopedia.nil147fix.PlusTransformer.getMinecraftInstance;
import static de.femtopedia.nil147fix.PlusTransformer.isDedicatedServer;

@Patch.Class("chocolate.mods.BD.ConfigHelper")
public class ConfigHelperTransformer extends MiniPlusTransformer {

    @Patch.Method("loadConfig()V")
    public void warnAboutMissingChocolateFolderUponStart(PatchContext ctx) {
        ctx.jumpToStart();

        ctx.add(
                INVOKESTATIC(hooks(), "warnAboutMissingChocolateFolder", "()V")
        );
    }

    public static class Hooks {

        public static void warnAboutMissingChocolateFolder() {
            File configDir = new File(Loader.instance.canonicalMinecraftDir, "Chocolate");
            File buildingDir = new File(configDir, "Building");
            File chestConfigDir = new File(configDir, "ChestConfig");
            File dungeonConfigDir = new File(configDir, "DungeonConfig");
            if (!configDir.exists() || !configDir.isDirectory() ||
                !buildingDir.exists() || !buildingDir.isDirectory() ||
                !chestConfigDir.exists() || !chestConfigDir.isDirectory() ||
                !dungeonConfigDir.exists() || !dungeonConfigDir.isDirectory()) {
                Nil147FixPremain.log.error("You have installed Better Dungeons, but have not");
                Nil147FixPremain.log.error("extracted the Chocolate folder correctly!");
                Nil147FixPremain.log.error("You will experience various bugs and crashes!");
                Nil147FixPremain.log.error("Please install the mod correctly, i.e., extract");
                Nil147FixPremain.log.error("the \"Chocolate\" folder from the mod zip into");
                Nil147FixPremain.log.error("your Minecraft root directory, which should be:");
                Nil147FixPremain.log.error(Minecraft.getMinecraftDir().toString());
            }

            if (!isDedicatedServer()) {
                Minecraft mc = (Minecraft) getMinecraftInstance();
                if (mc != null && mc.thePlayer != null) {
                    mc.thePlayer.addChatMessage("[" + Nil147FixPremain.MOD_NAME + "] Check the console!");
                }
            }
        }

    }

}
