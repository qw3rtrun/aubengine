package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;
import java.util.Comparator;

import static java.util.Arrays.asList;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;

public class Vector4f implements Serializable, Near<Vector4f> {
    public static Vector4f ZERO = new Vector4f(0, 0, 0, 0);
    public static Vector4f W = new Vector4f(0, 0, 0, 1);
    public static Vector4f X = new Vector4f(1, 0, 0, 0);
    public static Vector4f XW = new Vector4f(1, 0, 0, 1);
    public static Vector4f Y = new Vector4f(0, 1, 0, 0);
    public static Vector4f YW = new Vector4f(0, 1, 0, 1);
    public static Vector4f Z = new Vector4f(0, 0, 1, 0);
    public static Vector4f ZW = new Vector4f(0, 0, 1, 1);
    public static Vector4f XY = new Vector4f(1, 1, 0, 0);
    public static Vector4f XYW = new Vector4f(1, 1, 0, 1);
    public static Vector4f YZ = new Vector4f(0, 1, 1, 0);
    public static Vector4f YZW = new Vector4f(0, 1, 1, 1);
    public static Vector4f XZ = new Vector4f(1, 0, 1, 0);
    public static Vector4f XZW = new Vector4f(1, 0, 1, 1);
    public static Vector4f XYZ = new Vector4f(1, 1, 1, 0);
    public static Vector4f XYZW = new Vector4f(1, 1, 1, 1);
    public final float x;
    public final float y;
    public final float z;
    public final float w;

    private Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Vector4f vect() {
        return ZERO;
    }

    public static Vector4f vect(float x, float y, float z) {
        return vect(x, y, z, 0);
    }

    public static Vector4f vect(float x, float y, float z, float w) {
//        if (w == 0) {
//            if (x == 0) {
//                if (y == 0) {
//                    if (z == 0) return ZERO;
//                    if (z == 1) return Z;
//                } else if (y == 1) {
//                    if (z == 0) return Y;
//                    if (z == 1) return YZ;
//                }
//            } else if (x == 1) {
//                if (y == 0) {
//                    if (z == 0) return X;
//                    if (z == 1) return XZ;
//                } else if (y == 1) {
//                    if (z == 0) return XY;
//                    if (z == 1) return XYZ;
//                }
//            }
//        } else if (w == 1) {
//            if (x == 0) {
//                if (y == 0) {
//                    if (z == 0) return W;
//                    if (z == 1) return ZW;
//                } else if (y == 1) {
//                    if (z == 0) return YW;
//                    if (z == 1) return YZW;
//                }
//            } else if (x == 1) {
//                if (y == 0) {
//                    if (z == 0) return XW;
//                    if (z == 1) return XZW;
//                } else if (y == 1) {
//                    if (z == 0) return XYW;
//                    if (z == 1) return XYZW;
//                }
//            }
//        }

        return new Vector4f(x, y, z, w);
    }

    public Vector4f addAll(Vector4f... vectors) {
        switch (vectors.length) {
            case 0:
                return this;
            case 1:
                return add(vectors[0]);
            default:
                return asList(vectors).stream().reduce(this, Vector4f::add);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    public Vector4f x(float x) {
        return vect(x, this.y, this.z, this.w);
    }

    public Vector4f y(float y) {
        return vect(this.x, y, this.z, this.w);
    }

    public Vector4f z(float z) {
        return vect(this.x, this.y, z, this.w);
    }

    public Vector4f w(float w) {
        return vect(this.x, this.y, this.z, w);
    }

    public Vector4f inverse() {
        return vect(-x, -y, -z);
    }

    public Vector4f normalize() {
        return multiply(1 / length());
    }

    public Vector4f multiply(float k) {
        if (k == 0) return ZERO;
        if (k == 1) return this;
        return vect(k * x, k * y, k * z);
    }

    public Matrix4f multiply(Vector4f v) {
        return matr(x * v.x, x * v.y, x * v.z, x * v.w,
                y * v.x, y * v.y, y * v.z, y * v.w,
                z * v.x, z * v.y, z * v.z, z * v.w,
                w * v.x, w * v.y, w * v.z, w * v.w);
    }

    public Vector4f add(Vector4f v) {
        if (ZERO == v) return this;
        if (ZERO == this) return v;
        return vect(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    public Vector4f add(float x, float y, float z, float w) {
        return vect(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Vector4f add(float x, float y, float z) {
        return add(x, y, z, 0);
    }

    public Vector4f addX(float x) {
        return add(x, 0, 0, 0);
    }

    public Vector4f addY(float y) {
        return add(0, y, 0, 0);
    }

    public Vector4f addZ(float z) {
        return add(0, 0, z, 0);
    }

    public Vector4f addW(float w) {
        return add(0, 0, 0, w);
    }

    public Vector4f subtract(Vector4f v) {
        if (ZERO == v) return this;
        return vect(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    public Vector4f subtract(float x, float y, float z, float w) {
        return add(-x, -y, -z, -w);
    }

    public Vector4f subtract(float x, float y, float z) {
        return subtract(x, y, z, 0);
    }

    public float dotProduct(Vector4f v) {
        if (v == ZERO || this == ZERO) return 0;
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    public Vector4f product(Vector4f v) {
        return vect(z * v.y - y * v.z, x * v.z - z * v.x, y * v.x - x * v.y, 0);
    }

    public float length() {
        return (float) Math.sqrt(dotProduct(this));
    }

    public float bound() {
        return Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.max(Math.abs(z), Math.abs(w)));
    }

    public float distanceBound(Vector4f v) {
        return Math.max(Math.max(Math.abs(x - v.x), Math.abs(y - v.y)), Math.max(Math.abs(z - v.z), Math.abs(w - v.w)));
    }

    public float distance(Vector4f v) {
        return subtract(v).length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector4f vector4f = (Vector4f) o;

        return Float.compare(vector4f.w, w) == 0 && Float.compare(vector4f.x, x) == 0 && Float.compare(vector4f.y, y) == 0 && Float.compare(vector4f.z, z) == 0;

    }

    @Override
    public boolean isNearTo(Vector4f vect, float epsilon) {
        return distanceBound(vect) < epsilon;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

}
