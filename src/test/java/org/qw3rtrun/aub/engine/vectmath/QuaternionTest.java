package org.qw3rtrun.aub.engine.vectmath;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.*;

public class QuaternionTest {
    @Test
    public void productAll() throws Exception {
        assertThat(Quaternion.productAll(QX1, QY1, QZ1, QXYZ1),
                nearTo(quaternion(0, 0, 4, -4)));
    }

    @Test
    public void addAll() throws Exception {
        assertThat(Quaternion.addAll(QX1, QY1, QZ1, QXYZ1),
                nearTo(quaternion(2, 2, 2, 4)));
    }

    @Test
    public void conjugate() throws Exception {
        assertThat(quaternion(1, 0, 1, 1).conjugate(), nearTo(quaternion(-1, 0, -1, 1)));
        assertThat(quaternion().conjugate(), nearTo(quaternion()));
    }

    @Test
    public void reciprocal() throws Exception {
        assertThat(QX1.reciprocal(), nearTo(quaternion(-0.5f, 0, 0, 0.5f)));
        assertThat(quaternion(3, 1, 1, 0.5f).reciprocal(),
                nearTo(quaternion(-0.26666668f, -0.08888889f, -0.08888889f, 0.044444446f)));
    }

    @Test
    public void normalize() throws Exception {
        assertThat(Q1.normalize(), nearTo(Q1));
        assertThat(quaternion(5, 5, 5, 5).normalize(), nearTo(QXYZ1.multiply(0.5f)));
    }

    @Test
    public void multiply() throws Exception {
        assertThat(QXYZ1.multiply(1), nearTo(QXYZ1));
        assertThat(Q0.multiply(1), nearTo(Q0));
        assertThat(quaternion(1, 0.5f, 0, -2).multiply(2), nearTo(quaternion(2, 1, 0, -4)));
    }

    @Test
    public void add() throws Exception {
        assertThat(QXYZ1.add(Q0), nearTo(QXYZ1));
        assertThat(quaternion(1, 2, 3, 4).add(QXYZ1), nearTo(quaternion(2, 3, 4, 5)));
    }

    @Test
    public void subtract() throws Exception {

    }

    @Test
    public void product() throws Exception {
        assertThat(QX0.product(QY0), nearTo(QZ0));
        assertThat(QX0.product(Q1), nearTo(QX0));
        assertThat(QXYZ1.product(Q1), nearTo(QXYZ1));
        assertThat(QXYZ1.product(QXYZ0), nearTo(quaternion(1, 1, 1, -3)));
        assertThat(quaternion(2, 1.5f, -1, 2.5f).product(quaternion(1, -1, 0, 2)), nearTo(quaternion(5.5f, -0.5f, -5.5f, 4.5f)));
        assertThat(QX1.product(QY1).product(QZ1).product(QXYZ1),
                nearTo(quaternion(0, 0, 4, -4)));
    }

    @Test
    public void norm() throws Exception {
        assertThat(Q0.norm(), equalTo(0f));
        assertThat(Q1.norm(), equalTo(1f));
        assertThat(QX0.norm(), equalTo(1f));
        assertThat(QY0.norm(), equalTo(1f));
        assertThat(QZ0.norm(), equalTo(1f));

        assertThat(QXYZ1.norm(), equalTo(2f));
        assertThat(quaternion(2, -1, 0, 4).norm(), equalTo(4.582576f));
    }

    @Test
    public void rotateMatrix() throws Exception {

    }

    @Test
    public void rotateNormMatrix() throws Exception {

    }

    @Test
    public void isUnit() throws Exception {

    }

    @Test
    public void asVector() throws Exception {

    }

}

