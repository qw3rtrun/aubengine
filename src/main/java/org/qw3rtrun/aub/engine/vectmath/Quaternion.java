package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;

import static java.util.Arrays.asList;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

public class Quaternion implements Serializable, Near<Quaternion> {

    public static final Quaternion Q1 = new Quaternion(0, 0, 0, 1);
    public static final Quaternion Q0 = new Quaternion(0, 0, 0, 0);

    public static final Quaternion QX0 = new Quaternion(1, 0, 0, 0);
    public static final Quaternion QX1 = new Quaternion(1, 0, 0, 1);
    public static final Quaternion QY0 = new Quaternion(0, 1, 0, 0);
    public static final Quaternion QY1 = new Quaternion(0, 1, 0, 1);
    public static final Quaternion QZ0 = new Quaternion(0, 0, 1, 0);
    public static final Quaternion QZ1 = new Quaternion(0, 0, 1, 1);

    public static final Quaternion QXYZ0 = new Quaternion(1, 1, 1, 0);
    public static final Quaternion QXYZ1 = new Quaternion(1, 1, 1, 1);

    public final float x;
    public final float y;
    public final float z;
    public final float a;

    private Quaternion(float x, float y, float z, float a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }

    public static Quaternion quaternion(float x, float y, float z, float a){
        return new Quaternion(x, y, z, a);
    }

    public static Quaternion quaternion(Vector4f v){
        return new Quaternion(v.x, v.y, v.z, v.w);
    }

    public static Quaternion quaternion() {
        return Q1;
    }

    public static Quaternion quaternion(Vector4f v, float a) {
        return new Quaternion(v.x, v.y, v.z, a);
    }

    public static Quaternion productAll(Quaternion... vectors) {
        return asList(vectors).stream().reduce(new Quaternion(0, 0, 0, 1), Quaternion::product);
    }

    public static Quaternion addAll(Quaternion... vectors) {
        return asList(vectors).stream().reduce(new Quaternion(0, 0, 0, 0), Quaternion::add);
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

    public float getA() {
        return a;
    }

    public Quaternion withX(float x) {
        return quaternion(x, this.y, this.z, this.a);
    }

    public Quaternion withY(float y) {
        return quaternion(this.x, y, this.z, this.a);
    }

    public Quaternion withZ(float z) {
        return quaternion(this.x, this.y, z, this.a);
    }

    public Quaternion withA(float a) {
        return quaternion(this.x, this.y, this.z, a);
    }

    public Quaternion withVector(Vector4f v) {
        return quaternion(v.x, v.y, v.z, a);
    }

    public Quaternion conjugate() {
        return new Quaternion(-1 * x, -1 * y, -1 * z, a);
    }

    public Quaternion reciprocal() {
        return conjugate().multiply(1 / (norm2()));
    }

    public Quaternion normalize() {
        return multiply(1 / norm());
    }

    public Quaternion multiply(float k) {
        return new Quaternion(k * x, k * y, k * z, k* a);
    }

    public Quaternion add(Quaternion v) {
        return new Quaternion(x + v.x, y + v.y, z + v.z, a + v.a);
    }

    public Quaternion subtract(Quaternion v) {
        return new Quaternion(x - v.x, y - v.y, z - v.z, a - v.a);
    }

    public Quaternion product(Quaternion v) {
        return new Quaternion(
                a * v.x + x * v.a + y * v.z - z * v.y,
                a * v.y - x * v.z + y * v.a + z * v.x,
                a * v.z + x * v.y - y * v.x + z * v.a,
                a * v.a - x * v.x - y * v.y - z * v.z);
    }

    public float norm() {
        return (float) Math.sqrt(norm2());
    }

    public float norm2() {
        return x * x + y * y + z * z + a * a;
    }

    public float distance(Quaternion q) {
        return this.subtract(q).norm();
    }

    public float distance2(Quaternion q) {
        return this.subtract(q).norm();
    }

    public Matrix4f rotateMatrix() {
        double C = Math.cos(a);
        double S = Math.sin(a);
        double iC = 1 - C;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        return matr(
                (float) (x2 + (1 - x2) * C), (float) (iC * x * y - z * S), (float) (iC * x * z + y * S),
                (float) (iC * x * y + z * S), (float) (y2 + (1 - y2) * C), (float) (iC * y * z - x * S),
                (float) (iC * x * z - y * S), (float) (iC * y * z + x * S), (float) (z2 + (1 - z2) * C));
    }

    public Matrix4f rotateNormMatrix() {
        return matr(
                1 - 2 * y * y - 2 * z * z, 2 * (x * y - z * a), 2 * (x * z + y * a),
                2 * (x * y + z * a), 1 - 2 * x * x - 2 * z * z, 2 * (y * z - x * a),
                2 * (x * z - y * a), 2 * (y * z + x * a), 1 - 2 * x * x - 2 * y * y);
    }

    public boolean isUnit() {
        return norm() == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quaternion vector4f = (Quaternion) o;

        return Float.compare(vector4f.a, a) == 0 && Float.compare(vector4f.x, x) == 0 && Float.compare(vector4f.y, y) == 0 && Float.compare(vector4f.z, z) == 0;
    }

    @Override
    public float bound() {
        return MathUtils.max(x, y, z, a);
    }

    @Override
    public boolean isNearTo(Quaternion o, double epsilon) {
        return asVector().isNearTo(o.asVector(), epsilon);
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        result = 31 * result + (a != +0.0f ? Float.floatToIntBits(a) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "q(" + x + ", " + y + ", " + z + ", " + a + ")";
    }

    public Vector4f asVector() {
        return vect(x, y, z, a);
    }
}
