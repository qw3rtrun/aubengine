package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fConstant;
import org.qw3rtrun.aub.engine.property.quaternion.ObservableQuaternion;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;

public abstract class Transformation {

    public abstract Rotation rotate(ObservableQuaternion rotation);

    public abstract Scaling scale(ObservableVector3f scaling);

    public abstract Translation translate(ObservableVector3f translation);

    public abstract Vector3fBinding apply(ObservableVector3f source);

    public abstract Matrix4fBinding asMatrix();

    public abstract Transformation invert();

    protected abstract Transformation invert(Transformation invertChain);

    abstract static class AbstractTransformation extends Transformation {

        private final Transformation chain;

        AbstractTransformation() {
            this(root);
        }

        AbstractTransformation(Transformation chain) {
            this.chain = chain;
        }

        Transformation getChain() {
            return chain;
        }

        public Vector3fBinding apply(ObservableVector3f source) {
            return chain.apply(source);
        }

        public Matrix4fBinding asMatrix() {
            return chain.asMatrix();
        }

        public Transformation invert() {
            return this.invert(root);
        }

        protected abstract Transformation invert(Transformation chain);

        public Rotation rotate(ObservableQuaternion rotation) {
            return new Rotation(getChain(), rotation);
        }

        public Scaling scale(ObservableVector3f scaling) {
            return new Scaling(getChain(), scaling);
        }

        public Translation translate(ObservableVector3f translation) {
            return new Translation(getChain(), translation);
        }

        @Override
        public int hashCode() {
            return chain != null ? chain.hashCode() : 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AbstractTransformation)) return false;

            AbstractTransformation that = (AbstractTransformation) o;

            return chain != null ? chain.equals(that.chain) : that.chain == null;
        }
    }

    protected static Transformation root = new Transformation() {

        @Override
        public Transformation invert() {
            return this;
        }

        @Override
        protected Transformation invert(Transformation invertChain) {
            return invertChain;
        }

        public Matrix4fBinding asMatrix() {
            return Matrix4fBinding.identity(Matrix4fConstant.CONST_E);
        }

        public Rotation rotate(ObservableQuaternion rotation) {
            return new Rotation(rotation);
        }

        public Scaling scale(ObservableVector3f scaling) {
            return new Scaling(scaling);
        }

        public Translation translate(ObservableVector3f translation) {
            return new Translation(translation);
        }

        @Override
        public Vector3fBinding apply(ObservableVector3f source) {
            return Vector3fBinding.identity(source);
        }
    };

    public static Transformation start() {
        return root;
    }
}
