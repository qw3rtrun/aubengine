package org.qw3rtrun.aub.engine.vectmath;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.*;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

/**
 * Created by strunov on 7/8/2016.
 */
public class Matrix4fTest {

    @Test
    public void add() throws Exception {
        assertThat(rows(XYZW, XYZW, XYZW, XYZW).add(rows(X, Y, Z, W)),
                nearTo(matr(
                        2, 1, 1, 1,
                        1, 2, 1, 1,
                        1, 1, 2, 1,
                        1, 1, 1, 2
                )));
    }

    @Test
    public void multiply() throws Exception {
        assertThat(E.multiply(vect4f(1, 2, 3, 4)), nearTo(vect4f(1, 2, 3, 4)));
        assertThat(rows(XY, YZW, Z, XZ).multiply(vect4f(1, 2, 3, 4)),
                nearTo(vect4f(3, 9, 3, 4)));
    }

    @Test
    public void transpose() throws Exception {
        assertThat(matr(
                2, 1, 1, 1,
                1, 2, 1, 1,
                1, 1, 2, 1,
                1, 1, 1, 2
        ).transpose(), nearTo(matr(
                2, 1, 1, 1,
                1, 2, 1, 1,
                1, 1, 2, 1,
                1, 1, 1, 2
        )));

        assertThat(matr(
                1, 1, 1, 1,
                2, 1, 1, 1,
                2, 3, 1, 1,
                2, 3, 4, 1
        ).transpose(), nearTo(matr(
                1, 2, 2, 2,
                1, 1, 3, 3,
                1, 1, 1, 4,
                1, 1, 1, 1
        )));
    }

    @Test
    public void multiplyFloat() throws Exception {
        assertThat(matr(
                2, 1, 1, 1,
                1, 2, 1, 1,
                1, 1, 2, 1,
                1, 1, 1, 2
        ).multiply(2), nearTo(matr(
                4, 2, 2, 2,
                2, 4, 2, 2,
                2, 2, 4, 2,
                2, 2, 2, 4
        )));
    }

    @Test
    public void multiplyMatr() throws Exception {
        assertThat(matr(
                2, 1, 1, 1,
                1, 2, 1, 1,
                1, 1, 2, 1,
                1, 1, 1, 2
        ).multiply(E), nearTo(matr(
                2, 1, 1, 1,
                1, 2, 1, 1,
                1, 1, 2, 1,
                1, 1, 1, 2
        )));
    }

    @Test
    public void checkDeterminant() {
        assertThat(E.determinant(), nearTo(1f));
        assertThat(rows(XY, YZ, ZW, XZ).determinant(), nearTo(-2f));
    }

}