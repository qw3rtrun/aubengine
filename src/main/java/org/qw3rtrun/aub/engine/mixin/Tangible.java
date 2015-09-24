package org.qw3rtrun.aub.engine.mixin;

import java.util.Optional;

public interface Tangible {

    default Optional<Object> material() {
        return Optional.empty();
    }
}
