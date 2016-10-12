package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.vector.Vector3fConstant.CONST_X;
import static org.qw3rtrun.aub.engine.property.vector.Vector3fConstant.vect3fc;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.X;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.vect3f;

public class ScalingTest {

    @Test
    public void testScale() throws Exception {
        Vector3fProperty a = new Vector3fProperty(vect3f(1, 2, 3));
        Vector3fProperty b = new Vector3fProperty(Vector3f.XYZ);

        Vector3fBinding scaling = new Scaling(a).apply(b);

        assertThat(scaling, nearTo(vect3fc(1, 2, 3)));

        a.setValue(-1, 0, 3);
        assertThat(scaling, nearTo(vect3fc(-1, 0, 3)));

        b.setValue(0, 1.5f, -1);
        assertThat(scaling, nearTo(vect3fc(0, 0, -3)));
    }

    @Test
    public void testInvertScale() throws Exception {
        Vector3fProperty a = new Vector3fProperty(vect3f(1, 1f / 2, 1f / 3));
        Vector3fProperty b = new Vector3fProperty(Vector3f.XYZ);
        Vector3fBinding scaling = new Scaling(a).invert().apply(b);

        assertThat(scaling, nearTo(vect3fc(1, 2, 3)));

        a.setValue(-1, 1, 1f / 3);
        assertThat(scaling, nearTo(vect3fc(-1, 1, 3)));

        b.setValue(1, 1.5f, -1);
        assertThat(scaling, nearTo(vect3fc(-1, 1.5f, -3)));
    }

    @Test
    public void testMatrix() {
        Scaling scaling = new Scaling(vect3fc(1, 2, 3));
        Matrix4fBinding matrix = scaling.asMatrix();
        assertThat(matrix.get(), nearTo(Matrix4f.cols(Vector4f.X.multiply(1), Vector4f.Y.multiply(2), Vector4f.Z.multiply(3), Vector4f.W.multiply(-1))));

        assertThat(matrix.product(X), nearTo(scaling.apply(CONST_X)));
    }

}