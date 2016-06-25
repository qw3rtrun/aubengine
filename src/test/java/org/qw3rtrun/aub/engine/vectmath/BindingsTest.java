package org.qw3rtrun.aub.engine.vectmath;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.property.Bindings.*;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

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
        ObservableValue<Matrix4f> m = scaleMatrix(vector(x, y, z));

        assertThat(m.getValue().multiply(X), nearTo(X));
        assertThat(m.getValue().multiply(YZ), nearTo(YZ));
        assertThat(m.getValue().multiply(YW), nearTo(YW));
        assertThat(m.getValue().multiply(XZW), nearTo(XZW));

        x.set(2);
        y.set(-1);
        z.set(0);
        assertThat(m.getValue().multiply(vect(2, 4, 100)), nearTo(vect(4, -4, 0)));

        ObservableValue<Vector4f> v = product(m, vect(1, 2, 3));
        assertThat(v.getValue(), nearTo(vect(2, -2, 0)));

        z.setValue(-1);
        assertThat(v.getValue(), nearTo(vect(2, -2, -3)));

        x.setValue(100);
        assertThat(v.getValue(), nearTo(vect(100, -2, -3)));

        y.setValue(1);
        z.setValue(100);
        assertThat(v.getValue(), nearTo(vect(100, 2, 300)));
    }

    @Test
    public void testTranslate() {
        ObservableValue<Matrix4f> m = translationMatrix(vector(x, y, z));

        assertThat(m.getValue().multiply(XW), nearTo(vect(2, 1, 1, 1)));
        assertThat(m.getValue().multiply(YZW), nearTo(vect(1, 2, 2, 1)));
        assertThat(m.getValue().multiply(YW), nearTo(vect(1, 2, 1, 1)));
        assertThat(m.getValue().multiply(XZW), nearTo(vect(2, 1, 2, 1)));

        x.set(2);
        y.set(-1);
        z.set(0);
        assertThat(
                m.getValue().multiply(vect(2, -3, 0, 1)),
                nearTo(vect(4, -4, 0, 1)));

        ObservableValue<Vector4f> v = product(m, vect(1, 2, 3, 1));
        assertThat(v.getValue(), nearTo(vect(3, 1, 3, 1)));

        z.setValue(-1);
        assertThat(v.getValue(), nearTo(vect(3, 1, 2, 1)));

        x.setValue(100);
        assertThat(v.getValue(), nearTo(vect(101, 1, 2, 1)));

        y.setValue(1);
        z.setValue(100);
        assertThat(v.getValue(), nearTo(vect(101, 3, 103, 1)));
    }
}
