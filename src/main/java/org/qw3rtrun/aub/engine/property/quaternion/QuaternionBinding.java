package org.qw3rtrun.aub.engine.property.quaternion;

import com.sun.javafx.binding.FloatConstant;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.value.ObservableNumberValue;
import org.qw3rtrun.aub.engine.property.BaseBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fConstant;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;

import java.util.Arrays;
import java.util.function.Supplier;

import static org.qw3rtrun.aub.engine.vectmath.Quaternion.QXYZ0;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

public class QuaternionBinding extends BaseBinding<Quaternion> implements Binding<Quaternion>, QuaternionExpression {

    public QuaternionBinding(Supplier<Quaternion> func, Observable... dependencies) {
        super(func, dependencies);
    }

    public static QuaternionBinding normalize(ObservableQuaternion quat) {
        return new QuaternionBinding(() -> quat.get().normalize(), quat);
    }

    public static Vector3fBinding rotate(ObservableQuaternion rotation, ObservableVector3f vector) {
        return new Vector3fBinding(() -> {
            Quaternion normalized = rotation.get().normalize();
            return normalized.product(quaternion(vector.getValue())).product(normalized.conjugate()).getVectorPart();
        }, rotation, vector);
    }

    public static QuaternionBinding axisRotation(ObservableVector3f axis, ObservableNumberValue radian) {
        return new QuaternionBinding(() -> {
            Vector3fBinding normalizedAxis = Vector3fBinding.normalize(axis);
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

    public static QuaternionBinding orientation(ObservableVector3f rotation) {
        return concatRotations(
                axisRotation(Vector3fConstant.CONST_X, FloatConstant.valueOf(rotation.getX())),
                axisRotation(Vector3fConstant.CONST_Y, FloatConstant.valueOf(rotation.getY())),
                axisRotation(Vector3fConstant.CONST_Z, FloatConstant.valueOf(rotation.getZ()))
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
