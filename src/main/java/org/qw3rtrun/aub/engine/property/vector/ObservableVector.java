package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public interface ObservableVector extends ObservableValue<Vector4f> {
    default float getX() {
        return getValue().getX();
    }

    default float getY() {
        return getValue().getY();
    }

    default float getZ() {
        return getValue().getZ();
    }

    default float getW() {
        return getValue().getW();
    }

    default float getLength() {
        return getValue().length();
    }
}
