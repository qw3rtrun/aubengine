package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Translation extends Transformation.AbstractTransformation {

    private final ObservableVector translation;

    public Translation(ObservableVector translation) {
        this.translation = translation;
    }

    public Translation(Transformation chain, ObservableVector translation) {
        super(chain);
        this.translation = translation;
    }

    @Override
    public Vector4fBinding apply(ObservableVector source) {
        Vector4fBinding apply = super.apply(source);
        return Vector4fBinding.add(apply, translation);
    }

    @Override
    public Matrix4fBinding asMatrix() {
        return Matrix4fBinding.multiply(
                new Matrix4fBinding(() -> Matrix4f.cols(X, Y, Z, translation.getValue()), translation),
                super.asMatrix()
        );
    }

    @Override
    protected Transformation invert(Transformation chain) {
        return getChain().invert(new Translation(Vector4fBinding.inverse(translation)));
    }

    @Override
    public Translation translate(ObservableVector translation) {
        return new Translation(getChain(),
                new Vector4fBinding(() -> vect(
                        this.translation.getX() + translation.getX(),
                        this.translation.getX() + translation.getY(),
                        this.translation.getX() + translation.getZ(),
                        1
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
