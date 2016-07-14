package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Function;

import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

public class RotateBinding extends Vector4fBinding {
    private final ObservableQuaternion rotation;
    private final ObservableVector source;

    public RotateBinding(ObservableVector source, ObservableQuaternion rotation) {
        super(() -> rotation.get().product(quaternion(source.getValue())).product(rotation.get().conjugate()).asVector(),
                rotation, rotation);
        this.rotation = rotation;
        this.source = source;
    }

    public static Function<ObservableVector, RotateBinding> rotateBy(ObservableQuaternion quaternion) {
        return (src) -> new RotateBinding(src, quaternion);
    }

    public static Function<ObservableQuaternion, RotateBinding> rotate(ObservableVector source) {
        return (rotation) -> new RotateBinding(source, rotation);
    }

    public Quaternion getRotation() {
        return rotation.get();
    }

    public ObservableQuaternion rotationProperty() {
        return rotation;
    }

    public Vector4f getSource() {
        return source.getValue();
    }

    public ObservableVector sourceProperty() {
        return source;
    }
}
