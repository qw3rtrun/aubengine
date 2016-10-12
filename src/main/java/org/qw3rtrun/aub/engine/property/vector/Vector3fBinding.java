package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.property.BaseBinding;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.sun.javafx.binding.FloatConstant.valueOf;
import static java.util.Arrays.stream;
import static org.qw3rtrun.aub.engine.vectmath.Vector3f.ZERO;

public class Vector3fBinding extends BaseBinding<Vector3f> implements Binding<Vector3f>, Vector3fExpression {

    public Vector3fBinding(Supplier<Vector3f> func, Observable... dependencies) {
        super(func, dependencies);
    }

    private static Vector3fBinding multiply0(ObservableNumberValue k, ObservableValue<Vector3f> v, Observable... dependencies) {
        return new Vector3fBinding(() -> v.getValue().multiply(k.floatValue()), dependencies);
    }

    private static Vector3fBinding add0(ObservableValue<Vector3f> v1, ObservableValue<Vector3f> v2, Observable... dependencies) {
        return new Vector3fBinding(() -> v1.getValue().add(v2.getValue()), dependencies);
    }

    public static Vector3fBinding identity(ObservableVector3f vector) {
        return new Vector3fBinding(vector::get, vector);
    }

    public static <P, T extends Number> FloatBinding func(Function<P, T> f, ObservableValue<P> p) {
        return new FloatBinding() {
            {
                bind(p);
            }

            @Override
            protected float computeValue() {
                return f.apply(p.getValue()).floatValue();
            }
        };
    }

    public static Vector3fBinding inverse(ObservableVector3f vector) {
        return Vector3fBinding.multiply(-1, vector);
    }

    public static Vector3fBinding multiply(ObservableNumberValue k, ObservableValue<Vector3f> v) {
        return multiply0(k, v, k, v);
    }

    public static Vector3fBinding multiply(float k, ObservableValue<Vector3f> v) {
        return multiply0(valueOf(k), v, v);
    }

    public static Vector3fBinding multiply(ObservableFloatValue k, Vector3f v) {
        return multiply0(k, new Vector3fConstant(v), k);
    }

    public static Vector3fBinding add(ObservableValue<Vector3f> v1, ObservableValue<Vector3f> v2) {
        return add0(v1, v2, v1, v2);
    }

    public static Vector3fBinding add(ObservableValue<Vector3f> v1, Vector3f v2) {
        return add0(v1, new Vector3fConstant(v2), v1);
    }

    public static Vector3fBinding add(ObservableVector3f... vectors) {
        switch (vectors.length) {
            case 0:
                return new Vector3fBinding(() -> ZERO);
            case 1:
                return new Vector3fBinding(vectors[0]::getValue, vectors[0]);
            case 2:
                return add0(vectors[0], vectors[1], vectors[0], vectors[1]);
            default:
                return new Vector3fBinding(() -> Vector3f.vect3f().addAll(stream(vectors)
                        .map(ObservableValue::getValue).toArray(Vector3f[]::new)), vectors);
        }
    }

    public static Vector3fBinding vector(ObservableFloatValue x, ObservableFloatValue y, ObservableFloatValue z) {
        return new Vector3fBinding(() -> Vector3f.vect3f(x.get(), y.get(), z.get()), x, y, z);
    }

    public static ObservableFloatValue x(ObservableValue<Vector3f> vector) {
        return func(Vector3f::getX, vector);
    }

    public static ObservableVector3f withX(ObservableFloatValue x) {
        return null;//        new Vector3fBinding()
    }

    public static ObservableFloatValue y(ObservableValue<Vector3f> vector) {
        return func(Vector3f::getY, vector);
    }

    public static ObservableFloatValue z(ObservableValue<Vector3f> vector) {
        return func(Vector3f::getZ, vector);
    }

    public static Vector3fBinding normalize(ObservableVector3f vector) {
        return new Vector3fBinding(() -> vector.getValue().normalize(), vector);
    }

    public String toString() {
        return isValid() ? "Vector [" + getValue() + "]"
                : "Vector [invalid]";
    }
}
