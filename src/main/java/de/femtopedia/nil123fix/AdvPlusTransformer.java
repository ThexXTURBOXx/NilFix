package de.femtopedia.nil123fix;

import nilloader.api.ASMTransformer;
import nilloader.api.lib.mini.InsnBuilder;

public abstract class AdvPlusTransformer extends InsnBuilder implements PlusTransformer, ASMTransformer {

    public AdvPlusTransformer() {
        super(Nil123FixPremain.mappings);
    }

}
