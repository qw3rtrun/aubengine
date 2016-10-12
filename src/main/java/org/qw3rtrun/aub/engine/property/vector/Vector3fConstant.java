package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import static org.qw3rtrun.aub.engine.vectmath.Vector3f.*;

public class Vector3fConstant implements ObservableVector3f, Vector3fExpression {

    public static final Vector3fConstant CONST_0 = new Vector3fConstant(ZERO);
    public static final Vector3fConstant CONST_X = new Vector3fConstant(X);
    public static final Vector3fConstant CONST_Y = new Vector3fConstant(Y);
    public static final Vector3fConstant CONST_Z = new Vector3fConstant(Z);
    public static final Vector3fConstant CONST_XY = new Vector3fConstant(XY);

    private final Vector3f v;

    public Vector3fConstant() {
        this(vect3f());
    }

    public Vector3fConstant(Vector3f v) {
        this.v = v;
    }

    public static Vector3fConstant vect3fc(float x, float y, float z) {
        return new Vector3fConstant(vect3f(x, y, z));
    }

    public static Vector3fConstant vect3fc() {
        return new Vector3fConstant(vect3f());
    }

    public static Vector3fConstant vect3fc(Vector3f v) {
        return new Vector3fConstant(v);
    }

    @Override
    public void addListener(ChangeListener<? super Vector3f> listener) {
    }

    @Override
    public void removeListener(ChangeListener<? super Vector3f> listener) {
    }

    @Override
    public Vector3f getValue() {
        return v;
    }

    @Override
    public float getX() {
        return v.x;
    }

    @Override
    public float getY() {
        return v.y;
    }

    @Override
    public float getZ() {
        return v.z;
    }

    @Override
    public float getLength() {
        return v.length();
    }

    @Override
    public void addListener(InvalidationListener listener) {
    }

    @Override
    public void removeListener(InvalidationListener listener) {
    }

    @Override
    public String toString() {
        return "Vector3fConstant{" +
                "v=" + v +
                '}';
    }
}
