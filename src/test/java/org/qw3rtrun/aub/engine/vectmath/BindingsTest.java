package org.qw3rtrun.aub.engine.vectmath;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableValue;
import org.junit.Before;
import org.junit.Test;

import static org.qw3rtrun.aub.engine.property.Bindings.*;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;
import static org.qw3rtrun.aub.engine.vectmath.Vector4fTest.assertNear;

/**
 * Created by strunov on 9/23/2015.
 */
public class BindingsTest {

    FloatProperty x = new SimpleFloatProperty();
    FloatProperty y = new SimpleFloatProperty();
    FloatProperty z = new SimpleFloatProperty();

    @Before
    public void setUp() {
        x.set(1);
        y.set(1);
        z.set(1);
    }

    @Test
    public void testScale() {
        ObservableValue<Matrix4f> m = scale(vector(x, y, z));

        assertNear(vX, m.getValue().multiply(vX));
        assertNear(vYZ, m.getValue().multiply(vYZ));
        assertNear(pY, m.getValue().multiply(pY));
        assertNear(pXZ, m.getValue().multiply(pXZ));

        x.set(2);
        y.set(-1);
        z.set(0);
        assertNear(vect(4, -4, 0), m.getValue().multiply(vect(2, 4, 100)));

        ObservableValue<Vector4f> v = product(m, vect(1, 2, 3));
        assertNear(vect(2, -2, 0), v.getValue());

        z.setValue(-1);
        assertNear(vect(2, -2, -3), v.getValue());

        x.setValue(100);
        assertNear(vect(100, -2, -3), v.getValue());

        y.setValue(1);
        z.setValue(100);
        assertNear(vect(100, 2, 300), v.getValue());
    }

    @Test
    public void testTranslate() {
        ObservableValue<Matrix4f> m = translate(vector(x, y, z));

        assertNear(vect(2, 1, 1), m.getValue().multiply(vX));
        assertNear(vect(1, 2, 2), m.getValue().multiply(vYZ));
        assertNear(vect(1, 2, 1), m.getValue().multiply(pY));
        assertNear(vect(2, 1, 2), m.getValue().multiply(pXZ));

        x.set(2);
        y.set(-1);
        z.set(0);
        assertNear(vect(4, -4, 0), m.getValue().multiply(vect(2, -3, 0)));

        ObservableValue<Vector4f> v = product(m, vect(1, 2, 3));
        assertNear(vect(3, 1, 3), v.getValue());

        z.setValue(-1);
        assertNear(vect(3, 1, 2), v.getValue());

        x.setValue(100);
        assertNear(vect(101, 1, 2), v.getValue());

        y.setValue(1);
        z.setValue(100);
        assertNear(vect(101, 3, 103), v.getValue());
    }
}
