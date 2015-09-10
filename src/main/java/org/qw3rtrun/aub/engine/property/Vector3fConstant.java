package org.qw3rtrun.aub.engine.property;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

/**
 * Created by strunov on 9/8/2015.
 */
public class Vector3fConstant implements ObservableValue<Vector4f> {

    public static Vector3fConstant CONST_X = new Vector3fConstant(vX);
    public static Vector3fConstant CONST_Y = new Vector3fConstant(vY);
    public static Vector3fConstant CONST_Z = new Vector3fConstant(vZ);

    private final Vector4f v;

    public Vector3fConstant(Vector4f v) {
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
