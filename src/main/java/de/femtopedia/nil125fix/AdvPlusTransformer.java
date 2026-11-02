package de.femtopedia.nil125fix;

import java.util.Optional;
import nilloader.NilAgent;
import nilloader.api.ASMTransformer;
import nilloader.api.lib.mini.InsnBuilder;

public abstract class AdvPlusTransformer extends InsnBuilder implements PlusTransformer, ASMTransformer {

    public AdvPlusTransformer() {
        super(Optional.ofNullable(NilAgent.getActiveMappings(Nil125FixPremain.MOD_ID)));
    }

}
