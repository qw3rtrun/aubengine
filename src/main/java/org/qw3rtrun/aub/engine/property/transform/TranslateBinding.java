package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Function;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

public class TranslateBinding extends Vector4fBinding {
    private final ObservableVector translate;
    private final ObservableVector source;

    public TranslateBinding(ObservableVector sourceVector, ObservableVector scaleVector) {
        super(() -> vect(
                scaleVector.getX() + sourceVector.getX(),
                scaleVector.getY() + sourceVector.getY(),
                scaleVector.getZ() + sourceVector.getZ(),
                scaleVector.getW() + sourceVector.getW()
        ), sourceVector, scaleVector);
        this.translate = scaleVector;
        this.source = sourceVector;
    }

    public static Function<ObservableVector, TranslateBinding> translateBy(ObservableVector scale) {
        return (src) -> new TranslateBinding(src, scale);
    }

    public static Function<ObservableVector, TranslateBinding> translate(ObservableVector source) {
        return (translate) -> new TranslateBinding(source, translate);
    }

    public Vector4f getTranslate() {
        return translate.getValue();
    }

    public ObservableVector translateProperty() {
        return translate;
    }

    public Vector4f getSource() {
        return source.getValue();
    }

    public ObservableVector sourceProperty() {
        return source;
    }

    public TranslateBinding inverse() {
        return new TranslateBinding(source, Vector4fBinding.inverse(translate));
    }
}
