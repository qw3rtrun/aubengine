package org.qw3rtrun.aub.engine.vectmath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.Random;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.*;

@SuppressWarnings("WeakerAccess")
@RunWith(Parameterized.class)
public class QuaternionAlgebraTest {

    private static Random random;
    @Parameterized.Parameter(0)
    public Quaternion a;
    @Parameterized.Parameter(1)
    public Quaternion b;
    @Parameterized.Parameter(2)
    public Quaternion c;
    @Parameterized.Parameter(3)
    public Float k;

    private static Quaternion random() {
        return quaternion(random.nextFloat() * 100, random.nextFloat() * 100, random.nextFloat() * 100, random.nextFloat() * 100);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        random = new Random();
        return asList(
                // Border conditions
                new Object[]{Q0, Q0, Q0, 0f},
                new Object[]{QZ1, Q0, Q0, 0f},
                new Object[]{Q0, QY1, Q0, 0f},
                new Object[]{Q0, Q0, QZ1, 0f},
                new Object[]{Q0, Q0, Q0, 1f},
                new Object[]{QXYZ1, Q0, Q0, 1f},
                new Object[]{Q0, QXYZ1, Q0, 1f},
                new Object[]{Q0, Q0, QXYZ1, 1f},
                new Object[]{QXYZ1, QXYZ1, QXYZ1, 1f},
                new Object[]{quaternion(3, 5, -3, 1), quaternion(0, -1.5f, 10, 4), quaternion(2, 2, 2, -0.5f), 3.56f},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()},
                new Object[]{random(), random(), random(), random.nextFloat()}
        );
    }

    @Test
    /**
     * a** = a
     */
    public void checkCommutativity() {
        assertThat(a.conjugate().conjugate(), nearTo(a));
        assertThat(b.conjugate().conjugate(), nearTo(b));
        assertThat(c.conjugate().conjugate(), nearTo(c));
    }

    @Test
    /**
     * ||q|| = sqrt(qq*)
     */
    public void checkNormAsProduct() {
        assertThat(a.product(a.conjugate()), nearTo(Q1.multiply(a.norm2())));
        assertThat(c.product(c.conjugate()), nearTo(Q1.multiply(c.norm2())));
    }

    @Test
    /**
     * ||ka|| = |k|*||a||
     */
    public void checkNormScalability() {
        assertThat(a.multiply(k).norm(), nearTo(abs(k) * a.norm()));
        assertThat(b.multiply(k).norm(), nearTo(abs(k) * b.norm()));
    }

    @Test
    /**
     * ||ab|| = ||a||*||b||
     */
    public void checkNormMultiply() {
        assertThat(a.product(b).norm(), nearTo(a.norm() * b.norm()));
        assertThat(b.product(c).norm(), nearTo(b.norm() * c.norm()));
    }


    @Test
    /**
     * qq-1 = 1
     */
    public void checkReciprocal() {
        if (a.norm2() > 0)
            assertThat(a.product(a.reciprocal()), nearTo(Q1));
        if (c.norm2() > 0)
            assertThat(c.product(c.reciprocal()), nearTo(Q1));
    }

    @Test
    public void productAll() {
        assertThat(Quaternion.productAll(a, b, c, QXYZ1),
                nearTo(a.product(b).product(c).product(QXYZ1)));
    }

    @Test
    public void addAll() {
        assertThat(Quaternion.addAll(a, b, c, QXYZ1),
                nearTo(a.add(b).add(c).add(QXYZ1)));
    }
}
