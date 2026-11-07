package de.femtopedia.nil145fix.nei.bettersearchfield;

import de.femtopedia.nil145fix.MiniPlusTransformer;
import java.lang.reflect.Modifier;
import nilloader.api.lib.asm.tree.ClassNode;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.src.GuiTextField")
public class GuiTextFieldTransformer extends MiniPlusTransformer {

    @Override
    protected boolean modifyClassStructure(ClassNode clazz) {
        // Make fields non-final
        clazz.fields.forEach(field -> field.access &= ~Modifier.FINAL);
        return true; // don't know if absolutely necessary, let's just do it...
    }

}
