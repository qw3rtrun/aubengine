package org.qw3rtrun.aub.engine.property;

import com.sun.javafx.binding.FloatConstant;
import javafx.beans.Observable;
import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Function;

import static com.sun.javafx.binding.FloatConstant.valueOf;
import static java.util.Arrays.asList;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

/**
 * Created by strunov on 9/8/2015.
 */
public class Bindings {

    private static Vector4fBinding multiply0(ObservableFloatValue k, ObservableValue<Vector4f> v, Observable... dependencies) {
        return new Vector4fBinding() {{
            bind(() -> {
                return v.getValue().multiply(k.get());
            }, dependencies);
        }};
    }

    private static Vector4fBinding add0(ObservableValue<Vector4f> v1, ObservableValue<Vector4f> v2, Observable... dependencies) {
        return new Vector4fBinding() {{
            bind(() -> {
                return v1.getValue().add(v2.getValue());
            }, dependencies);
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

    public static Vector4fBinding multiply(ObservableFloatValue k, ObservableValue<Vector4f> v) {
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
                    bind(() -> {
                        return vZERO;
                    });
                }};
            case 1:
                return new Vector4fBinding() {{
                    bind(vectors[0]::getValue, vectors[0]);
                }};
            case 2:
                return add0(vectors[0], vectors[1], vectors);
            default:
                return new Vector4fBinding() {{
                    bind(() -> {
                        return sum(asList(vectors).stream().map(ObservableValue::getValue).toArray(Vector4f[]::new));
                    }, vectors);
                }};
        }
    }

    public static Vector4fBinding vector(ObservableFloatValue x, ObservableFloatValue y, ObservableFloatValue z) {
        return new Vector4fBinding() {{
            bind(() -> {
                return vect(x.get(), y.get(), z.get());
            }, x, y, z);
        }};
    }

    public static ObservableFloatValue x(ObservableValue<Vector4f> vector) {
        return func(Vector4f::getX, vector);
    }

    public static Vector4fBinding product(ObservableValue<Matrix4f> matrix, ObservableValue<Vector4f> vector) {
        return new Vector4fBinding() {{
            bind(() -> {
                return matrix.getValue().multiply(vector.getValue());
            }, matrix, vector);
        }};
    }

    public static Vector4fBinding product(ObservableValue<Matrix4f> matrix, Vector4f vector) {
        return new Vector4fBinding() {{
            bind(() -> {
                return matrix.getValue().multiply(vector);
            }, matrix);
        }};
    }

    public static Vector4fBinding product(Matrix4f matrix, ObservableValue<Vector4f> vector) {
        return new Vector4fBinding() {{
            bind(() -> {
                return matrix.multiply(vector.getValue());
            }, vector);
        }};
    }

    public static Matrix4fBinding translate(ObservableValue<Vector4f> translation) {
        return new Matrix4fBinding() {{
            bind(() -> {
                return Matrix4f.cols(vX, vY, vZ, translation.getValue().point(1));
            }, translation);
        }};
    }

    public static Matrix4fBinding scale(ObservableValue<Vector4f> scale) {
        return new Matrix4fBinding() {{
            bind(() -> {
                final Vector4f s = scale.getValue();
                return Matrix4f.rows(vX.multiply(s.x), vY.multiply(s.y), vZ.multiply(s.z), 1);
            }, scale);
        }};
    }

    public static Matrix4fBinding rotate(ObservableValue<Vector4f> rotation) {
        return new Matrix4fBinding() {{
            FloatBinding c = func(x -> {
                return Math.cos(x.doubleValue());
            }, FloatConstant.valueOf(1));
        }};
    }
}
