package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Function;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

public class ScaleBinding extends Vector4fBinding {
    private final ObservableVector scaleVector;
    private final ObservableVector sourceVector;

    public ScaleBinding(ObservableVector sourceVector, ObservableVector scaleVector) {
        super(() -> vect(
                scaleVector.getX() * sourceVector.getX(),
                scaleVector.getY() * sourceVector.getY(),
                scaleVector.getZ() * sourceVector.getZ(),
                scaleVector.getW() * sourceVector.getW()
        ), sourceVector, scaleVector);
        this.scaleVector = scaleVector;
        this.sourceVector = sourceVector;
    }

    public static Function<ObservableVector, ScaleBinding> scaleBy(ObservableVector scale) {
        return (src) -> new ScaleBinding(src, scale);
    }

    public static Function<ObservableVector, ScaleBinding> scale(ObservableVector source) {
        return (scale) -> new ScaleBinding(source, scale);
    }

    public Vector4f getScaleVector() {
        return scaleVector.getValue();
    }

    public ObservableVector scaleVectorProperty() {
        return scaleVector;
    }

    public Vector4f getSourceVector() {
        return sourceVector.getValue();
    }

    public ObservableVector sourceVectorProperty() {
        return sourceVector;
    }
}
