package org.qw3rtrun.aub.engine.vectmath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.qw3rtrun.aub.engine.Matchers;

import java.util.Collection;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

@RunWith(Parameterized.class)
public class Vector4fAlgebraTest {

    private static Random random;
    @Parameterized.Parameter(0)
    public Vector4f a;
    @Parameterized.Parameter(1)
    public Vector4f b;
    @Parameterized.Parameter(2)
    public Vector4f c;
    @Parameterized.Parameter(3)
    public Float k;

    private static Vector4f random() {
        return vect4f(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        random = new Random();
        return asList(
                // Border conditions
                new Object[]{ZERO, ZERO, ZERO, 0f},
                new Object[]{X, ZERO, ZERO, 0f},
                new Object[]{ZERO, Y, ZERO, 0f},
                new Object[]{ZERO, ZERO, Z, 0f},
                new Object[]{ZERO, ZERO, ZERO, 1f},
                new Object[]{XYZ, ZERO, ZERO, 1f},
                new Object[]{ZERO, XYZ, ZERO, 1f},
                new Object[]{ZERO, ZERO, XYZ, 1f},
                new Object[]{XYZ, XYZ, XYZ, 1f},
                new Object[]{vect4f(3, 5, -3), vect4f(0, -1.5f, 10), vect4f(2, 2, 2), 3.56f},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()}
        );
    }

    @Test
    /**
     * [a, b] = -[b, a]
     */
    public void checkAntiCommutativity() {
        assertThat(a.product(b), nearTo(b.product(a).inverse()));
    }

    @Test
    /**
     * [a+b, c] = [a, c] + [b, c]
     */
    public void checkAdditionDistributivity() {
        assertThat(a.add(b).product(c), nearTo(a.product(c).add(b.product(c))));
    }

    @Test
    /**
     * [ka, b] = [a, kb] = k[a, b]
     */
    public void checkScalarScalability() {
        Vector4f product = a.multiply(k).product(b);
        assertThat(product, nearTo(a.product(b.multiply(k))));
        assertThat(product, nearTo(a.product(b).multiply(k)));
    }

    @Test
    /**
     * [a, a] = 0
     */
    public void checkZero() {
        assertThat(a.product(a), nearTo(ZERO));
        assertThat(b.product(b), nearTo(ZERO));
        assertThat(c.product(c), nearTo(ZERO));
    }

    @Test
    /**
     * [a, [b, c]] = b(a, c) - c(a, b)
     */
    public void checkTripleProduct() {
        assertThat(a.product(b.product(c)), nearTo(b.multiply(a.dotProduct(c)).subtract(c.multiply(a.dotProduct(b)))));
    }

    @Test
    /**
     * ([a, b], c) = (a, [b, c])
     */
    public void checkLagrangeIdentity() {
        assertEquals(a.product(b).dotProduct(c), a.dotProduct(b.product(c)), Matchers.EPSILON);
    }
}
