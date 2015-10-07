package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;

import static java.util.Arrays.asList;

public class Vector4f implements Serializable {
    public static Vector4f vZERO = new Vector4f(0, 0, 0, 0);
    public static Vector4f pZERO = new Vector4f(0, 0, 0, 1);
    public static Vector4f vX = new Vector4f(1, 0, 0, 0);
    public static Vector4f pX = new Vector4f(1, 0, 0, 1);
    public static Vector4f vY = new Vector4f(0, 1, 0, 0);
    public static Vector4f pY = new Vector4f(0, 1, 0, 1);
    public static Vector4f vZ = new Vector4f(0, 0, 1, 0);
    public static Vector4f pZ = new Vector4f(0, 0, 1, 1);
    public static Vector4f vXY = new Vector4f(1, 1, 0, 0);
    public static Vector4f pXY = new Vector4f(1, 1, 0, 1);
    public static Vector4f vYZ = new Vector4f(0, 1, 1, 0);
    public static Vector4f pYZ = new Vector4f(0, 1, 1, 1);
    public static Vector4f vXZ = new Vector4f(1, 0, 1, 0);
    public static Vector4f pXZ = new Vector4f(1, 0, 1, 1);
    public static Vector4f vXYZ = new Vector4f(1, 1, 1, 0);
    public static Vector4f pXYZ = new Vector4f(1, 1, 1, 1);
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

    public static Vector4f vect(float x, float y, float z) {
        if (x == 0) {
            if (y == 0) {
                if (z == 0) return vZERO;
                if (z == 1) return vZ;
            } else if (y == 1) {
                if (z == 0) return vY;
                if (z == 1) return vYZ;
            }
        } else if (x == 1) {
            if (y == 0) {
                if (z == 0) return vX;
                if (z == 1) return vXZ;
            } else if (y == 1) {
                if (z == 0) return vXY;
                if (z == 1) return vXYZ;
            }
        }
        return new Vector4f(x, y, z, 0);
    }

    public static Vector4f point(float x, float y, float z, float w) {
        if (w == 1) {
            if (x == 0) {
                if (y == 0) {
                    if (z == 0) return pZERO;
                    if (z == 1) return pZ;
                } else if (y == 1) {
                    if (z == 0) return pY;
                    if (z == 1) return pYZ;
                }
            } else if (x == 1) {
                if (y == 0) {
                    if (z == 0) return pX;
                    if (z == 1) return pXZ;
                } else if (y == 1) {
                    if (z == 0) return pXY;
                    if (z == 1) return pXYZ;
                }
            }
        }
        return new Vector4f(x, y, z, w);
    }

    public static Vector4f sum(Vector4f... vectors) {
        switch (vectors.length) {
            case 0:
                return vZERO;
            case 1:
                return vectors[0];
            case 2:
                return vectors[0].add(vectors[1]);
            default:
                return asList(vectors).stream().reduce(vZERO, Vector4f::add);
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

    public boolean isVector() {
        return w == 0;
    }

    public boolean isPoint() {
        return w != 0;
    }

    public Vector4f vect() {
        return isVector() ? this : vect(x, y, z);
    }

    public Vector4f point() {
        return isPoint() ? this : new Vector4f(x, y, z, 1);
    }

    public Vector4f point(float w) {
        return w == this.w ? this : new Vector4f(x, y, z, w);
    }

    public Vector4f inverse() {
        return vect(-x, -y, -z);
    }

    public Vector4f normalize() {
        return vect().multiply(1 / length());
    }

    public Vector4f multiply(float k) {
        if (k == 0) return vZERO;
        if (k == 1) return this;
        return vect(k * x, k * y, k * z);
    }

    public Matrix4f multiply(Vector4f v) {
        return new Matrix4f(x * v.x, x * v.y, x * v.z, x * v.w,
                y * v.x, y * v.y, y * v.z, y * v.w,
                z * v.x, z * v.y, z * v.z, z * v.w,
                w * v.x, w * v.y, w * v.z, w * v.w);
    }

    public Vector4f multiply(Matrix4f m) {
        return new Vector4f(m.xx * x + m.yx * y + m.zx * z + m.wx * w,
                m.xy * x + m.yy * y + m.zy * z + m.wy * w,
                m.xz * x + m.yz * y + m.zz * z + m.wz * w,
                m.xw * x + m.yw * y + m.zw * z + m.ww * w);
    }

    public Vector4f add(Vector4f v) {
        if (vZERO == v) return this;
        if (vZERO == this) return v;
        return new Vector4f(x + v.x, y + v.y, z + v.z, w);
    }

    public Vector4f subtract(Vector4f v) {
        if (vZERO == v) return this;
        return new Vector4f(x - v.x, y - v.y, z - v.z, w);
    }

    public float dotProduct(Vector4f v) {
        if (v == vZERO || this == vZERO) return 0;
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector4f product(Vector4f v) {
        return vect(z * v.y - y * v.z, x * v.z - z * v.x, y * v.x - x * v.y);
    }

    public float length() {
        return (float) Math.sqrt(dotProduct(this));
    }

    public float bound() {
        return Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z));
    }

    public float distanceBound(Vector4f v) {
        return Math.max(Math.max(Math.abs(x - v.x), Math.abs(y - v.y)), Math.abs(z - v.z));
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
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + (isPoint() ? ", " + w + ")" : ")");
    }

}
