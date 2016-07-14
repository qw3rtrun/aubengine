package org.qw3rtrun.aub.engine.vectmath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.Random;

import static java.lang.Double.max;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.*;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

@RunWith(Parameterized.class)
public class Matrix4fAlgebraTest {

    private static Random random;
    @Parameterized.Parameter(0)
    public Matrix4f a;
    @Parameterized.Parameter(1)
    public Matrix4f b;
    @Parameterized.Parameter(2)
    public Vector4f c;
    @Parameterized.Parameter(3)
    public Float k;

    private static Vector4f randomV() {
        return vect(random.nextInt(100) / 10f, random.nextInt(100) / 10f, random.nextInt(100) / 10f, random.nextInt(100) / 10f);
    }

    private static Matrix4f randomM() {
        return rows(randomV(), randomV(), randomV(), randomV());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        random = new Random();
        return asList(
                // Border conditions
                new Object[]{O0, O0, ZERO, 0f},
                new Object[]{E, O0, ZERO, 0f},
                new Object[]{E, E, ZERO, 0f},
                new Object[]{O0, O0, XYZ, 0f},
                new Object[]{O0, O0, ZERO, 1f},
                new Object[]{E, E, XYZ, 1f},
                new Object[]{randomM(), randomM(), randomV(), random.nextFloat()},
                new Object[]{randomM(), randomM(), randomV(), random.nextFloat()},
                new Object[]{randomM(), randomM(), randomV(), random.nextFloat()},
                new Object[]{randomM(), randomM(), randomV(), random.nextFloat()},
                new Object[]{randomM(), randomM(), randomV(), random.nextFloat()}
        );
    }

    @Test
    /**
     * E*M = M
     * E*V = V
     */
    public void checkIdentity() {
        assertThat(E.multiply(a), nearTo(a));
        assertThat(b.multiply(E), nearTo(b));
        assertThat(E.multiply(c), nearTo(c));
    }

    @Test
    /**
     * (A*B)*V = A*(B*V)
     */
    public void checkMultipleChain() {
        assertThat(a.multiply(b).multiply(c), nearTo(a.multiply(b.multiply(c))));
    }

    @Test
    /**
     * det(A*B) = det(A) * det(B)
     */
    public void checkDeterminant() {
        double determinant = a.multiply(b).determinant();
        assertThat(determinant, nearTo(a.determinant() * b.determinant(), max(abs(determinant) * 0.001, 0.000001)));
    }
}
