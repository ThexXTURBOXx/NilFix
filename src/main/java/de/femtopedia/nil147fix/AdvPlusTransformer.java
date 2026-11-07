package de.femtopedia.nil147fix;

import nilloader.api.ASMTransformer;
import nilloader.api.lib.mini.InsnBuilder;

public abstract class AdvPlusTransformer extends InsnBuilder implements PlusTransformer, ASMTransformer {

    public AdvPlusTransformer() {
        super(Nil147FixPremain.mappings);
    }

}
