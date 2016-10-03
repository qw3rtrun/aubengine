package org.qw3rtrun.aub.engine.property.matrix;

import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public interface MatrixExpression extends ObservableMatrix {

    default Matrix4fBinding concat(ObservableMatrix matrix) {
        return Matrix4fBinding.multiply(this, matrix);
    }

    default Vector4fBinding product(ObservableValue<Vector4f> vector) {
        return new Vector4fBinding(() -> MatrixExpression.this.getValue().multiply(vector.getValue()), this, vector);
    }

    default Vector4fBinding product(Vector4f vector) {
        return new Vector4fBinding(() -> this.getValue().multiply(vector), this);
    }
}
