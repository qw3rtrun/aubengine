package org.qw3rtrun.aub.engine.property.quaternion;

import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.VectorExpression;

import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

public interface QuaternionExpression extends ObservableQuaternion {

    default QuaternionExpression conjugate() {
        return new QuaternionBinding(() -> this.get().conjugate(), this);
    }

    default QuaternionExpression product(ObservableQuaternion quaternion) {
        return new QuaternionBinding(() -> this.get().product(quaternion.getValue()).product(this.get().conjugate()),
                this, quaternion);
    }

    default VectorExpression rotate(ObservableVector vector) {
        return new Vector4fBinding(() -> this.get().product(quaternion(vector.getValue())).product(this.get().conjugate()).asVector(), QuaternionExpression.this, vector);
    }

}
