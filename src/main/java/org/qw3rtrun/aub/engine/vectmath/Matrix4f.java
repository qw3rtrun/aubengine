package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;
import java.util.List;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

/**
 * Enter the Matrix...
 * <p>
 * M = M.xx M.xy M.xz M.xw
 * M.yx M.yy M.yz M.yw
 * M.zx M.zy M.zz M.zw
 * M.wx M.wy M.wz M.ww
 * <p>
 * Matrix can be construct from rows ...
 * <p>
 * rows (X, Y, Z, a) = X.x X.y X.z 0
 * Y.x Y.y Y.z 0
 * Z.x Z.y Z.z 0
 * 0   0   0   a
 * <p>
 * ... or from cols ...
 * <p>
 * cols(X, Y, Z, a) = X.x Y.x Z.x 0
 * X.y Y.y Z.y 0
 * X.z Y.z Z.z 0
 * 0   0   0   a
 * <p>
 * The matrix can be multiply by the matrix ...
 * <p>
 * M.multiply(V) = M.xx M.xy M.xz M.xw * V.x = M.xx*V.x + M.xy*V.y + M.xz*V.z + M.xw*V.a
 * M.yx M.yy M.yz M.yw   V.y   M.yx*V.x + M.yy*V.y + M.yz*V.z + M.yw*V.a
 * M.zx M.zy M.zz M.zw   V.z   M.zx*V.x + M.zy*V.y + M.zz*V.z + M.zw*V.a
 * M.wx M.wy M.wz M.ww   V.a   M.wx*V.x + M.wy*V.y + M.wz*V.z + M.ww*V.a
 * <p>
 * ... also the matrix can be multiply by another matrix ...
 * <p>
 * M.multiply(A) = M.xx M.xy M.xz M.xw * A.xx A.xy A.xz A.xw =
 * M.yx M.yy M.yz M.yw   A.yx A.yy A.yz A.yw
 * M.zx M.zy M.zz M.zw   A.zx A.zy A.zz A.zw
 * M.wx M.wy M.wz M.ww   A.wx A.wy A.wz A.ww
 * <p>
 * = M.x_*A._x M.x_*A._y M.x_*A._z M.x_*A._w
 * M.y_*A._x M.y_*A._y M.y_*A._z M.y_*A._w
 * M.z_*A._x M.z_*A._y M.z_*A._z M.z_*A._w
 * M.w_*A._x M.w_*A._y M.w_*A._z M.w_*A._w
 */

public class Matrix4f implements Near<Matrix4f>, Serializable {
    public static final Matrix4f O0 = rows(ZERO, ZERO, ZERO, ZERO);
    public static final Matrix4f O1 = rows(ZERO, ZERO, ZERO, W);
    public static final Matrix4f E = rows(X, Y, Z, W);
    public final float xx;
    public final float xy;
    public final float xz;
    public final float xw;
    public final float yx;
    public final float yy;
    public final float yz;
    public final float yw;
    public final float zx;
    public final float zy;
    public final float zz;
    public final float zw;
    public final float wx;
    public final float wy;
    public final float wz;
    public final float ww;

    private Matrix4f(float xx, float xy, float xz, float xw, float yx, float yy, float yz, float yw, float zx, float zy, float zz, float zw, float wx, float wy, float wz, float ww) {
        this.xx = xx;
        this.xy = xy;
        this.xz = xz;
        this.xw = xw;
        this.yx = yx;
        this.yy = yy;
        this.yz = yz;
        this.yw = yw;
        this.zx = zx;
        this.zy = zy;
        this.zz = zz;
        this.zw = zw;
        this.wx = wx;
        this.wy = wy;
        this.wz = wz;
        this.ww = ww;
    }

    public static Matrix4f matr(float xx, float xy, float xz, float xw, float yx, float yy, float yz, float yw, float zx, float zy, float zz, float zw, float wx, float wy, float wz, float ww) {
        return new Matrix4f(xx, xy, xz, xw, yx, yy, yz, yw, zx, zy, zz, zw, wx, wy, wz, ww);
    }

    public static Matrix4f matr(float xx, float xy, float xz,
                                float yx, float yy, float yz,
                                float zx, float zy, float zz,
                                float ww) {
        return new Matrix4f(xx, xy, xz, 0, yx, yy, yz, 0, zx, zy, zz, 0, 0, 0, 0, ww);
    }

