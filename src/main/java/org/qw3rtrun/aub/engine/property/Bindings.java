package org.qw3rtrun.aub.engine.property;

import javafx.beans.Observable;
import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Function;

import static com.sun.javafx.binding.FloatConstant.valueOf;
import static java.util.Arrays.asList;
import static org.qw3rtrun.aub.engine.property.Matrix4fBinding.binding;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

/**
 * Created by strunov on 9/8/2015.
 */
public class Bindings {

    private static Vector4fBinding multiply0(ObservableNumberValue k, ObservableValue<Vector4f> v, Observable... dependencies) {
        return new Vector4fBinding() {{
            bind(() -> v.getValue().multiply(k.floatValue()), dependencies);
        }};
    }

    private static Vector4fBinding add0(ObservableValue<Vector4f> v1, ObservableValue<Vector4f> v2, Observable... dependencies) {
        return new Vector4fBinding() {{
            bind(() -> v1.getValue().add(v2.getValue()), dependencies);
        }};
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

    @SafeVarargs
    public static Vector4fBinding add(ObservableValue<Vector4f>... vectors) {
        switch (vectors.length) {
            case 0:
                return new Vector4fBinding() {{
                    bind(() -> ZERO);
                }};
            case 1:
                return new Vector4fBinding() {{
                    bind(vectors[0]::getValue, vectors[0]);
                }};
            case 2:
                return add0(vectors[0], vectors[1], vectors);
            default:
                return new Vector4fBinding() {{
                    bind(() -> vect().addAll(asList(vectors).stream().map(ObservableValue::getValue).toArray(Vector4f[]::new)), vectors);
                }};
        }
    }

    public static Vector4fBinding vector(ObservableFloatValue x, ObservableFloatValue y, ObservableFloatValue z) {
        return new Vector4fBinding() {{
            bind(() -> vect(x.get(), y.get(), z.get()), x, y, z);
        }};
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
        return new Vector4fBinding() {{
            bind(() -> matrix.getValue().multiply(vector.getValue()), matrix, vector);
        }};
    }

    public static Vector4fBinding product(ObservableValue<Matrix4f> matrix, Vector4f vector) {
        return new Vector4fBinding() {{
            bind(() -> matrix.getValue().multiply(vector), matrix);
        }};
    }

    public static Vector4fBinding product(Matrix4f matrix, ObservableValue<Vector4f> vector) {
        return new Vector4fBinding() {{
            bind(() -> matrix.multiply(vector.getValue()), vector);
        }};
    }

    public static Matrix4fBinding multiply(ObservableMatrix m1, ObservableMatrix m2) {
        return binding(() -> m1.getValue().multiply(m2.getValue()), m1, m2);
    }

    public static Matrix4fBinding translationMatrix(ObservableValue<Vector4f> translation) {
        return binding(() -> Matrix4f.cols(X, Y, Z, translation.getValue().w(1)), translation);
    }

    public static Matrix4fBinding scaleMatrix(ObservableValue<Vector4f> scale) {
        return binding(() -> {
                final Vector4f s = scale.getValue();
                return Matrix4f.rows(X.multiply(s.x), Y.multiply(s.y), Z.multiply(s.z), W);
            }, scale);
    }

    public static Matrix4fBinding rotationMatrix(ObservableValue<Vector4f> quaternion) {
        return new Matrix4fBinding(
            () -> {
                double x = quaternion.getValue().x;
                double y = quaternion.getValue().y;
                double z = quaternion.getValue().z;
                double a = quaternion.getValue().w;
                double C = Math.cos(a);
                double S = Math.sin(a);
                double iC = 1 - C;
                double x2 = x * x;
                double y2 = y * y;
                double z2 = z * z;
                return matr(
                        (float) (x2 + (1 - x2) * C), (float) (iC * x * y - z * S), (float) (iC * x * z + y * S),
                        (float) (iC * x * y + z * S), (float) (y2 + (1 - y2) * C), (float) (iC * y * z - x * S),
                        (float) (iC * x * z - y * S), (float) (iC * y * z + x * S), (float) (z2 + (1 - z2) * C));
            }, quaternion);
    }

    public static Matrix4fBinding rotationMatrix(Vector4f quaterion) {
        return rotationMatrix(new Vector4fConstant(quaterion));
    }

}
