package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fConstant;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;

public class Identity implements Transformation {
    @Override
    public Vector4fBinding apply(ObservableVector source) {
        return Vector4fBinding.identity(source);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return Matrix4fBinding.identity(Matrix4fConstant.CONST_E);
    }

    @Override
    public Transformation invert() {
        return this;
    }
}
