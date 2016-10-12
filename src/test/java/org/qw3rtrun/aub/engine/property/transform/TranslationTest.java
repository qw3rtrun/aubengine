package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fConstant;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.*;


public class TranslationTest {

    @Test
    public void testTranslate() throws Exception {
        Vector3fProperty a = new Vector3fProperty(Vector3f.ZERO);
        Vector3fProperty b = new Vector3fProperty(vect3f(2, -3, 0));
        Vector3fBinding byConst = new Translation(Vector3fConstant.vect3fc(2, -3, 0)).apply(a);
        Vector3fBinding byProp = new Translation(b).apply(a);

        assertThat(byConst.getValue(), nearTo(vect3f(2, -3, 0)));
        assertThat(byProp.getValue(), nearTo(vect3f(2, -3, 0)));

        a.setValue(XYZ);
        assertThat(byConst.getValue(), nearTo(vect3f(3, -2, 1)));
        assertThat(byProp.getValue(), nearTo(vect3f(3, -2, 1)));

        b.setValue(ZERO);
        assertThat(byProp.getValue(), nearTo(XYZ));
    }

    @Test
    public void testInverse() throws Exception {
        Vector3fProperty a = new Vector3fProperty(Vector3f.ZERO);
        Vector3fProperty b = new Vector3fProperty(vect3f(2, -3, 0));
        Vector3fBinding byConst = new Translation(Vector3fConstant.vect3fc(2, -3, 0)).invert().apply(a);
        Vector3fBinding byProp = new Translation(b).invert().apply(a);

        assertThat(byConst.getValue(), nearTo(vect3f(-2, 3, 0)));
        assertThat(byProp.getValue(), nearTo(vect3f(-2, 3, 0)));

        a.setValue(XYZ);
        assertThat(byConst.getValue(), nearTo(vect3f(-1, 4, 1)));
        assertThat(byProp.getValue(), nearTo(vect3f(-1, 4, 1)));

        b.setValue(ZERO);
        assertThat(byProp.getValue(), nearTo(XYZ));
    }

    @Test
    public void testMatrix() {
        Vector3fConstant v = Vector3fConstant.vect3fc(2, -3, 0);
        Translation t = new Translation(v);
        Matrix4fBinding m = t.asMatrix();
        assertThat(m.get(), nearTo(Matrix4f.cols(Vector4f.X, Vector4f.Y, Vector4f.Z, v.get().w(1))));

        //assertThat(m.product(X), nearTo(t.apply(CONST_X)));
    }
}