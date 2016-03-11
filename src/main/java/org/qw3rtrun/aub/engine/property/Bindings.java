package org.qw3rtrun.aub.engine.property;

import com.sun.javafx.binding.FloatConstant;
import javafx.beans.Observable;
import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.matrix.ObservableMatrix;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fConstant;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Function;

import static com.sun.javafx.binding.FloatConstant.valueOf;
import static java.util.Arrays.asList;
import static org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding.binding;
import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.*;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

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


    public static Matrix4fBinding rotationMatrix(Vector4f quaternion) {
        return rotationMatrix(new Vector4fConstant(quaternion));
    }

    public static QuaternionBinding axisRotation(ObservableVector axis, ObservableFloatValue radian) {
        return new QuaternionBinding(() -> {
            double a2 = radian.doubleValue() / 2;
            float sin = (float) Math.sin(a2);
            float cos = (float) Math.cos(a2);
            return quaternion(axis.getX() * sin, axis.getY() * sin, axis.getZ() * sin, cos);
        }, axis, radian);
    }

    public static QuaternionBinding orientation(ObservableVector rotation) {
        return axisRotation(CONST_X, FloatConstant.valueOf(rotation.getX()))
                .product(axisRotation(CONST_Y, FloatConstant.valueOf(rotation.getY()))
                        .product(axisRotation(CONST_Z, FloatConstant.valueOf(rotation.getZ()))));
    }

}
