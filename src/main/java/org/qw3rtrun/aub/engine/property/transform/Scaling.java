package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector3f.vect3f;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Scaling extends Transformation.AbstractTransformation {

    private final ObservableVector3f scale;

    public Scaling(ObservableVector3f scale) {
        super();
        this.scale = scale;
    }

    public Scaling(Transformation chain, ObservableVector3f scale) {
        super(chain);
        this.scale = scale;
    }

    @Override
    public Vector3fBinding apply(ObservableVector3f source) {
        Vector3fBinding apply = super.apply(source);
        return new Vector3fBinding(() -> vect3f(
                scale.getX() * apply.getX(),
                scale.getY() * apply.getY(),
                scale.getZ() * apply.getZ()
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
                                W.multiply(1)
                        ), scale
                ),
                super.asMatrix());
    }

    @Override
    protected Transformation invert(Transformation chain) {
        return getChain().invert(new Scaling(new Vector3fBinding(() -> vect3f(
                scale.getX() != 0 ? 1 / scale.getX() : Float.POSITIVE_INFINITY,
                scale.getY() != 0 ? 1 / scale.getY() : Float.POSITIVE_INFINITY,
                scale.getZ() != 0 ? 1 / scale.getZ() : Float.POSITIVE_INFINITY
        ), scale)));
    }

    @Override
    public Scaling scale(ObservableVector3f scale) {
        return new Scaling(getChain(), new Vector3fBinding(
                () -> vect3f(
                        this.scale.getX() * scale.getX(),
                        this.scale.getY() * scale.getY(),
                        this.scale.getZ() * scale.getZ()
                ), this.scale, scale)
        );
    }
}
