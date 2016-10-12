package org.qw3rtrun.aub.engine.property.quaternion;

import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;

interface QuaternionExpression extends ObservableQuaternion {

    default QuaternionBinding conjugate() {
        return new QuaternionBinding(() -> this.get().conjugate(), this);
    }

    default Vector3fBinding rotate(ObservableVector3f vector) {
        return QuaternionBinding.rotate(this, vector);
    }

    default QuaternionBinding normalize() {
        return QuaternionBinding.normalize(this);
    }
}
