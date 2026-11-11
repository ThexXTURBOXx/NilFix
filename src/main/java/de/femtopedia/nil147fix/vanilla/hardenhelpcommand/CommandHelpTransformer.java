package de.femtopedia.nil147fix.vanilla.hardenhelpcommand;

import de.femtopedia.nil147fix.MiniPlusTransformer;
import net.minecraft.src.ICommand;
import net.minecraft.src.ICommandSender;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.CommandHelp")
public class CommandHelpTransformer extends MiniPlusTransformer {

    @Patch.Method("processCommand(Lnet/minecraft/src/ICommandSender;[Ljava/lang/String;)V")
    public void fixFaultyCommandUsages(PatchContext ctx) {
        while (true) {
            PatchContext.SearchResult res = ctx.search(
                    INVOKEINTERFACE("net/minecraft/src/ICommand", "getCommandUsage",
                            "(Lnet/minecraft/src/ICommandSender;)Ljava/lang/String;")
            );
            if (!res.isSuccessful()) break;

            LabelNode Lskip = new LabelNode();

            res.jumpBefore();
            ctx.add(
                    INVOKESTATIC(hooks(), "getCommandUsage",
                            "(Lnet/minecraft/src/ICommand;Lnet/minecraft/src/ICommandSender;)Ljava/lang/String;"),
                    GOTO(Lskip)
            );

            res.jumpAfter();
            ctx.add(
                    Lskip
            );
        }
    }

    public static class Hooks {

        public static String getCommandUsage(ICommand cmd, ICommandSender sender) {
            String usage = cmd.getCommandUsage(sender);
            return usage != null ? usage : "/" + cmd.getCommandName() + " ...";
        }

    }

}
