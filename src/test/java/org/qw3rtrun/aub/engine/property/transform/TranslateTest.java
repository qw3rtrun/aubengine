package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fConstant;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.vectConst;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;


public class TranslateTest {

    @Test
    public void testTranslate() throws Exception {
        Vector4fProperty a = new Vector4fProperty(Vector4f.ZERO);
        Vector4fProperty b = new Vector4fProperty(vect(2, -3, 0, 1.5f));
        Vector4fBinding byConst = new Translate(vectConst(2, -3, 0, 1.5f)).apply(a);
        Vector4fBinding byProp = new Translate(b).apply(a);

        assertThat(byConst.getValue(), nearTo(vect(2, -3, 0, 1.5f)));
        assertThat(byProp.getValue(), nearTo(vect(2, -3, 0, 1.5f)));

        a.setValue(XYZW);
        assertThat(byConst.getValue(), nearTo(vect(3, -2, 1, 2.5f)));
        assertThat(byProp.getValue(), nearTo(vect(3, -2, 1, 2.5f)));

        b.setValue(ZERO);
        assertThat(byProp.getValue(), nearTo(XYZW));
    }

    @Test
    public void testInverse() throws Exception {
        Vector4fProperty a = new Vector4fProperty(Vector4f.ZERO);
        Vector4fProperty b = new Vector4fProperty(vect(2, -3, 0, 1.5f));
        Vector4fBinding byConst = new Translate(vectConst(2, -3, 0, 1.5f)).invert().apply(a);
        Vector4fBinding byProp = new Translate(b).invert().apply(a);

        assertThat(byConst.getValue(), nearTo(vect(-2, 3, 0, -1.5f)));
        assertThat(byProp.getValue(), nearTo(vect(-2, 3, 0, -1.5f)));

        a.setValue(XYZW);
        assertThat(byConst.getValue(), nearTo(vect(-1, 4, 1, -0.5f)));
        assertThat(byProp.getValue(), nearTo(vect(-1, 4, 1, -0.5f)));

        b.setValue(ZERO);
        assertThat(byProp.getValue(), nearTo(XYZW));
    }

    @Test
    public void testMatrix() {
        Vector4fConstant translation = vectConst(2, -3, 0, 1.5f);
        Matrix4fBinding matrix = new Translate(translation).asMatrix();
        assertThat(matrix.get(), nearTo(Matrix4f.cols(X, Y, Z, translation.get())));
    }
}