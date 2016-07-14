package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Near;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public interface ObservableVector extends ObservableValue<Vector4f>, Near<ObservableVector> {

    default Vector4f get() {
        return getValue();
    }

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

    @Override
    default boolean isNearTo(ObservableVector o, double epsilon) {
        return get().isNearTo(o.get(), epsilon);
    }

    @Override
    default float bound() {
        return get().bound();
    }
}
