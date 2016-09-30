package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.property.BaseBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.sun.javafx.binding.FloatConstant.valueOf;
import static java.util.Arrays.stream;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.ZERO;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

public class Vector4fBinding extends BaseBinding<Vector4f> implements Binding<Vector4f>, VectorExpression {

    public Vector4fBinding(Supplier<Vector4f> func, Observable... dependencies) {
        super(func, dependencies);
    }

    private static Vector4fBinding multiply0(ObservableNumberValue k, ObservableValue<Vector4f> v, Observable... dependencies) {
        return new Vector4fBinding(() -> v.getValue().multiply(k.floatValue()), dependencies);
    }

    private static Vector4fBinding add0(ObservableValue<Vector4f> v1, ObservableValue<Vector4f> v2, Observable... dependencies) {
        return new Vector4fBinding(() -> v1.getValue().add(v2.getValue()), dependencies);
    }

    public static Vector4fBinding identity(ObservableVector vector) {
        return new Vector4fBinding(vector::get, vector);
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

    public static Vector4fBinding inverse(ObservableVector vector) {
        return Vector4fBinding.multiply(-1, vector);
    }

    public static Vector4fBinding multiply(ObservableNumberValue k, ObservableValue<Vector4f> v) {
        return multiply0(k, v, k, v);
    }

    public static Vector4fBinding multiply(float k, ObservableValue<Vector4f> v) {
        return multiply0(valueOf(k), v, v);
    }

    public static Vector4fBinding multiply(ObservableFloatValue k, Vector4f v) {
        return multiply0(k, new Vector4fConstant(v), k);
    }

    public static Vector4fBinding add(ObservableValue<Vector4f> v1, ObservableValue<Vector4f> v2) {
        return add0(v1, v2, v1, v2);
    }

    public static Vector4fBinding add(ObservableValue<Vector4f> v1, Vector4f v2) {
        return add0(v1, new Vector4fConstant(v2), v1);
    }

    public static Vector4fBinding add(ObservableVector... vectors) {
        switch (vectors.length) {
            case 0:
                return new Vector4fBinding(() -> ZERO);
            case 1:
                return new Vector4fBinding(vectors[0]::getValue, vectors[0]);
            case 2:
                return add0(vectors[0], vectors[1], vectors[0], vectors[1]);
            default:
                return new Vector4fBinding(() -> vect().addAll(stream(vectors)
                        .map(ObservableValue::getValue).toArray(Vector4f[]::new)), vectors);
        }
    }

    public static Vector4fBinding vector(ObservableFloatValue x, ObservableFloatValue y, ObservableFloatValue z) {
        return new Vector4fBinding(() -> vect(x.get(), y.get(), z.get()), x, y, z);
    }

    public static ObservableFloatValue x(ObservableValue<Vector4f> vector) {
        return func(Vector4f::getX, vector);
    }

    public static ObservableFloatValue y(ObservableValue<Vector4f> vector) {
        return func(Vector4f::getY, vector);
    }

    public static ObservableFloatValue z(ObservableValue<Vector4f> vector) {
        return func(Vector4f::getZ, vector);
    }

    public static Vector4fBinding product(ObservableValue<Matrix4f> matrix, ObservableValue<Vector4f> vector) {
        return new Vector4fBinding(() -> matrix.getValue().multiply(vector.getValue()), matrix, vector);
    }

    public static Vector4fBinding product(ObservableValue<Matrix4f> matrix, Vector4f vector) {
        return new Vector4fBinding(() -> matrix.getValue().multiply(vector), matrix);
    }

    public static Vector4fBinding product(Matrix4f matrix, ObservableValue<Vector4f> vector) {
        return new Vector4fBinding(() -> matrix.multiply(vector.getValue()), vector);
    }

    public static Vector4fBinding normalize(ObservableVector vector) {
        return new Vector4fBinding(() -> vector.getValue().normalize(), vector);
    }

    public String toString() {
        return isValid() ? "Vector [" + getValue() + "]"
                : "Vector [invalid]";
    }
}
