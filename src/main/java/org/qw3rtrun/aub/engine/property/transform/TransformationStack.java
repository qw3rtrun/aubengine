package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fConstant;
import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;

public interface TransformationStack {

    Vector4fBinding apply(ObservableVector source);

    Matrix4fBinding asMatrix();

    TransformationStack invert();

    TransformationStack scale(ObservableVector scale);

    TransformationStack rotate(ObservableQuaternion rotation);

    TransformationStack translate(ObservableVector translate);

    static class Root implements TransformationStack {
        @Override
        public Vector4fBinding apply(ObservableVector source) {
            return Vector4fBinding.identity(source);
        }

        @Override
        public Matrix4fBinding asMatrix() {
            return Matrix4fBinding.identity(Matrix4fConstant.CONST_E);
        }

        @Override
        public TransformationStack invert() {
            return this;
        }

        @Override
        public TransformationStack scale(ObservableVector scale) {
            return null;
        }

        @Override
        public TransformationStack rotate(ObservableQuaternion rotation) {
            return null;
        }

        @Override
        public TransformationStack translate(ObservableVector translate) {
            return null;
        }
    }

    static class AbstractTransformation implements TransformationStack {

        private final TransformationStack previous;

        public AbstractTransformation(TransformationStack previous) {
            this.previous = previous;
        }

        @Override
        public Vector4fBinding apply(ObservableVector source) {
            return apply(previous.apply(source));
        }

        @Override
        public Matrix4fBinding asMatrix() {
            return null;
        }

        @Override
        public TransformationStack invert() {
            return null;
        }

        @Override
        public TransformationStack scale(ObservableVector scale) {
            return null;
        }

        @Override
        public TransformationStack rotate(ObservableQuaternion rotation) {
            return null;
        }

        @Override
        public TransformationStack translate(ObservableVector translate) {
            return null;
        }
    }
}
