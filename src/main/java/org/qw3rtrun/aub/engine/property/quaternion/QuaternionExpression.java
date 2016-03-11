package org.qw3rtrun.aub.engine.property.quaternion;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;

import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

public interface QuaternionExpression extends ObservableQuaternion {

    default QuaternionBinding conjugate() {
        return new QuaternionBinding(() -> this.get().conjugate(), this);
    }

    default QuaternionBinding product(ObservableQuaternion quaternion) {
        return new QuaternionBinding(() -> this.get().product(quaternion.getValue()).product(this.get().conjugate()),
                this, quaternion);
    }

    default Vector4fBinding rotate(ObservableVector vector) {
        return new Vector4fBinding(() -> this.get().product(quaternion(vector.getValue())).product(this.get().conjugate()).asVector(), QuaternionExpression.this, vector);
    }

    default Matrix4fBinding rotationMatrix() {
        return new Matrix4fBinding(
                () -> {
                    Quaternion n = this.get().normalize();
                    double C = Math.cos(a);
                    double S = Math.sin(a);
                    double iC = 1 - C;
                    double x2 = x * x;
                    double y2 = y * y;
                    double z2 = z * z;
                    return matr(
                            (float) (x2 + (1 - x2) * C), (float) (iC * x * y - z * S), (float) (iC * x * z + y * S),
                            (float) (iC * x * y + z * S), (float) (y2 + (1 - y2) * C), (float) (iC * y * z - x * S),
                            (float) (iC * x * z - y * S), (float) (iC * y * z + x * S), (float) (z2 + (1 - z2) * C));
                }, quaternion);
    }


}
