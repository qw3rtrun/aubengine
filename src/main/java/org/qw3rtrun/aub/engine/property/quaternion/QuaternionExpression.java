package org.qw3rtrun.aub.engine.property.quaternion;

import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;

interface QuaternionExpression extends ObservableQuaternion {

    default QuaternionBinding conjugate() {
        return new QuaternionBinding(() -> this.get().conjugate(), this);
    }

    default Vector4fBinding rotate(ObservableVector vector) {
        return QuaternionBinding.rotate(this, vector);
    }

    default QuaternionBinding normalize() {
        return QuaternionBinding.normalize(this);
    }
}
