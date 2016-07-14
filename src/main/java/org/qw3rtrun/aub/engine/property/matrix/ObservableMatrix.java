package org.qw3rtrun.aub.engine.property.matrix;

import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Near;

public interface ObservableMatrix extends ObservableValue<Matrix4f>, Near<ObservableMatrix> {

    default Matrix4f get() {
        return getValue();
    }

    @Override
    default boolean isNearTo(ObservableMatrix o, double epsilon) {
        return get().isNearTo(o.get(), epsilon);
    }

    @Override
    default float bound() {
        return get().bound();
    }
}
