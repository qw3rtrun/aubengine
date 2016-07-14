package org.qw3rtrun.aub.engine;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.qw3rtrun.aub.engine.vectmath.Near;

import static java.lang.Float.max;
import static java.lang.Math.abs;

public class Matchers {

    public static final float EPSILON = .00001f;

    @Factory
    public static <T extends Near<T>> Matcher<T> nearTo(T actual, double epsilon) {
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
        return nearTo(t, EPSILON * max(t.bound(), EPSILON));
    }

    public static Matcher<Number> nearTo(Number f, double epsilon) {
        return new BaseMatcher<Number>() {
            @Override
            public boolean matches(Object o) {
                return abs(f.doubleValue() - ((Number) o).doubleValue()) < abs(epsilon);
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(f);
            }
        };
    }

    public static Matcher<Number> nearTo(Number f) {
        return nearTo(f, EPSILON * max(f.floatValue(), EPSILON));
    }
}
