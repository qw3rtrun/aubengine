package org.qw3rtrun.aub.engine.vectmath;

public interface Near<T> {
    boolean isNearTo(T o, double epsilon);

    float bound();
}
