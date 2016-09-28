package org.qw3rtrun.aub.engine.property.transform;

import org.junit.Assert;
import org.junit.Test;
import org.qw3rtrun.aub.engine.Matchers;
import org.qw3rtrun.aub.engine.property.quaternion.Quaternion4fProperty;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

public class RotationTest {

    @Test
    public void testRotate() throws Exception {
        Vector4fProperty a = new Vector4fProperty(Vector4f.X);
        Quaternion4fProperty quat = new Quaternion4fProperty(
                Quaternion.quaternion(0, (float) Math.sin(Math.PI / 2), 0, (float) Math.cos(Math.PI / 2)));
        Vector4fBinding rotation = new Rotation(quat).apply(a);
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get().inverse()));

        quat.setValue(Quaternion.quaternion(0, (float) Math.sin(Math.PI), 0, (float) Math.cos(Math.PI)));
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get()));

        quat.setValue(Quaternion.quaternion(0, (float) Math.sin(100 * Math.PI), 0, (float) Math.cos(100 * Math.PI)));
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get()));

        quat.setValue(Quaternion.quaternion(0, 0, (float) Math.sin(Math.PI / 2), (float) Math.cos(Math.PI / 2)));
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get().inverse()));

        a.setValue(Vector4f.Y);
        Assert.assertThat(rotation.getValue(), Matchers.nearTo(a.get().inverse()));
    }

}