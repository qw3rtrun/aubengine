package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;

public class Transformation {

    private final ObservableVector v;

    public Transformation(ObservableVector v) {
        this.v = v;
    }

    public ObservableVector get() {
        return v;
    }

    public Transformation scale(ObservableVector scale) {
        return new Transformation(new ScaleBinding(get(), scale));
    }

    public Transformation rotate(ObservableQuaternion rotation) {
        return new Transformation(new RotateBinding(get(), rotation));
    }

    public Transformation translate(ObservableVector translate) {
        return new Transformation(new TranslateBinding(get(), translate));
    }
}
