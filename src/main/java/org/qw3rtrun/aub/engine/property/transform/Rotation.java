package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;

import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;

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
    public Vector3fBinding apply(ObservableVector3f source) {
        Vector3fBinding apply = super.apply(source);
        return QuaternionBinding.rotate(QuaternionBinding.normalize(rotation), apply);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return Matrix4fBinding.multiply(
                new Matrix4fBinding(
                        () -> {
                            Quaternion normalized = rotation.get().normalize();
                            float x = normalized.getX();
                            float y = normalized.getY();
                            float z = normalized.getZ();
                            float a = normalized.getA();
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

