package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Vec2 implements Serializable, Near<Vec2> {

    public static Vec2 ZERO = new Vec2(0, 0);
    public static Vec2 X = new Vec2(1, 0);
    public static Vec2 Y = new Vec2(0, 1);
    public static Vec2 XY = new Vec2(1, 1);
    public final float x;
    public final float y;

    private Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 vec2() {
        return ZERO;
    }

    public static Vec2 vec2(float x, float y) {
        return new Vec2(x, y);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public Vec2 x(float x) {
        return vec2(x, this.y);
    }

    public Vec2 y(float y) {
        return vec2(this.x, y);
    }

    public Vec2 yx() {
        return new Vec2(this.y, this.x);
    }

    public Vec2 inverse() {
        return vec2(-x, -y);
    }

    public Vec2 normalize() {
        return multiply(1 / length());
    }

    public Vec2 addAll(Vec2... vectors) {
        switch (vectors.length) {
            case 0:
                return this;
            case 1:
                return add(vectors[0]);
            default:
                return Arrays.stream(vectors).reduce(this, Vec2::add);
        }
    }

    public Vec2 multiply(float k) {
        if (k == 0) return ZERO;
        if (k == 1) return this;
        return vec2(k * x, k * y);
    }

    public Vec2 add(Vec2 v) {
        if (ZERO == v) return this;
        if (ZERO == this) return v;
        return vec2(x + v.x, y + v.y);
    }

    public Vec2 add(float x, float y) {
        return vec2(this.x + x, this.y + y);
    }

    public Vec2 addX(float x) {
        return add(x, 0);
    }

    public Vec2 addY(float y) {
        return add(0, y);
    }

    public Vec2 subtract(Vec2 v) {
        if (ZERO == v) return this;
        return vec2(x - v.x, y - v.y);
    }

    public Vec2 subtract(float x, float y) {
        return vec2(this.x - x, this.y - y);
    }

    public float dotProduct(Vec2 v) {
        if (v == ZERO || this == ZERO) return 0;
        return x * v.x + y * v.y;
    }

    public float length() {
        return (float) Math.sqrt(dotProduct(this));
    }

    public float bound() {
        return Math.max(abs(x), abs(y));
    }

    public float distanceBound(Vec2 v) {
        return Math.max(abs(x - v.x), abs(y - v.y));
    }

    public float distance(Vec2 v) {
        return subtract(v).length();
    }

    public Vec2 lerp(Vec2 vec, float alpha) {
        return new Vec2((1 - alpha) * x + alpha * vec.x, (1 - alpha) * y + alpha * vec.y);
    }

    public Vec2 lerp(float x, float y, float alpha) {
        return new Vec2((1 - alpha) * this.x + alpha * x, (1 - alpha) * this.y + alpha * y);
    }

    public Vector3f xxx() {
        return Vector3f.vect3f(x, x, x);
    }

    public Vector3f xxy() {
        return Vector3f.vect3f(x, x, y);
    }

    public Vector3f xyx() {
        return Vector3f.vect3f(x, y, x);
    }

    public Vector3f xyy() {
        return Vector3f.vect3f(x, y, y);
    }

    public Vector3f yxx() {
        return Vector3f.vect3f(y, x, x);
    }

    public Vector3f yxy() {
        return Vector3f.vect3f(y, x, y);
    }

    public Vector3f yyx() {
        return Vector3f.vect3f(y, y, x);
    }

    public Vector3f yyy() {
        return Vector3f.vect3f(y, y, y);
    }

    @Override
    public boolean isNearTo(Vec2 vec, double epsilon) {
        return distanceBound(vec) < abs(epsilon);
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec2 vector4f = (Vec2) o;

        return Float.compare(vector4f.x, x) == 0 && Float.compare(vector4f.y, y) == 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
