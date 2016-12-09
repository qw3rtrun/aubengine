package org.qw3rtrun.aub.engine.property.transform;

import com.sun.javafx.binding.FloatConstant;
import org.junit.Test;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.quaternion.Quaternion4fProperty;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fConstant;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.matrix.Matrix4fConstant.CONST_E;
import static org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding.axisRotation;
import static org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding.orientation;
import static org.qw3rtrun.aub.engine.property.vector.Vector3fConstant.CONST_Y;
import static org.qw3rtrun.aub.engine.property.vector.Vector3fConstant.vect3fc;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.Y;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.vect3f;

public class RotationTest {

    @Test
    public void testRotate() throws Exception {
        Vector3fProperty a = new Vector3fProperty(Vector3f.X);
        Quaternion4fProperty quat = new Quaternion4fProperty(
                Quaternion.quaternion(0, (float) Math.sin(Math.PI / 2), 0, (float) Math.cos(Math.PI / 2)));
        Vector3fBinding rotation = new Rotation(quat).apply(a);
        assertThat(rotation.getValue(), nearTo(a.get().inverse()));

        quat.setValue(Quaternion.quaternion(0, (float) Math.sin(Math.PI), 0, (float) Math.cos(Math.PI)));
        assertThat(rotation.getValue(), nearTo(a.get()));

        quat.setValue(Quaternion.quaternion(0, (float) Math.sin(100 * Math.PI), 0, (float) Math.cos(100 * Math.PI)));
        assertThat(rotation.getValue(), nearTo(a.get()));

        quat.setValue(Quaternion.quaternion(0, 0, (float) Math.sin(Math.PI / 2), (float) Math.cos(Math.PI / 2)));
        assertThat(rotation.getValue(), nearTo(a.get().inverse()));

        a.setValue(Y);
        assertThat(rotation.getValue(), nearTo(a.get().inverse()));
    }

    @Test
    public void testRotationInvert() {
        Vector3fProperty a = new Vector3fProperty(Vector3f.X);
        Quaternion4fProperty quat = new Quaternion4fProperty(
                Quaternion.quaternion(0, (float) Math.sin(Math.PI / 2), 0, (float) Math.cos(Math.PI / 2)));
        Vector3fBinding rotation = new Rotation(quat).apply(a);
        Vector3fBinding invert = new Rotation(quat).invert().apply(rotation);

        assertThat(invert, nearTo(a));
    }

    @Test
    public void testRotationMatrix() {
        Vector3fProperty a = new Vector3fProperty(Vector3f.X);
        Quaternion4fProperty quat = new Quaternion4fProperty(
                Quaternion.quaternion(0, (float) Math.sin(Math.PI / 2), 0, (float) Math.cos(Math.PI / 2)));
        Rotation r = new Rotation(quat);
        Matrix4fBinding m = r.asMatrix();

        assertThat(r.apply(a), nearTo(m.product(a)));

        a.setValue(Vector3f.XYZ);
        assertThat(r.apply(a), nearTo(m.product(a)));

        a.setValue(vect3f(3, 5, 7));
        assertThat(r.apply(a), nearTo(m.product(a)));

        quat.setValue(Quaternion.quaternion(3, 6, 1, 3));
        assertThat(r.apply(a), nearTo(m.product(a)));
    }

    @Test
    public void testInvertedMatrix() {
        Vector3fProperty a = new Vector3fProperty(Vector3f.X);
        Quaternion4fProperty quat = new Quaternion4fProperty(
                Quaternion.quaternion(0, (float) Math.sin(Math.PI / 2), 0, (float) Math.cos(Math.PI / 2)));
        Rotation r = new Rotation(quat);
        Matrix4fBinding m = r.asMatrix();
        Matrix4fBinding im = r.invert().asMatrix();

        assertThat(m.concat(im), nearTo(CONST_E));
    }

    @Test
    public void rotateAroundXByZero() {
        Vector3fConstant v = vect3fc(4, 4, 4);
        Rotation rotation = new Rotation(QuaternionBinding.axisRotation(Vector3fConstant.CONST_X, FloatConstant.valueOf(0)));
        assertThat(rotation.apply(v), nearTo(v));
    }

    @Test
    public void orientationTest() {
        ObservableQuaternion o = orientation(vect3fc(Y.multiply((float) (Math.PI / 2))));
        ObservableQuaternion r = axisRotation(CONST_Y, FloatConstant.valueOf((float) (Math.PI / 2)));
        assertThat(o, nearTo(r));
    }

}