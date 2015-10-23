package org.qw3rtrun.aub.engine.vectmath;

import java.io.Serializable;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

/**
 *
 * Enter the Matrix...
 *
 * M = M.xx M.xy M.xz M.xw
 *     M.yx M.yy M.yz M.yw
 *     M.zx M.zy M.zz M.zw
 *     M.wx M.wy M.wz M.ww
 *
 * Matrix can be construct from rows ...
 *
 * rows (X, Y, Z, w) = X.x X.y X.z 0
 *                     Y.x Y.y Y.z 0
 *                     Z.x Z.y Z.z 0
 *                     0   0   0   w
 *
 * ... or from cols ...
 *
 * cols(X, Y, Z, w) = X.x Y.x Z.x 0
 *                    X.y Y.y Z.y 0
 *                    X.z Y.z Z.z 0
 *                    0   0   0   w
 *
 * The matrix can be multiply by the matrix ...
 *
 * M.multiply(V) = M.xx M.xy M.xz M.xw * V.x = M.xx*V.x + M.xy*V.y + M.xz*V.z + M.xw*V.w
 *                 M.yx M.yy M.yz M.yw   V.y   M.yx*V.x + M.yy*V.y + M.yz*V.z + M.yw*V.w
 *                 M.zx M.zy M.zz M.zw   V.z   M.zx*V.x + M.zy*V.y + M.zz*V.z + M.zw*V.w
 *                 M.wx M.wy M.wz M.ww   V.w   M.wx*V.x + M.wy*V.y + M.wz*V.z + M.ww*V.w
 *
 *... also the matrix can be multiply by another matrix ...
 *
 * M.multiply(A) = M.xx M.xy M.xz M.xw * A.xx A.xy A.xz A.xw =
 *                 M.yx M.yy M.yz M.yw   A.yx A.yy A.yz A.yw
 *                 M.zx M.zy M.zz M.zw   A.zx A.zy A.zz A.zw
 *                 M.wx M.wy M.wz M.ww   A.wx A.wy A.wz A.ww
 *
 * = M.xx*A.xx+M.xy*A.yx+M.xz*V.zx+M.xw*A.zw M.xx*A.xx+M.xy*A.yx+M.xz*V.zx+M.xw*A.zw
 *
 *
 *
 */


public class Matrix4f implements Serializable {
    public static final Matrix4f O0 = rows(vZERO, vZERO, vZERO, 0);
    public static final Matrix4f O1 = rows(vZERO, vZERO, vZERO, 1);
    public static final Matrix4f E = rows(vX, vY, vZ, 1);
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

    public Matrix4f(float xx, float xy, float xz, float xw, float yx, float yy, float yz, float yw, float zx, float zy, float zz, float zw, float wx, float wy, float wz, float ww) {
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

    public static Matrix4f transform(float xx, float xy, float xz,
                                     float yx, float yy, float yz,
                                     float zx, float zy, float zz,
                                     float ww) {
        return new Matrix4f(xx, xy, xz, 0, yx, yy, yz, 0, zx, zy, zz, 0, 0, 0, 0, ww);
    }

    public static Matrix4f rows(Vector4f x, Vector4f y, Vector4f z, Vector4f w) {
        return new Matrix4f(x.x, x.y, x.z, x.w, y.x, y.y, y.z, y.w, z.x, z.y, z.z, z.w, w.x, w.y, w.z, w.w);
    }

    public static Matrix4f rows(Vector4f x, Vector4f y, Vector4f z, float ww) {
        return new Matrix4f(x.x, x.y, x.z, 0, y.x, y.y, y.z, 0, z.x, z.y, z.z, 0, 0, 0, 0, ww);
    }

    public static Matrix4f cols(Vector4f x, Vector4f y, Vector4f z, float ww) {
        return new Matrix4f(x.x, y.x, z.x, 0, x.y, y.y, z.y, 0, x.z, y.z, z.z, 0, 0, 0, 0, ww);
    }

    public static Matrix4f cols(Vector4f x, Vector4f y, Vector4f z, Vector4f w) {
        return new Matrix4f(x.x, y.x, z.x, w.x, x.y, y.y, z.y, w.y, x.z, y.z, z.z, w.z, x.w, y.w, z.w, w.w);
    }

    public Matrix4f add(Matrix4f m) {
        return new Matrix4f(m.xx + xx, m.xy + xy, m.xz + xz, m.xw + xw,
                m.yx + yx, m.yy + yy, m.yz + yz, m.yw + yw,
                m.zx + zx, m.zy + zy, m.zz + zz, m.zw + zw,
                m.wx + wx, m.wy + wy, m.wz + wz, m.ww + ww);
    }

    public Matrix4f multiply(double a) {
        return new Matrix4f((float) a * xx, (float) (a * xy), (float) a * xz, (float) a * xw,
                (float) a * yx, (float) a * yy, (float) a * yz, (float) a * yw,
                (float) a * zx, (float) a * zy, (float) a * zz, (float) a * zw,
                (float) a * wx, (float) a * wy, (float) a * wz, (float) a * ww);
    }

    public Matrix4f transpose() {
        return new Matrix4f(xx, yx, zx, wx, xy, yy, zy, wy, xz, yz, zz, wz, xw, yw, zw, ww);
    }

    public Vector4f multiply(Vector4f v) {
        return vect(xx * v.x + xy * v.y + xz * v.z + xw, yx * v.x + yy * v.y + yz * v.z + yw, zx * v.x + zy * v.y + zz * v.z + zw);
    }

    public Matrix4f multiply(Matrix4f matrix4f){
        return null;
//        return new Vector4f(
//                xx*V.x + M.yx*V.y + M.zx*V.z + M.wx*V.w,
//                M.xy*V.x + M.yy*V.y + M.zy*V.z + M.wy*V.w,
//                M.xz*V.x + M.yz*V.y + M.zz*V.z + M.wz*V.w,
//                M.xw*V.x + M.yw*V.y + M.zw*V.z + M.ww*V.w);
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

