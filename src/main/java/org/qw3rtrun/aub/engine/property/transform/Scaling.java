package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Scaling implements Transformation {

    private final ObservableVector scale;

    public Scaling(ObservableVector scale) {
        this.scale = scale;
    }

    @Override
    public Vector4fBinding apply(ObservableVector source) {
        return new Vector4fBinding(() -> vect(
                scale.getX() * source.getX(),
                scale.getY() * source.getY(),
                scale.getZ() * source.getZ(),
                scale.getW() * source.getW()
        ), source, scale);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return new Matrix4fBinding(() -> Matrix4f.cols(
                X.multiply(scale.getX()),
                Y.multiply(scale.getY()),
                Z.multiply(scale.getZ()),
                W.multiply(scale.getW())
        ), scale);
    }

    @Override
    public Transformation invert() {
        return new Scaling(new Vector4fBinding(() -> vect(
                scale.getX() != 0 ? 1 / scale.getX() : Float.POSITIVE_INFINITY,
                scale.getY() != 0 ? 1 / scale.getY() : Float.POSITIVE_INFINITY,
                scale.getZ() != 0 ? 1 / scale.getZ() : Float.POSITIVE_INFINITY,
                scale.getW() != 0 ? 1 / scale.getW() : Float.POSITIVE_INFINITY
        ), scale));
    }
}
