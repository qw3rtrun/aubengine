package org.qw3rtrun.aub.engine.property.quaternion;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;

import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

interface QuaternionExpression extends ObservableQuaternion {

    default QuaternionBinding conjugate() {
        return new QuaternionBinding(() -> this.get().conjugate(), this);
    }

    default Vector4fBinding rotate(ObservableVector vector) {
        return new Vector4fBinding(() -> this.get().product(quaternion(vector.getValue())).product(this.get().conjugate()).asVector(), QuaternionExpression.this, vector);
    }

    default Matrix4fBinding rotationNormMatrix() {
        return new Matrix4fBinding(() -> this.get().rotateNormMatrix(), this);
    }

    default Matrix4fBinding rotationMatrix() {
        return new Matrix4fBinding(() -> this.get().rotateMatrix(), this);
    }

}
