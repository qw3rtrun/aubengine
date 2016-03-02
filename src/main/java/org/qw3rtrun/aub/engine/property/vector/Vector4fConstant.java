package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Vector4fConstant implements ObservableVector, VectorExpression {

    public static Vector4fConstant CONST_X = new Vector4fConstant(X);
    public static Vector4fConstant CONST_Y = new Vector4fConstant(Y);
    public static Vector4fConstant CONST_Z = new Vector4fConstant(Z);
    public static Vector4fConstant CONST_XY = new Vector4fConstant(XY);

    private final Vector4f v;

    public Vector4fConstant() {
        this(vect());
    }

    public Vector4fConstant(Vector4f v) {
        this.v = v;
    }

    public static Vector4fConstant vectConst(float x, float y, float z, float w) {
        return new Vector4fConstant(vect(x, y, z, w));
    }

    public static Vector4fConstant vectConst(float x, float y, float z) {
        return new Vector4fConstant(vect(x, y, z));
    }

    public static Vector4fConstant vectConst() {
        return new Vector4fConstant(vect());
    }

    public static Vector4fConstant vectConst(Vector4f v) {
        return new Vector4fConstant(v);
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
    public float getW() {
        return v.w;
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
}
