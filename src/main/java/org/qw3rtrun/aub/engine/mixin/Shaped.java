package org.qw3rtrun.aub.engine.mixin;

import java.util.Optional;

/**
 * Created by strunov on 9/23/2015.
 */
public interface Shaped {

    public default Optional<Object> mesh() {
        return Optional.empty();
    }

}
