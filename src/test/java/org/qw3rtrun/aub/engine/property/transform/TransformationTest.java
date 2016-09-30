package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.Matchers;
import org.qw3rtrun.aub.engine.property.vector.Vector4fConstant;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TransformationTest {

    Transformation t = Translation.start();

    @Test
    public void identityTest() {
        Vector4fProperty a = new Vector4fProperty(Vector4f.XYZW);
        assertThat(t.apply(a), Matchers.nearTo(a));
        assertThat(t.asMatrix().get(), Matchers.nearTo(Matrix4f.E));
        assertEquals(t, t.invert());
    }

    @Test
    public void simpleTest() throws Exception {
        assertEquals(t.translate(Vector4fConstant.CONST_XY), new Translation(Vector4fConstant.CONST_XY));
    }
}