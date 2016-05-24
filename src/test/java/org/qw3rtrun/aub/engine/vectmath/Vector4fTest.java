package org.qw3rtrun.aub.engine.vectmath;

import junit.framework.AssertionFailedError;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

@RunWith(JMockit.class)
public class Vector4fTest {

    private static final float EPSILON = .000001f;

    public static void assertNear(Vector4f exp, Vector4f act) {
        if (exp.distanceBound(act) > EPSILON) throw new AssertionFailedError(exp + " <> " + act);
    }

    public static void assertNotNear(Vector4f exp, Vector4f act) {
        if (exp.distanceBound(act) <= EPSILON) throw new AssertionFailedError(exp + " == " + act);
    }

    @Test
    public void testComponent() {
        assertNear(vect(1, 3, -5, 5), vect().x(1).y(3).z(-5).w(5));
        assertNear(vect(1, 3, -5), vect(1, 1, 1).add(0, 2, -6));
        assertNear(vect(1, 3, -5), vect(1, 1, 1).addX(0).addY(2).addZ(-6));
        assertNear(vect(1, 3, -5, 5), vect(1, 1, 1).addX(0).addY(2).addZ(-6).addW(5));
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
        assertNotNear(ZERO, X);
        assertNotNear(X, Y);
        assertNotNear(X, XY);
        assertNotNear(ZERO, YZ);

        assertEquals(ZERO, vect(0, 0, 0));
        assertEquals(X, vect(1, 0, 0));
        assertEquals(X, vect(1, 0, -0));

        assertEquals(XW, vect(1, 0, 0, 1));
    }

    @Test
    public void testInverse() {
        assertEquals(X.inverse(), vect(-1, -0f, -0f));
        assertNear(X.inverse(), vect(-1, 0, 0));
        assertNear(vect(-5, -4, -3).inverse(), vect(5, 4, 3));
    }


    @Test
    public void testSimpleScale() {
        Vector4f v = vect(1, 0, -2);
        assertNear(v, v.multiply(1));
        assertNear(ZERO, v.multiply(0));
        assertNear(vect(2.5f, 0, -5), v.multiply(2.5f));
        assertNear(vect(-3, 0, 6), v.multiply(-3f));
    }

    @Test
    public void testAddAll(){
        Vector4f v = vect(1, 0, -2);
        assertNear(v, v.addAll());
        assertNear(v.add(1, 2, 3), v.addAll(vect(1, 2, 3)));
        assertNear(v.add(1, 2, 3).add(4, 2, 0), v.addAll(vect(1, 2, 3), vect(4, 2, 0)));
    }

    @Test
    public void normalize() throws Exception {
        float norm = (float) (1 / Math.sqrt(3));
        assertNear(vect(norm, norm, norm), vect(3, 3, 3).normalize());
        assertNear(vect(norm, -norm, norm), vect(3, -3, 3).normalize());
    }

    @Test
    public void multiply() throws Exception {
        //TODO
    }


}

