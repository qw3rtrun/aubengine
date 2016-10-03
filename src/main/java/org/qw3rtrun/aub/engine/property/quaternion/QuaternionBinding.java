package org.qw3rtrun.aub.engine.property.quaternion;

import com.sun.javafx.binding.FloatConstant;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.value.ObservableNumberValue;
import org.qw3rtrun.aub.engine.property.BaseBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;

import java.util.Arrays;
import java.util.function.Supplier;

import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.*;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.QXYZ0;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

public class QuaternionBinding extends BaseBinding<Quaternion> implements Binding<Quaternion>, QuaternionExpression {

    public QuaternionBinding(Supplier<Quaternion> func, Observable... dependencies) {
        super(func, dependencies);
    }

    public static QuaternionBinding normalize(ObservableQuaternion quat) {
        return new QuaternionBinding(() -> quat.get().normalize(), quat);
    }

    public static Vector4fBinding rotate(ObservableQuaternion rotation, ObservableVector vector) {
        return new Vector4fBinding(() -> {
            Quaternion normalized = rotation.get().normalize();
            return normalized.product(quaternion(vector.getValue())).product(normalized.conjugate()).asVector();
        }, rotation, vector);
    }

    public static QuaternionBinding axisRotation(ObservableVector axis, ObservableNumberValue radian) {
        return new QuaternionBinding(() -> {
            Vector4fBinding normalizedAxis = Vector4fBinding.normalize(axis);
            double a2 = radian.doubleValue() / 2;
            double sin = (float) Math.sin(a2);
            double cos = (float) Math.cos(a2);
            return quaternion(
                    (float) (normalizedAxis.getX() * sin),
                    (float) (normalizedAxis.getY() * sin),
                    (float) (normalizedAxis.getZ() * sin),
                    (float) cos);
        }, axis, radian);
    }

    public static QuaternionBinding orientation(ObservableVector rotation) {
        return concatRotations(
                axisRotation(CONST_X, FloatConstant.valueOf(rotation.getX())),
                axisRotation(CONST_Y, FloatConstant.valueOf(rotation.getY())),
                axisRotation(CONST_Z, FloatConstant.valueOf(rotation.getZ()))
        );
    }

    public static QuaternionBinding concatRotation(ObservableQuaternion quaternion1, ObservableQuaternion orientation2) {
        return new QuaternionBinding(() -> orientation2.get().product(quaternion1.get()).normalize());
    }

    public static QuaternionBinding concatRotations(ObservableQuaternion... quaternions) {
        switch (quaternions.length) {
            case 0:
                return new QuaternionBinding(() -> QXYZ0);
            case 1:
                return new QuaternionBinding(quaternions[0]::get, quaternions[0]);
            default:
                //noinspection OptionalGetWithoutIsPresent
                return (QuaternionBinding) Arrays.stream(quaternions).<QuaternionBinding>reduce(QuaternionBinding::concatRotation).get();
        }
    }

    public String toString() {
        return isValid() ? "Quaternion [" + getValue() + "]"
                : "Quaternion [invalid]";
    }
}
