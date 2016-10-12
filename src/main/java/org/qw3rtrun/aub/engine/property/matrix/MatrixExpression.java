package org.qw3rtrun.aub.engine.property.matrix;

import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector4f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public interface MatrixExpression extends ObservableMatrix {

    default Matrix4fBinding concat(ObservableMatrix matrix) {
        return Matrix4fBinding.multiply(this, matrix);
    }

    default Vector4fBinding product(ObservableVector4f vector) {
        return new Vector4fBinding(() -> MatrixExpression.this.getValue().multiply(vector.getValue()), this, vector);
    }

    default Vector3fBinding product(ObservableVector3f vector) {
        return new Vector3fBinding(() -> MatrixExpression.this.getValue().multiply(vector.getValue()), this, vector);
    }

    default Vector4fBinding product(Vector4f vector) {
        return new Vector4fBinding(() -> this.getValue().multiply(vector), this);
    }

    default Vector3fBinding product(Vector3f vector) {
        return new Vector3fBinding(() -> this.getValue().multiply(vector), this);
    }
}
