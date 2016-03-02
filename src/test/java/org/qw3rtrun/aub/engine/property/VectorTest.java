package org.qw3rtrun.aub.engine.property;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.*;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fProperty.vectProp;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.XYZ;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

public class VectorTest {

    @Test
    public void test() {
        Vector4fBinding xyz = CONST_X.add(CONST_Y).add(0, 0, 1);
        assertEquals(XYZ, xyz.getValue());
        Vector4fProperty var = vectProp(0, 1, 1, 0);
        Vector4fBinding sum = CONST_XY.add(var).add(CONST_Z);
        assertEquals(vect(1, 2, 2), sum.getValue());
        var.setValue(9, 8, 7, 6);
        assertFalse(sum.isValid());
        assertEquals(vect(10, 9, 8, 6), sum.getValue());

    }
}
