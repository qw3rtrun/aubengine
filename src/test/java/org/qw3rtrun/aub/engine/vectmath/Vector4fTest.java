package org.qw3rtrun.aub.engine.vectmath;

import junit.framework.AssertionFailedError;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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
    public void testBound() {
        assertEquals(0, vZERO.bound(), EPSILON);
        assertEquals(1, vX.bound(), EPSILON);
        assertEquals(1, vY.bound(), EPSILON);
        assertEquals(1, vZ.bound(), EPSILON);
        assertEquals(1, vXY.bound(), EPSILON);
        assertEquals(1, vXZ.bound(), EPSILON);
        assertEquals(1, vYZ.bound(), EPSILON);
        assertEquals(1, vXYZ.bound(), EPSILON);
        assertEquals(3, vect(3, -3, 3).bound(), EPSILON);
        assertEquals(3, vect(-1, -3, -3).bound(), EPSILON);
        assertEquals(3, point(-1, -3, -3, 100).bound(), EPSILON);
        assertEquals(3, point(-1, -3, -3, 1).bound(), EPSILON);
        assertEquals(3, vect(-1, -3, -3).point().bound(), EPSILON);
    }

    @Test
    public void testDistanceBound() {
        assertEquals(0, vZERO.distanceBound(vZERO), EPSILON);
        assertEquals(0, vY.distanceBound(vY), EPSILON);
        assertEquals(0, vXZ.distanceBound(vXZ), EPSILON);
        assertEquals(0, vect(5, -3, 4).distanceBound(vect(5, -3, 4)), EPSILON);
    }


    @Test
    public void testDotProduct() {
        assertEquals(0, vZERO.dotProduct(vect(5, 0, 1)), EPSILON);
        assertEquals(0, vZERO.dotProduct(vZERO), EPSILON);
        assertEquals(1, vX.length(), EPSILON);
        assertEquals(1, vY.length(), EPSILON);
        assertEquals(1, vZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), vXY.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), vXZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), vYZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1 + 1), vXYZ.length(), EPSILON);
    }

    @Test
    public void testLength() {
        assertEquals(0, vZERO.length(), EPSILON);
        assertEquals(1, vX.length(), EPSILON);
        assertEquals(1, vY.length(), EPSILON);
        assertEquals(1, vZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), vXY.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), vXZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1), vYZ.length(), EPSILON);
        assertEquals(Math.sqrt(1 + 1 + 1), vXYZ.length(), EPSILON);
        assertEquals(Math.sqrt(9 + 9 + 9), vect(3, -3, 3).length(), EPSILON);
        assertEquals(Math.sqrt(1 + 9 + 9), vect(-1, -3, -3).length(), EPSILON);

        assertEquals(Math.sqrt(1 + 9 + 9), point(-1, -3, -3, 100).length(), EPSILON);
        assertEquals(Math.sqrt(1 + 9 + 9), point(-1, -3, -3, 1).length(), EPSILON);
        assertEquals(Math.sqrt(1 + 9 + 9), point(-1, -3, -3, -1).length(), EPSILON);
    }

    @Test
    public void testEqual() {
        assertNotNear(vZERO, vX);
        assertNotNear(vX, vY);
        assertNotNear(vX, vXY);
        assertNotNear(vZERO, vYZ);

        assertSame(vZERO, vect(0, 0, 0));
        assertSame(vX, vect(1, 0, 0));
        assertSame(vX, vect(1, 0, -0));

        assertSame(pX, point(1, 0, 0, 1));
    }

    @Test
    public void testInverse() {
        assertEquals(vX.inverse(), vect(-1, -0f, -0f));
        assertNear(vX.inverse(), vect(-1, 0, 0));
        assertNear(vect(-5, -4, -3).inverse(), vect(5, 4, 3));
    }


    @Test
    public void testSimpleScale() {
        Vector4f v = vect(1, 0, -2);
        assertNear(v, v.multiply(1));
        assertNear(vZERO, v.multiply(0));
        assertNear(vect(2.5f, 0, -5), v.multiply(2.5f));
        assertNear(vect(-3, 0, 6), v.multiply(-3f));
    }


}
