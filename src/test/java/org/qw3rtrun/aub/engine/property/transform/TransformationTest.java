package org.qw3rtrun.aub.engine.property.transform;

import com.sun.javafx.binding.FloatConstant;
import org.junit.Test;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fConstant;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.transform.Transformation.start;
import static org.qw3rtrun.aub.engine.property.vector.Vector3fConstant.vect3fc;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.X;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.ZERO;

public class TransformationTest {

    private Transformation t = start();

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
        Transformation t = start();

        assertThat(
                t.translate(vect3fc(1, 2, 4))
                        .scale(vect3fc(4, 2, 1))
                        .rotate(QuaternionBinding.axisRotation(Vector3fConstant.CONST_X, FloatConstant.valueOf((float) Math.PI)))
                        .apply(vect3fc()
                        ),
                nearTo(vect3fc(4, -4, -4)));
    }

    @Test
    public void testMatrixTransformation() {
        Transformation tx = t
                .translate(vect3fc(1, 2, 4))
                .scale(vect3fc(4, 2, 1))
                .rotate(QuaternionBinding.axisRotation(vect3fc(X), FloatConstant.valueOf((float) Math.PI / 2)));
        assertThat(tx.apply(vect3fc()), nearTo(tx.asMatrix().product(ZERO)));
        assertThat(tx.invert().apply(vect3fc()), nearTo(tx.invert().asMatrix().product(ZERO)));
    }
}