    public static Matrix4f matr(float xx, float xy, float xz,
                                float yx, float yy, float yz,
                                float zx, float zy, float zz) {
        return new Matrix4f(xx, xy, xz, 0, yx, yy, yz, 0, zx, zy, zz, 0, 0, 0, 0, 1);
    }


    public static Matrix4f rows(Vector4f x, Vector4f y, Vector4f z, Vector4f w) {
        return new Matrix4f(x.x, x.y, x.z, x.w, y.x, y.y, y.z, y.w, z.x, z.y, z.z, z.w, w.x, w.y, w.z, w.w);
    }

    public static Matrix4f cols(Vector4f x, Vector4f y, Vector4f z, Vector4f w) {
        return new Matrix4f(x.x, y.x, z.x, w.x, x.y, y.y, z.y, w.y, x.z, y.z, z.z, w.z, x.w, y.w, z.w, w.w);
    }

    public Vector4f columnX() {
        return vect4f(xx, yx, zx, wx);
    }

    public Vector4f columnY() {
        return vect4f(xy, yy, zy, wy);
    }

    public Vector4f columnZ() {
        return vect4f(xz, yz, zz, wz);
    }

    public Vector4f columnW() {
        return vect4f(xw, yw, zw, ww);
    }

    public List<Vector4f> columns() {
        return asList(columnX(), columnY(),
                columnZ(), columnW());
    }

    public List<Vector4f> rows() {
        return asList(rowX(), rowY(), rowZ(), rowW());
    }

    private Vector4f rowW() {
        return vect4f(wx, wy, ww, wz);
    }

    private Vector4f rowZ() {
        return vect4f(zx, zy, zz, zw);
    }

    private Vector4f rowY() {
        return vect4f(yx, yy, yz, yw);
    }

    private Vector4f rowX() {
        return vect4f(xx, xy, xz, xw);
    }

    @Override
    public float bound() {
        return MathUtils.max(rowX().bound(), rowY().bound(), rowZ().bound(), rowW().bound());
    }

    public Matrix4f add(Matrix4f m) {
        return new Matrix4f(m.xx + xx, m.xy + xy, m.xz + xz, m.xw + xw,
                m.yx + yx, m.yy + yy, m.yz + yz, m.yw + yw,
                m.zx + zx, m.zy + zy, m.zz + zz, m.zw + zw,
                m.wx + wx, m.wy + wy, m.wz + wz, m.ww + ww);
    }

    public Matrix4f transpose() {
        return new Matrix4f(xx, yx, zx, wx, xy, yy, zy, wy, xz, yz, zz, wz, xw, yw, zw, ww);
    }

    public Matrix4f multiply(double a) {
        return new Matrix4f((float) a * xx, (float) (a * xy), (float) a * xz, (float) a * xw,
                (float) a * yx, (float) a * yy, (float) a * yz, (float) a * yw,
                (float) a * zx, (float) a * zy, (float) a * zz, (float) a * zw,
                (float) a * wx, (float) a * wy, (float) a * wz, (float) a * ww);
    }

    public Vector4f multiply(Vector4f v) {
        return vect4f(xx * v.x + xy * v.y + xz * v.z + xw * v.w,
                yx * v.x + yy * v.y + yz * v.z + yw * v.w,
                zx * v.x + zy * v.y + zz * v.z + zw * v.w,
                wx * v.x + wy * v.y + wz * v.z + ww * v.w);
    }

    public Vector3f multiply(Vector3f v) {
        return Vector3f.vect3f(xx * v.x + xy * v.y + xz * v.z + xw,
                yx * v.x + yy * v.y + yz * v.z + yw,
                zx * v.x + zy * v.y + zz * v.z + zw);
    }

    public Matrix4f multiply(Matrix4f m) {
        return new Matrix4f(
                xx * m.xx + xy * m.yx + xz * m.zx + xw * m.wx, xx * m.xy + xy * m.yy + xz * m.zy + xw * m.wy, xx * m.xz + xy * m.yz + xz * m.zz + xw * m.wz, xx * m.xw + xy * m.yw + xz * m.zw + xw * m.ww,
                yx * m.xx + yy * m.yx + yz * m.zx + yw * m.wx, yx * m.xy + yy * m.yy + yz * m.zy + yw * m.wy, yx * m.xz + yy * m.yz + yz * m.zz + yw * m.wz, yx * m.xw + yy * m.yw + yz * m.zw + yw * m.ww,
                zx * m.xx + zy * m.yx + zz * m.zx + zw * m.wx, zx * m.xy + zy * m.yy + zz * m.zy + zw * m.wy, zx * m.xz + zy * m.yz + zz * m.zz + zw * m.wz, zx * m.xw + zy * m.yw + zz * m.zw + zw * m.ww,
                wx * m.xx + wy * m.yx + wz * m.zx + ww * m.wx, wx * m.xy + wy * m.yy + wz * m.zy + ww * m.wy, wx * m.xz + wy * m.yz + wz * m.zz + ww * m.wz, wx * m.xw + wy * m.yw + wz * m.zw + ww * m.ww);
    }

