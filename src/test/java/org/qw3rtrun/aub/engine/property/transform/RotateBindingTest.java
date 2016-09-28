package org.qw3rtrun.aub.engine.property.transform;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.junit.Assert;
import org.junit.Test;
import org.qw3rtrun.aub.engine.Matchers;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public class RotateBindingTest {

    @Test
    public void testRotate() throws Exception {
        Vector4fProperty a = new Vector4fProperty(Vector4f.X);
        Vector4fProperty axis = new Vector4fProperty(Vector4f.Y);
        DoubleProperty rad = new SimpleDoubleProperty(Math.PI / -2);
        RotateBinding rotation = RotateBinding.rotateBy(rad, axis).apply(a);
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(Vector4f.Z));

        rad.set(Math.PI);
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get().inverse()));

        rad.set(Math.PI * 2);

        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get()));

        rad.set(Math.PI * 2000);
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get()));

        rad.setValue(Math.PI / 2);
        axis.setValue(Vector4f.YZ);
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(Vector4f.vect(0, 1, -1).normalize()));
    }

}