package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Translate implements Transformation {

    private final ObservableVector translation;

    public Translate(ObservableVector translation) {
        this.translation = translation;
    }

    @Override
    public Vector4fBinding apply(ObservableVector source) {
        return Vector4fBinding.add(source, translation);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return new Matrix4fBinding(() -> Matrix4f.cols(X, Y, Z, translation.getValue()), translation);
    }

    @Override
    public Transformation invert() {
        return new Translate(Vector4fBinding.inverse(translation));
    }
}
