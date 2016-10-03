package org.qw3rtrun.aub.engine.property.matrix;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.property.BaseBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.util.function.Supplier;

/**
 * Created by strunov on 9/23/2015.
 */
public class Matrix4fBinding extends BaseBinding<Matrix4f> implements Binding<Matrix4f>, MatrixExpression {

    public Matrix4fBinding(Supplier<Matrix4f> f, Observable... dependencies) {
        super(f, dependencies);
    }

    public static Matrix4fBinding binding(Supplier<Matrix4f> f, Observable... dependencies) {
        return new Matrix4fBinding(f, dependencies);
    }

    public static Matrix4fBinding identity(ObservableMatrix matrix) {
        return new Matrix4fBinding(matrix::get);
    }

    public static Matrix4fBinding multiply(ObservableMatrix m1, ObservableMatrix m2) {
        return binding(() -> m1.getValue().multiply(m2.getValue()), m1, m2);
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

    @Override
    public String toString() {
        return isValid() ? "Matrix4fBinding [value: " + getValue() + "]"
                : "Matrix4fBinding [invalid]";
    }
}

