package org.qw3rtrun.aub.engine;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.qw3rtrun.aub.engine.vectmath.Near;

public class Matchers {

    public static final float EPSILON = .000001f;

    @Factory
    public static <T extends Near<T>> Matcher<T> nearTo(T actual, float epsilon) {
        return new BaseMatcher<T>() {
            @Override
            public boolean matches(Object o) {
                return actual.isNearTo((T) o, epsilon);
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(actual);
            }
        };
    }

    public static <T extends Near<T>> Matcher<T> nearTo(T t) {
        return nearTo(t, EPSILON);
    }


}
