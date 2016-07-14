package org.qw3rtrun.aub.engine.property.quaternion;

import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Near;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

public interface ObservableQuaternion extends ObservableValue<Quaternion>, Near<ObservableQuaternion> {

    default Quaternion get() {
        return getValue();
    }

    default float getX() {
        return getValue().x;
    }

    default float getY() {
        return getValue().y;
    }

    default float getZ() {
        return getValue().z;
    }

    default float getA() {
        return getValue().getA();
    }

    default Vector4f getVector() {
        return vect(getValue().x, getValue().y, getValue().z);
    }

    @Override
    default boolean isNearTo(ObservableQuaternion o, double epsilon) {
        return get().isNearTo(o.get(), epsilon);
    }

    @Override
    default float bound() {
        return get().bound();
    }
}
