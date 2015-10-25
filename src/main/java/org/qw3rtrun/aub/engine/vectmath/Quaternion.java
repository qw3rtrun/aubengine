package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;

import static java.util.Arrays.asList;

public class Quaternion implements Serializable {
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

    public static Quaternion quaternion(Vector4f v, float rad){
        return new Quaternion(v.x, v.y, v.z, rad);
    }

    public static Quaternion productAll(Quaternion... vectors) {
        return asList(vectors).stream().reduce(new Quaternion(1, 0, 0, 0), Quaternion::product);
    }

    public static Quaternion addAll(Quaternion... vectors) {
        return asList(vectors).stream().reduce(new Quaternion(1, 0, 0, 0), Quaternion::add);
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

    public Quaternion conjugate() {
        return new Quaternion (-x, -y, -z, a);
    }

    public Quaternion reciprocal() {
        float norm = norm();
        return conjugate().multiply(1/(norm*norm));
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
        return (float) Math.sqrt(x*x + y*y + z*z + a*a);
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

}
