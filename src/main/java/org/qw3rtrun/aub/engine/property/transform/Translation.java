package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Translation extends Transformation.AbstractTransformation {

    private final ObservableVector3f translation;

    public Translation(ObservableVector3f translation) {
        this.translation = translation;
    }

    public Translation(Transformation chain, ObservableVector3f translation) {
        super(chain);
        this.translation = translation;
    }

    @Override
    public Vector3fBinding apply(ObservableVector3f source) {
        Vector3fBinding apply = super.apply(source);
        return Vector3fBinding.add(apply, translation);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return Matrix4fBinding.multiply(
                new Matrix4fBinding(() -> Matrix4f.cols(X, Y, Z, translation.getValue().w(1)), translation),
                super.asMatrix()
        );
    }

    @Override
    protected Transformation invert(Transformation chain) {
        return getChain().invert(new Translation(Vector3fBinding.inverse(translation)));
    }

    @Override
    public Translation translate(ObservableVector3f translation) {
        return new Translation(getChain(),
                new Vector3fBinding(() -> Vector3f.vect3f(
                        this.translation.getX() + translation.getX(),
                        this.translation.getX() + translation.getY(),
                        this.translation.getX() + translation.getZ()
                ), this.translation, translation));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Translation)) return false;
        if (!super.equals(o)) return false;

        Translation that = (Translation) o;

        return translation != null ? translation.equals(that.translation) : that.translation == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        return result;
    }
}
