package org.qw3rtrun.aub.engine.scene;

import org.junit.Test;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

/**
 * Created by strunov on 10/26/2015.
 */
public class ObjectTest {

    @Test
    public void test() {
        SceneObject obj = new SceneObject();

        System.out.println(obj.toAbsolute(Vector4f.XYZW));

        obj.orientation().setValue(0, 1, 0);

        System.out.println(obj.toAbsolute(Vector4f.XYZW));

        obj.scale().setX(2);
        obj.scale().setZ(-1);

        System.out.println(obj.toAbsolute(Vector4f.XYZW));

        obj.translation().setValue(10, 10, 10);

        System.out.println(obj.toAbsolute(Vector4f.XYZW));

        System.out.println(obj.toAbsolute(Vector4f.XYZW));

    }
}
