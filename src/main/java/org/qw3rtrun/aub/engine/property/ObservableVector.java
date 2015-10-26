package org.qw3rtrun.aub.engine.property;

import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public interface ObservableVector extends ObservableValue<Vector4f> {
    float getX();

    float getY();

    float getZ();

    float getW();

    float getLength();
}
