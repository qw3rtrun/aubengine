package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Near;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

public interface ObservableVector3f extends ObservableValue<Vector3f>, Near<ObservableVector3f> {

    default Vector3f get() {
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

    default float getLength() {
        return getValue().length();
    }

    @Override
    default boolean isNearTo(ObservableVector3f o, double epsilon) {
        return get().isNearTo(o.get(), epsilon);
    }

    @Override
    default float bound() {
        return get().bound();
    }
}
