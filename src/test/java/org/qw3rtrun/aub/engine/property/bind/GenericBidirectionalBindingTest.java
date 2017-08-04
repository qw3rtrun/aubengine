package org.qw3rtrun.aub.engine.property.bind;

import javafx.beans.property.Property;
import org.junit.Test;
import org.qw3rtrun.aub.engine.property.quaternion.Quaternion4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import java.util.function.Function;

import static org.qw3rtrun.aub.engine.property.vector.Vector3fProperty.vectProp;

public class GenericBidirectionalBindingTest {

    @Test
    public void simpleTest() throws Exception {
        Property<Vector3f> v1 = vectProp(1, 2, 3);
        Property<Vector3f> v2 = vectProp();

        Function<Vector3f, Vector3f> inv = Vector3f::inverse;

        new GenericBidirectionalBinding<>(v1, v2, inv, inv);

        System.out.println(v1);
        System.out.println(v2);

        v1.setValue(Vector3f.vect3f(3, 2, 1));

        System.out.println(v1);
        System.out.println(v2);

        v2.setValue(Vector3f.vect3f(6, 6, 6));

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test
    public void simple2Test() throws Exception {
        Property<Vector3f> v1 = vectProp(1, 2, 3);
        Property<Vector3f> v2 = vectProp();
        Property<Vector3f> v3 = vectProp();

        Function<Vector3f, Vector3f> inv = Vector3f::inverse;

        new GenericBidirectionalBinding<>(v1, v2, inv, inv);
        new GenericBidirectionalBinding<>(v2, v3, inv, inv);

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);

        v1.setValue(Vector3f.vect3f(3, 2, 1));

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);

        v2.setValue(Vector3f.vect3f(6, 6, 6));

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);
    }

    @Test
    public void rotationTest() throws Exception {
        Property<Vector3f> euler = vectProp(0, 0, 0);
        Property<Vector3f> euler1 = vectProp(0, 0, 0);
        Property<Quaternion> orient = new Quaternion4fProperty();

        Function<Vector3f, Quaternion> o = Vector3f::toOrientation;
        Function<Quaternion, Vector3f> e = Quaternion::toEuler;

        new GenericBidirectionalBinding<>(euler, orient, o, e);
        new GenericBidirectionalBinding<>(orient, euler1, e, o);

        System.out.println(euler);
        System.out.println(orient);
        System.out.println(euler);

        euler.setValue(Vector3f.vect3f(3, 2, 1));
        System.out.println(euler);
        System.out.println(orient);
        System.out.println(euler);

        euler.setValue(Vector3f.vect3f(100, 100, 100));

        System.out.println(euler);
        System.out.println(orient);
        System.out.println(euler);
    }
}