package org.qw3rtrun.aub.engine.vectmath;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableValue;
import org.junit.Before;
import org.junit.Test;
import org.qw3rtrun.aub.engine.property.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.Vector4fProperty;

import static org.qw3rtrun.aub.engine.property.Bindings.*;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;
import static org.qw3rtrun.aub.engine.vectmath.Vector4fTest.assertNear;

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

        assertNear(X, m.getValue().multiply(X));
        assertNear(YZ, m.getValue().multiply(YZ));
        assertNear(YW, m.getValue().multiply(YW));
        assertNear(XZW, m.getValue().multiply(XZW));

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

        assertNear(vect(2, 1, 1, 1), m.getValue().multiply(XW));
        assertNear(vect(1, 2, 2, 1), m.getValue().multiply(YZW));
        assertNear(vect(1, 2, 1, 1), m.getValue().multiply(YW));
        assertNear(vect(2, 1, 2, 1), m.getValue().multiply(XZW));

        x.set(2);
        y.set(-1);
        z.set(0);
        assertNear(vect(4, -4, 0, 1), m.getValue().multiply(vect(2, -3, 0, 1)));

        ObservableValue<Vector4f> v = product(m, vect(1, 2, 3, 1));
        assertNear(vect(3, 1, 3, 1), v.getValue());

        z.setValue(-1);
        assertNear(vect(3, 1, 2, 1), v.getValue());

        x.setValue(100);
        assertNear(vect(101, 1, 2, 1), v.getValue());

        y.setValue(1);
        z.setValue(100);
        assertNear(vect(101, 3, 103, 1), v.getValue());
    }

    @Test
    public void testRotate() {
        Vector4fProperty q = new Vector4fProperty(X);
        Matrix4fBinding rotation = rotate(q);
        ObservableValue<Vector4f> rX = product(rotation, X.multiply(1000000));
        ObservableValue<Vector4f> rY = product(rotation, Y.multiply(1000000));
        ObservableValue<Vector4f> rZ = product(rotation, Z.multiply(1000000));

        System.out.println(rX.getValue());
        System.out.println(rY.getValue());
        System.out.println(rZ.getValue());
        System.out.println(Math.sin(2 * Math.PI));
        System.out.println(Math.cos(2 * Math.PI));
        System.out.println(Math.sin(Math.PI/2));
        System.out.println(Math.cos(Math.PI/2));

        q.setValue(X.w((float) (100*Math.PI)));

        System.out.println(rX.getValue());
        System.out.println(rY.getValue());
        System.out.println(rZ.getValue());
    }
}
