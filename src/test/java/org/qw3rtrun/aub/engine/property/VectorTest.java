package org.qw3rtrun.aub.engine.property;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.*;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fProperty.vectProp;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.XYZ;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect4f;

public class VectorTest {

    @Test
    public void test() {
        Vector4fBinding xyz = CONST_X.add(CONST_Y).add(0, 0, 1);
        assertEquals(XYZ, xyz.getValue());
        Vector4fProperty var = vectProp(0, 1, 1, 0);
        Vector4fBinding sum = CONST_XY.add(var).add(CONST_Z);
        assertEquals(Vector4f.vect4f(1, 2, 2), sum.getValue());
        var.setValue(9, 8, 7, 6);
        assertFalse(sum.isValid());
        assertEquals(vect4f(10, 9, 8, 6), sum.getValue());

    }

    @Test
    public void name() throws Exception {
        assertThat(Arrays.asList("A", "B", "C"), mapcher(l -> l.get(0), CoreMatchers.equalTo("A")));
        assertThat(Arrays.asList("A", "B", "C"), mapcher(List::size, CoreMatchers.equalTo(4)));
//        List<String> l = null;
//        assertThat(l, mapcher(List::size, CoreMatchers.equalTo(3)));

    }

    public static <T, R> Matcher<T> mapcher(Function<T, R> map, Matcher<R> matcher) {
        return new BaseMatcher<T>() {
            @Override
            public boolean matches(Object o) {
                return o == null ? false : matcher.matches(map.apply((T) o));
            }

            @Override
            public void describeTo(Description description) {
                matcher.describeTo(description);
            }
        };
    }

}
