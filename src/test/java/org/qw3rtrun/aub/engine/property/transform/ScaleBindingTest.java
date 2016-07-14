package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.transform.ScaleBinding.scale;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.vectConst;

public class ScaleBindingTest {

    @Test
    public void testScaleBy() throws Exception {
        Vector4fProperty a = new Vector4fProperty(Vector4f.XYZW);
        Vector4fProperty b = new Vector4fProperty(Vector4f.XYZW);
        ScaleBinding byConst = scale(a).apply(vectConst(1, 2, 3, -1));
        ScaleBinding byProperty = scale(a).apply(b);

        assertThat(byConst, nearTo(vectConst(1, 2, 3, -1)));
        assertThat(byProperty, nearTo(vectConst(1, 1, 1, 1)));

        a.setValue(-1, 0, 3, 2);
        assertThat(byConst, nearTo(vectConst(-1, 0, 9, -2)));
        assertThat(byProperty, nearTo(vectConst(-1, 0, 3, 2)));

        b.setValue(0, 1.5f, -1, -2);
        assertThat(byProperty, nearTo(vectConst(0, 0, -3, -4)));
    }

}