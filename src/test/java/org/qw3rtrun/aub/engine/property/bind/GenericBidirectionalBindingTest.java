package org.qw3rtrun.aub.engine.property.bind;

import javafx.beans.property.Property;
import org.junit.Test;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import java.util.function.Function;

import static org.junit.Assert.*;
import static org.qw3rtrun.aub.engine.property.vector.Vector3fProperty.vectProp;

public class GenericBidirectionalBindingTest {

    @Test
    public void simpleTest() throws Exception {
        Property<Vector3f> v1 = vectProp(1, 2, 3);
        Property<Vector3f> v2 = vectProp();

        Function<Vector3f, Vector3f> inv = Vector3f::inverse;

        new GenericBidirectionalBinding<Vector3f, Vector3f>(v1, v2, inv, inv);

        System.out.println(v1);
        System.out.println(v2);

        v1.setValue(Vector3f.vect3f(3, 2, 1));

        System.out.println(v1);
        System.out.println(v2);

        v2.setValue(Vector3f.vect3f(6, 6, 6));

        System.out.println(v1);
        System.out.println(v2);
    }
}