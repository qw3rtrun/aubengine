package org.qw3rtrun.aub.engine.vectmath;

import org.junit.BeforeClass;
import org.junit.Test;
import org.qw3rtrun.aub.engine.Matchers;

import java.util.Random;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.EPSILON;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.rows;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class Vector4fTest {

    private static Random random;

    @BeforeClass
    public static void init() throws Exception {
        random = new Random();
    }

    @Test
    public void testComponent() {
        assertThat(vect(1, 3, -5, 5), nearTo(vect().x(1).y(3).z(-5).w(5)));
        assertThat(vect(1, 3, -5), nearTo(vect(1, 1, 1).add(0, 2, -6)));
        assertThat(vect(1, 3, -5), nearTo(vect(1, 1, 1).addX(0).addY(2).addZ(-6)));
        assertThat(vect(1, 3, -5, 5), nearTo(vect(1, 1, 1).addX(0).addY(2).addZ(-6).addW(5)));
    }

    @Test
    public void testBound() {
        assertEquals(0, ZERO.bound(), EPSILON);
        assertEquals(1, X.bound(), EPSILON);
        assertEquals(1, Y.bound(), EPSILON);
        assertEquals(1, Z.bound(), EPSILON);
        assertEquals(1, XY.bound(), EPSILON);
        assertEquals(1, XZ.bound(), EPSILON);
        assertEquals(1, YZ.bound(), EPSILON);
        assertEquals(1, XYZ.bound(), EPSILON);
        assertEquals(3, vect(3, -3, 3).bound(), EPSILON);
        assertEquals(3, vect(-1, -3, -3).bound(), EPSILON);
        assertEquals(100, vect(-1, -3, -3, 100).bound(), EPSILON);
        assertEquals(3, vect(-1, -3, -3, 1).bound(), EPSILON);
    }

    @Test
    public void testDistanceBound() {
        assertEquals(0, ZERO.distanceBound(ZERO), EPSILON);
        assertEquals(0, Y.distanceBound(Y), EPSILON);
        assertEquals(0, XZ.distanceBound(XZ), EPSILON);
        assertEquals(0, vect(5, -3, 4).distanceBound(vect(5, -3, 4)), EPSILON);
    }


    @Test
    public void testDotProduct() {
        assertEquals(0, ZERO.dotProduct(vect(5, 0, 1)), EPSILON);
        assertEquals(0, ZERO.dotProduct(ZERO), EPSILON);
        assertEquals(1, X.length(), EPSILON);
        assertEquals(1, Y.length(), EPSILON);
        assertEquals(1, Z.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), XY.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), XZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), YZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1 + 1), XYZ.length(), EPSILON);
    }

    @Test
    public void testLength() {
        assertEquals(0, ZERO.length(), EPSILON);
        assertEquals(1, X.length(), EPSILON);
        assertEquals(1, Y.length(), EPSILON);
        assertEquals(1, Z.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), XY.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), XZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), YZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1 + 1), XYZ.length(), EPSILON);
        assertEquals(Math.sqrt(9 + 9 + 9), vect(3, -3, 3).length(), EPSILON);
        assertEquals(Math.sqrt(1 + 9 + 9), vect(-1, -3, -3).length(), EPSILON);

        assertEquals(Math.sqrt(1 + 9 + 9 + 1), vect(-1, -3, -3, 1).length(), EPSILON);
        assertEquals(Math.sqrt(1 + 9 + 9 + 1), vect(-1, -3, -3, -1).length(), EPSILON);
    }

    @Test
    public void testEqual() {
        assertThat(ZERO, not(nearTo(X)));
        assertThat(X, not(nearTo(Y)));
        assertThat(X, not(nearTo(XY)));
        assertThat(ZERO, not(nearTo(YZ)));

        assertEquals(ZERO, vect(0, 0, 0));
        assertEquals(X, vect(1, 0, 0));
        assertEquals(X, vect(1, 0, -0));

        assertEquals(XW, vect(1, 0, 0, 1));
    }

    @Test
    public void testInverse() {
        assertThat(X.inverse(), nearTo(vect(-1, -0f, -0f)));
        assertThat(X.inverse(), nearTo(vect(-1, 0, 0)));
        assertThat(vect(-5, -4, -3).inverse(), nearTo(vect(5, 4, 3)));
    }


    @Test
    public void testSimpleScale() {
        Vector4f v = vect(1, 0, -2);
        assertThat(v.multiply(1), nearTo(v));
        assertThat(v.multiply(0), nearTo(ZERO));
        assertThat(v.multiply(2.5f), nearTo(vect(2.5f, 0, -5)));
        assertThat(v.multiply(-3f), nearTo(vect(-3, 0, 6)));
    }

    @Test
    public void testAddAll() {
        Vector4f v = vect(1, 0, -2);
        assertThat(v.addAll(), nearTo(v));
        assertThat(v.add(1, 2, 3), nearTo(v.addAll(vect(1, 2, 3))));
        assertThat(v.add(1, 2, 3).add(4, 2, 0), nearTo(v.addAll(vect(1, 2, 3), vect(4, 2, 0))));
    }

    @Test
    public void normalize() throws Exception {
        float norm = (float) (1 / Math.sqrt(3));
        assertThat(vect(norm, norm, norm), nearTo(vect(3, 3, 3).normalize()));
        assertThat(vect(norm, -norm, norm), nearTo(vect(3, -3, 3).normalize()));
    }

    @Test
    public void multiply() throws Exception {
        assertThat(X.multiply(Y), nearTo(rows(Y, ZERO, ZERO, ZERO)));
        assertThat(X.multiply(X), nearTo(rows(X, ZERO, ZERO, ZERO)));
        assertThat(Z.multiply(X), nearTo(rows(ZERO, ZERO, X, ZERO)));
        assertThat(XYZW.multiply(ZERO), nearTo(rows(ZERO, ZERO, ZERO, ZERO)));
        assertThat(ZERO.multiply(XYZW.inverse()), nearTo(rows(ZERO, ZERO, ZERO, ZERO)));
        assertThat(vect(1, 2, 3).multiply(vect(3, 2, 1)),
                nearTo(matr(3, 2, 1, 0,
                        6, 4, 2, 0,
                        9, 6, 3, 0,
                        0, 0, 0, 0)));
    }

    @Test
    public void product() {
        // Base right-hand coordinate system rule
        assertThat(X.product(Y), nearTo(Z));
        assertThat(Y.product(Z), nearTo(X));
        assertThat(Z.product(X), nearTo(Y));

        //Algebra
        checkProductionAlgebra(random(), random(), random(), random.nextFloat());
        checkProductionAlgebra(random(), random(), random(), random.nextFloat());
        checkProductionAlgebra(random(), random(), random(), random.nextFloat());
        checkProductionAlgebra(random(), random(), random(), random.nextFloat());
        checkProductionAlgebra(random(), random(), random(), random.nextFloat());
    }

    private void checkProductionAlgebra(Vector4f a, Vector4f b, Vector4f c, float k) {
        //[A, B] = -[B, A]
        assertThat(a.product(b), nearTo(b.product(a).inverse()));
        //[kA, b] = [A, kB] = k[B, B]
        assertThat(a.multiply(k).product(b), nearTo(a.product(b.multiply(k))));
        //[a+b, c] = [a, c] + [b, c];
        assertThat(a.add(b).product(c), nearTo(a.product(c).add(b.product(c))));
        //[a, a] = 0
        assertThat(a.product(a), nearTo(ZERO));
        assertThat(b.product(b), nearTo(ZERO));
        assertThat(c.product(c), nearTo(ZERO));
        //[a, [b, c]] = b(a, c) - c(a, b)
        assertThat(a.product(b.product(c)), nearTo(b.multiply(a.dotProduct(c)).subtract(c.multiply(a.dotProduct(b)))));
        // ([a, b], c) = (a, [b, c])
        assertEquals(a.product(b).dotProduct(c), a.dotProduct(b.product(c)), Matchers.EPSILON);
    }

    private Vector4f random() {
        return vect(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
    }
}

