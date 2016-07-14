package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.transform.TranslateBinding.translateBy;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.vectConst;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;


public class TranslateBindingTest {

    @Test
    public void testTranslateBy() throws Exception {
        Vector4fProperty a = new Vector4fProperty(Vector4f.ZERO);
        Vector4fProperty b = new Vector4fProperty(vect(2, -3, 0, 1.5f));
        TranslateBinding byConst = translateBy(vectConst(2, -3, 0, 1.5f)).apply(a);
        TranslateBinding byProp = translateBy(b).apply(a);

        assertThat(byConst.getValue(), nearTo(vect(2, -3, 0, 1.5f)));
        assertThat(byProp.getValue(), nearTo(vect(2, -3, 0, 1.5f)));

        a.setValue(XYZW);
        assertThat(byConst.getValue(), nearTo(vect(3, -2, 1, 2.5f)));
        assertThat(byProp.getValue(), nearTo(vect(3, -2, 1, 2.5f)));

        b.setValue(ZERO);
        assertThat(byProp.getValue(), nearTo(XYZW));
    }

}