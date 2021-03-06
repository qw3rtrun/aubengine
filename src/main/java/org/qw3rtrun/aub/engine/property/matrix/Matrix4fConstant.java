package org.qw3rtrun.aub.engine.property.matrix;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.E;

/**
 * Created by strunov on 9/23/2015.
 */
public class Matrix4fConstant extends ObservableMatrixBase {
    public static Matrix4fConstant CONST_E = new Matrix4fConstant(E);

    private final Matrix4f v;

    public Matrix4fConstant(Matrix4f v) {
        this.v = v;
    }

    @Override
    public void addListener(ChangeListener<? super Matrix4f> listener) {
    }

    @Override
    public void removeListener(ChangeListener<? super Matrix4f> listener) {
    }

    @Override
    public Matrix4f getValue() {
        return v;
    }

    @Override
    public void addListener(InvalidationListener listener) {
    }

    @Override
    public void removeListener(InvalidationListener listener) {
    }
}
