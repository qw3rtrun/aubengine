package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableNumberValue;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

public interface VectorExpression extends ObservableVector {

    default Vector4fBinding multiply(float k) {
        return Vector4fBinding.multiply(k, this);
    }

    default Vector4fBinding add(ObservableVector other) {
        return Vector4fBinding.add(this, other);
    }

    default Vector4fBinding add(float x, float y, float z) {
        return Vector4fBinding.add(this, vect(x, y, z));
    }

    default FloatBinding length() {
        return new FloatBinding() {
            {
                bind(this);
            }

            @Override
            protected float computeValue() {
                return getLength();
            }
        };
    }

    default Vector4fBinding inverse() {
        return Vector4fBinding.inverse(this);
    }

    default Vector4fBinding multiply(ObservableNumberValue k) {
        return Vector4fBinding.multiply(k, this);
    }
}
