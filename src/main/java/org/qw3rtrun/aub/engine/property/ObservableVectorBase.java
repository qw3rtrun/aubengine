package org.qw3rtrun.aub.engine.property;

import javafx.beans.value.ObservableValueBase;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public abstract class ObservableVectorBase extends ObservableValueBase<Vector4f> implements ObservableVector {
    public float getX() {
        return getValue().getX();
    }

    public float getY() {
        return getValue().getY();
    }

    public float getZ() {
        return getValue().getZ();
    }

    public float getW() {
        return getValue().getW();
    }

    public float getLength() {
        return getValue().length();
    }
}