    public double determinant() {
        return (xx * determinant(yy, yz, yw, zy, zz, zw, wy, wz, ww)
                - xy * determinant(yx, yz, yw, zx, zz, zw, wx, wz, ww)
                + xz * determinant(yx, yy, yw, zx, zy, zw, wx, wy, ww)
                - xw * determinant(yx, yy, yz, zx, zy, zz, wx, wy, wz));
    }

    private double determinant(double xx, double xy, double xz,
                               double yx, double yy, double yz,
                               double zx, double zy, double zz) {
        return xx * yy * zz + xy * yz * zx + xz * yx * zy - xz * yy * zx - xy * yx * zz - xx * yz * zy;
    }

    @Override
    public boolean isNearTo(Matrix4f o, double epsilon) {
        return vect4f(rowX().distanceBound(o.rowX()),
                rowY().distanceBound(rowY()),
                rowZ().distanceBound(rowZ()))
                .bound() < abs(epsilon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix4f matrix4f = (Matrix4f) o;

        if (Float.compare(matrix4f.ww, ww) != 0) return false;
        if (Float.compare(matrix4f.wx, wx) != 0) return false;
        if (Float.compare(matrix4f.wy, wy) != 0) return false;
        if (Float.compare(matrix4f.wz, wz) != 0) return false;
        if (Float.compare(matrix4f.xw, xw) != 0) return false;
        if (Float.compare(matrix4f.xx, xx) != 0) return false;
        if (Float.compare(matrix4f.xy, xy) != 0) return false;
        if (Float.compare(matrix4f.xz, xz) != 0) return false;
        if (Float.compare(matrix4f.yw, yw) != 0) return false;
        if (Float.compare(matrix4f.yx, yx) != 0) return false;
        if (Float.compare(matrix4f.yy, yy) != 0) return false;
        if (Float.compare(matrix4f.yz, yz) != 0) return false;
        if (Float.compare(matrix4f.zw, zw) != 0) return false;
        if (Float.compare(matrix4f.zx, zx) != 0) return false;
        if (Float.compare(matrix4f.zy, zy) != 0) return false;
        if (Float.compare(matrix4f.zz, zz) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (xx != +0.0f ? Float.floatToIntBits(xx) : 0);
        result = 31 * result + (xy != +0.0f ? Float.floatToIntBits(xy) : 0);
        result = 31 * result + (xz != +0.0f ? Float.floatToIntBits(xz) : 0);
        result = 31 * result + (xw != +0.0f ? Float.floatToIntBits(xw) : 0);
        result = 31 * result + (yx != +0.0f ? Float.floatToIntBits(yx) : 0);
        result = 31 * result + (yy != +0.0f ? Float.floatToIntBits(yy) : 0);
        result = 31 * result + (yz != +0.0f ? Float.floatToIntBits(yz) : 0);
        result = 31 * result + (yw != +0.0f ? Float.floatToIntBits(yw) : 0);
        result = 31 * result + (zx != +0.0f ? Float.floatToIntBits(zx) : 0);
        result = 31 * result + (zy != +0.0f ? Float.floatToIntBits(zy) : 0);
        result = 31 * result + (zz != +0.0f ? Float.floatToIntBits(zz) : 0);
        result = 31 * result + (zw != +0.0f ? Float.floatToIntBits(zw) : 0);
        result = 31 * result + (wx != +0.0f ? Float.floatToIntBits(wx) : 0);
        result = 31 * result + (wy != +0.0f ? Float.floatToIntBits(wy) : 0);
        result = 31 * result + (wz != +0.0f ? Float.floatToIntBits(wz) : 0);
        result = 31 * result + (ww != +0.0f ? Float.floatToIntBits(ww) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + '(' + xx + ", " + xy + ", " + xz + ", " + xw + "), " + '(' + yx + ", " + yy + ", " + yz + ", " + yw + "), " + '(' + zx + ", " + zy + ", " + zz + ", " + zw + "), " + '(' + wx + ", " + wy + ", " + wz + ", " + ww + "))";
    }
}

