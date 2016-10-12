package org.qw3rtrun.aub.engine.property;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.*;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fProperty.vectProp;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.XYZ;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect4f;

public class VectorTest {

    @Test
    public void test() {
        Vector4fBinding xyz = CONST_X.add(CONST_Y).add(0, 0, 1);
        assertEquals(XYZ, xyz.getValue());
        Vector4fProperty var = vectProp(0, 1, 1, 0);
        Vector4fBinding sum = CONST_XY.add(var).add(CONST_Z);
        assertEquals(Vector4f.vect4f(1, 2, 2), sum.getValue());
        var.setValue(9, 8, 7, 6);
        assertFalse(sum.isValid());
        assertEquals(vect4f(10, 9, 8, 6), sum.getValue());

    }
}
