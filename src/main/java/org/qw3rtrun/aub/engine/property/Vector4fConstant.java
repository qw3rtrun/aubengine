package org.qw3rtrun.aub.engine.property;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

/**
 * Created by strunov on 9/8/2015.
 */
public class Vector4fConstant implements ObservableValue<Vector4f> {

    public static Vector4fConstant CONST_X = new Vector4fConstant(X);
    public static Vector4fConstant CONST_Y = new Vector4fConstant(Y);
    public static Vector4fConstant CONST_Z = new Vector4fConstant(Z);

    private final Vector4f v;

    public Vector4fConstant(Vector4f v) {
        this.v = v;
    }

    @Override
    public void addListener(ChangeListener<? super Vector4f> listener) {
    }

    @Override
    public void removeListener(ChangeListener<? super Vector4f> listener) {
    }

    @Override
    public Vector4f getValue() {
        return v;
    }

    @Override
    public void addListener(InvalidationListener listener) {
    }

    @Override
    public void removeListener(InvalidationListener listener) {
    }
}
