package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;

import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

public class Rotation extends Transformation.AbstractTransformation {

    private final ObservableQuaternion rotation;

    public Rotation(ObservableQuaternion rotation) {
        super();
        this.rotation = rotation;
    }

    Rotation(Transformation chain, ObservableQuaternion rotation) {
        super(chain);
        this.rotation = rotation;
    }

    @Override
    public Vector4fBinding apply(ObservableVector source) {
        Vector4fBinding apply = super.apply(source);
        return new Vector4fBinding(
                () -> rotation.get()
                        .product(quaternion(apply.get().w(0)))
                        .product(rotation.get().conjugate()).asVector(),
                apply, rotation);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return Matrix4fBinding.multiply(
                new Matrix4fBinding(
                        () -> {
                            float x = rotation.getX();
                            float y = rotation.getY();
                            float z = rotation.getZ();
                            float a = rotation.getA();
                            return matr(
                                    1 - 2 * y * y - 2 * z * z, 2 * (x * y - z * a), 2 * (x * z + y * a),
                                    2 * (x * y + z * a), 1 - 2 * x * x - 2 * z * z, 2 * (y * z - x * a),
                                    2 * (x * z - y * a), 2 * (y * z + x * a), 1 - 2 * x * x - 2 * y * y);
                        }, rotation
                ),
                super.asMatrix()
        );
    }

    @Override
    public Rotation rotate(ObservableQuaternion rotation) {
        return new Rotation(getChain(), QuaternionBinding.concatRotation(this.rotation, rotation));
    }

    @Override
    public Transformation invert(Transformation chain) {
        return getChain().invert(new Rotation(chain,
                new QuaternionBinding(() -> rotation.get().withA(-rotation.get().getA()), rotation)));
    }

    @Override
    public String toString() {
        return (getChain() == root ? "" : getChain().toString() + " -> ") + "Rotation{" + rotation + '}';
    }
}

