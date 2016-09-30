package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Scaling extends Transformation.AbstractTransformation {

    private final ObservableVector scale;

    public Scaling(ObservableVector scale) {
        super();
        this.scale = scale;
    }

    public Scaling(Transformation chain, ObservableVector scale) {
        super(chain);
        this.scale = scale;
    }

    @Override
    public Vector4fBinding apply(ObservableVector source) {
        Vector4fBinding apply = super.apply(source);
        return new Vector4fBinding(() -> vect(
                scale.getX() * apply.getX(),
                scale.getY() * apply.getY(),
                scale.getZ() * apply.getZ(),
                scale.getW() * apply.getW()
        ), apply, scale);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return Matrix4fBinding.multiply(
                new Matrix4fBinding(
                        () -> Matrix4f.cols(
                                X.multiply(scale.getX()),
                                Y.multiply(scale.getY()),
                                Z.multiply(scale.getZ()),
                                W.multiply(scale.getW())
                        ), scale
                ),
                super.asMatrix());
    }

    @Override
    protected Transformation invert(Transformation chain) {
        return getChain().invert(new Scaling(new Vector4fBinding(() -> vect(
                scale.getX() != 0 ? 1 / scale.getX() : Float.POSITIVE_INFINITY,
                scale.getY() != 0 ? 1 / scale.getY() : Float.POSITIVE_INFINITY,
                scale.getZ() != 0 ? 1 / scale.getZ() : Float.POSITIVE_INFINITY,
                scale.getW() != 0 ? 1 / scale.getW() : Float.POSITIVE_INFINITY
        ), scale)));
    }

    @Override
    public Scaling scale(ObservableVector scale) {
        return new Scaling(getChain(), new Vector4fBinding(
                () -> vect(
                        this.scale.getX() * scale.getX(),
                        this.scale.getY() * scale.getY(),
                        this.scale.getZ() * scale.getZ(),
                        this.scale.getW() * scale.getW()
                ), this.scale, scale)
        );
    }
}
