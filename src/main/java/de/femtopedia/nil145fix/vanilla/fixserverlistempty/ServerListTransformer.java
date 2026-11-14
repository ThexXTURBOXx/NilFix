package de.femtopedia.nil145fix.vanilla.fixserverlistempty;

import de.femtopedia.nil145fix.MiniPlusTransformer;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.ServerList")
public class ServerListTransformer extends MiniPlusTransformer {

    @Patch.Method("loadServerList()V")
    public void fixNPEinEmptyServerList(PatchContext ctx) {
        LabelNode Lskip = new LabelNode();

        ctx.search(
                ALOAD(1)
        ).jumpBefore();

        ctx.add(
                ALOAD(1),
                IFNONNULL(Lskip),
                NEW("net/minecraft/src/NBTTagCompound"),
                DUP(),
                INVOKESPECIAL("net/minecraft/src/NBTTagCompound", "<init>", "()V"),
                ASTORE(1),
                Lskip
        );
    }

}
