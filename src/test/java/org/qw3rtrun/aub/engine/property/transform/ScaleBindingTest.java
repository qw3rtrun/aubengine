package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.vectConst;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class ScaleBindingTest {

    @Test
    public void testScale() throws Exception {
        Vector4fProperty a = new Vector4fProperty(vect(1, 2, 3, -1));
        Vector4fProperty b = new Vector4fProperty(Vector4f.XYZW);

        Vector4fBinding scaling = new Scale(a).apply(b);

        assertThat(scaling, nearTo(vectConst(1, 2, 3, -1)));

        a.setValue(-1, 0, 3, 2);
        assertThat(scaling, nearTo(vectConst(-1, 0, 3, 2)));

        b.setValue(0, 1.5f, -1, -2);
        assertThat(scaling, nearTo(vectConst(0, 0, -3, -4)));
    }

    @Test
    public void testInvertScale() throws Exception {
        Vector4fProperty a = new Vector4fProperty(vect(1, 1f / 2, 1f / 3, -1));
        Vector4fProperty b = new Vector4fProperty(Vector4f.XYZW);
        Vector4fBinding scaling = new Scale(a).invert().apply(b);

        assertThat(scaling, nearTo(vectConst(1, 2, 3, -1)));

        a.setValue(-1, 1, 1f / 3, 1f / 2);
        assertThat(scaling, nearTo(vectConst(-1, 1, 3, 2)));

        b.setValue(1, 1.5f, -1, -2);
        assertThat(scaling, nearTo(vectConst(-1, 1.5f, -3, -4)));
    }

    @Test
    public void testMatrix() {
        Matrix4fBinding matrix = new Scale(vectConst(1, 2, 3, -1)).asMatrix();
        assertThat(matrix.get(), nearTo(Matrix4f.cols(X.multiply(1), Y.multiply(2), Z.multiply(3), W.multiply(-1))));
    }

}