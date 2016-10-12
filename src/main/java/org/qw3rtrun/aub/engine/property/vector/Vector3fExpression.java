package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableNumberValue;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

public interface Vector3fExpression extends ObservableVector3f {

    default Vector3fBinding multiply(float k) {
        return Vector3fBinding.multiply(k, this);
    }

    default Vector3fBinding add(ObservableVector3f other) {
        return Vector3fBinding.add(this, other);
    }

    default Vector3fBinding add(float x, float y, float z) {
        return Vector3fBinding.add(this, Vector3f.vect3f(x, y, z));
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

    default Vector3fBinding inverse() {
        return Vector3fBinding.inverse(this);
    }

    default Vector3fBinding multiply(ObservableNumberValue k) {
        return Vector3fBinding.multiply(k, this);
    }
}
