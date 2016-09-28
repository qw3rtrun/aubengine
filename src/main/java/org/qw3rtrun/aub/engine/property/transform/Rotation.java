package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;

import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

public class Rotation implements Transformation {

    private final ObservableQuaternion rotation;

    public Rotation(ObservableQuaternion rotation) {
        this.rotation = rotation;
    }

    @Override
    public Vector4fBinding apply(ObservableVector source) {
        return new Vector4fBinding(
                () -> rotation.get()
                        .product(quaternion(source.getValue()))
                        .product(rotation.get().conjugate()).asVector(),
                source, rotation);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return new Matrix4fBinding(() -> {
            float x = rotation.getX();
            float y = rotation.getY();
            float z = rotation.getZ();
            float a = rotation.getA();
            return matr(
                    1 - 2 * y * y - 2 * z * z, 2 * (x * y - z * a), 2 * (x * z + y * a),
                    2 * (x * y + z * a), 1 - 2 * x * x - 2 * z * z, 2 * (y * z - x * a),
                    2 * (x * z - y * a), 2 * (y * z + x * a), 1 - 2 * x * x - 2 * y * y);
        }, rotation);
    }

    @Override
    public Transformation invert() {
        return new Rotation(
                new QuaternionBinding(() -> rotation.get().withA(-rotation.get().getA()), rotation));
    }
}

