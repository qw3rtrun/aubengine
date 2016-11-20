package org.qw3rtrun.aub.engine.property.transform;

import com.sun.javafx.binding.FloatConstant;
import org.junit.Test;
import org.qw3rtrun.aub.engine.Matchers;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionConstant;
import org.qw3rtrun.aub.engine.property.vector.Vector3fConstant;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.property.vector.Vector4fConstant;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.transform.Transformation.start;
import static org.qw3rtrun.aub.engine.property.vector.Vector3fConstant.vect3fc;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.Q0;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.QX0;

public class TransformationTest {

    Transformation t = start();

    @Test
    public void identityTest() {
        Vector3fProperty a = new Vector3fProperty(Vector3f.XYZ);
        assertThat(t.apply(a), nearTo(a));
        assertThat(t.asMatrix().get(), nearTo(Matrix4f.E));
        assertEquals(t, t.invert());
    }

    @Test
    public void simpleTest() throws Exception {
        assertEquals(t.translate(Vector3fConstant.CONST_XY), new Translation(Vector3fConstant.CONST_XY));
    }

    @Test
    public void testAll() {
        Transformation t = start()
                .translate(vect3fc(1, 2, 4))
                .scale(vect3fc(4, 2, 1))
                .rotate(QuaternionBinding.axisRotation(Vector3fConstant.CONST_X, FloatConstant.valueOf(0)));

        assertThat(t.apply(vect3fc()), nearTo(vect3fc(4, 4, 4)));
    }
}