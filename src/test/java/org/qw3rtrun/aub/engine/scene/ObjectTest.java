package org.qw3rtrun.aub.engine.scene;

import org.junit.Test;
import org.qw3rtrun.aub.engine.property.vector.Vector3fConstant;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

/**
 * Created by strunov on 10/26/2015.
 */
public class ObjectTest {

    @Test
    public void test() {
        SceneObject obj = new SceneObject();

        Vector3fConstant xyzw = Vector3fConstant.vect3fc(Vector3f.XYZ);
        System.out.println(obj.toAbsolute(xyzw).get());

        obj.orientation().setValue(0, (float) Math.PI, 0);

        System.out.println(obj.toAbsolute(xyzw).get());

        obj.scale().setX(2);
        obj.scale().setZ(-1);

        System.out.println(obj.toAbsolute(xyzw).get());

        obj.translation().setValue(10, 10, 10);

        System.out.println(obj.toAbsolute(xyzw).get());


    }
}
