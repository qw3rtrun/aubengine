package org.qw3rtrun.aub.engine.property;

public interface MatrixExpression extends ObservableMatrix {

    default Matrix4fBinding concat(ObservableMatrix matrix) {
        return Bindings.multiply(this, matrix);
    }

    default Matrix4fBinding inversion() {
        //TODO
        return concat(Matrix4fConstant.CONST_E);
    }
}
