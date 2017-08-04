package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static org.qw3rtrun.aub.engine.vectmath.MathUtils.cos;
import static org.qw3rtrun.aub.engine.vectmath.MathUtils.sin;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;

public class Vector3f implements Serializable, Near<Vector3f> {
    public static Vector3f ZERO = new Vector3f(0, 0, 0);
    public static Vector3f X = new Vector3f(1, 0, 0);
    public static Vector3f Y = new Vector3f(0, 1, 0);
    public static Vector3f Z = new Vector3f(0, 0, 1);
    public static Vector3f XY = new Vector3f(1, 1, 0);
    public static Vector3f YZ = new Vector3f(0, 1, 1);
    public static Vector3f XZ = new Vector3f(1, 0, 1);
    public static Vector3f XYZ = new Vector3f(1, 1, 1);
    public final float x;
    public final float y;
    public final float z;

    private Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f vect3f() {
        return ZERO;
    }

    public static Vector3f vect3f(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    public Vector3f addAll(Vector3f... vectors) {
        switch (vectors.length) {
            case 0:
                return this;
            case 1:
                return add(vectors[0]);
            default:
                return asList(vectors).stream().reduce(this, Vector3f::add);
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

    public Vector3f x(float x) {
        return vect3f(x, this.y, this.z);
    }

    public Vector3f y(float y) {
        return vect3f(this.x, y, this.z);
    }

    public Vector3f z(float z) {
        return vect3f(this.x, this.y, z);
    }

    public Vector4f w(float w) {
        return Vector4f.vect4f(this.x, this.y, this.z);
    }

    public Vector3f inverse() {
        return vect3f(-x, -y, -z);
    }

    public Vector3f normalize() {
        return multiply(1 / length());
    }

    public Vector3f multiply(float k) {
        if (k == 0) return ZERO;
        if (k == 1) return this;
        return vect3f(k * x, k * y, k * z);
    }

    public Matrix4f multiply(Vector3f v) {
        return matr(x * v.x, x * v.y, x * v.z, 0,
                y * v.x, y * v.y, y * v.z, 0,
                z * v.x, z * v.y, z * v.z, 0,
                0, 0, 0, 0);
    }

    public Vector3f add(Vector3f v) {
        if (ZERO == v) return this;
        if (ZERO == this) return v;
        return vect3f(x + v.x, y + v.y, z + v.z);
    }

    public Vector3f add(float x, float y, float z) {
        return vect3f(this.x + x, this.y + y, this.z + z);
    }

    public Vector3f addX(float x) {
        return add(x, 0, 0);
    }

    public Vector3f addY(float y) {
        return add(0, y, 0);
    }

    public Vector3f addZ(float z) {
        return add(0, 0, z);
    }

    public Vector3f subtract(Vector3f v) {
        if (ZERO == v) return this;
        return vect3f(x - v.x, y - v.y, z - v.z);
    }


    public Vector3f subtract(float x, float y, float z) {
        return subtract(x, y, z);
    }

    public float dotProduct(Vector3f v) {
        if (v == ZERO || this == ZERO) return 0;
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3f product(Vector3f v) {
        return vect3f(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public float length() {
        return (float) Math.sqrt(dotProduct(this));
    }

    public float bound() {
        return Math.max(Math.max(abs(x), abs(y)), Math.max(abs(z), 0));
    }

    public float distanceBound(Vector3f v) {
        return Math.max(Math.max(abs(x - v.x), abs(y - v.y)), Math.max(abs(z - v.z), 0));
    }

    public float distance(Vector3f v) {
        return subtract(v).length();
    }

    public Quaternion toOrientation() {
        double t0 = cos(z * 0.5);
        double t1 = sin(z * 0.5);
        double t2 = cos(x * 0.5);
        double t3 = sin(x * 0.5);
        double t4 = cos(y * 0.5);
        double t5 = sin(y * 0.5);

        return Quaternion.quaternion(
                (float) (t0 * t3 * t4 - t1 * t2 * t5),
                (float) (t0 * t2 * t5 + t1 * t3 * t4),
                (float) (t1 * t2 * t4 - t0 * t3 * t5),
                (float) (t0 * t2 * t4 + t1 * t3 * t5)
        );
    }

    @Override
    public boolean isNearTo(Vector3f vect, double epsilon) {
        return distanceBound(vect) < abs(epsilon);
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3f vector4f = (Vector3f) o;

        return Float.compare(vector4f.x, x) == 0 && Float.compare(vector4f.y, y) == 0 && Float.compare(vector4f.z, z) == 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
