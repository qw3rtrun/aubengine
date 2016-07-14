package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.quaternion.Quaternion4fProperty;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import static org.qw3rtrun.aub.engine.vectmath.MathUtils.cos;
import static org.qw3rtrun.aub.engine.vectmath.MathUtils.sin;

public class RotateBindingTest {

    @Test
    public void testRotate() throws Exception {
        Vector4fProperty a = new Vector4fProperty(Vector4f.XYZW);

        double rad = Math.PI / 2;
        Quaternion4fProperty b = new Quaternion4fProperty(Quaternion.quaternion(sin(rad / 2), 0, 0, cos(rad / 2)));

        RotateBinding.rotate(a).apply(b);
    }

}