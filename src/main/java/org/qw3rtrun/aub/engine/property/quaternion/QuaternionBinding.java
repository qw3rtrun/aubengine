package org.qw3rtrun.aub.engine.property.quaternion;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import org.qw3rtrun.aub.engine.property.BaseBinding;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;

import java.util.function.Supplier;

public class QuaternionBinding extends BaseBinding<Quaternion> implements Binding<Quaternion>, QuaternionExpression {

    public QuaternionBinding(Supplier<Quaternion> func, Observable... dependencies) {
        super(func, dependencies);
    }

    public String toString() {
        return isValid() ? "Quaternion [" + getValue() + "]"
                : "Quaternion [invalid]";
    }
}
