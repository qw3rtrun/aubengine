package org.qw3rtrun.aub.engine.property.quaternion;

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
}
