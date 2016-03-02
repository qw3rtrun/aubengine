package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import org.qw3rtrun.aub.engine.property.BaseBinding;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Supplier;

public class Vector4fBinding extends BaseBinding<Vector4f> implements Binding<Vector4f>, VectorExpression {

    public Vector4fBinding(Supplier<Vector4f> func, Observable... dependencies) {
        super(func, dependencies);
    }

    public String toString() {
        return isValid() ? "Vector [" + getValue() + "]"
                : "Vector [invalid]";
    }
}
