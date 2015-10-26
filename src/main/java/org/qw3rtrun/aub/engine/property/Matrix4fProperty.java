package org.qw3rtrun.aub.engine.property;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import java.lang.ref.WeakReference;

public class Matrix4fProperty extends ObservableMatrixBase implements Property<Matrix4f>, MatrixExpression {

    private Matrix4f value = Matrix4f.E;
    private ObservableValue<? extends Matrix4f> observable;
    private InvalidationListener listener = null;

    private boolean valid = true;

    public Matrix4fProperty() {
    }

    public Matrix4fProperty(Matrix4f value) {
        this.value = value;
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    protected void invalidated() {
    }

    @Override
    public void bind(ObservableValue<? extends Matrix4f> newObservable) {
        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.getValue();
            observable.removeListener(listener);
            observable = null;
        }
    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public void bindBidirectional(Property<Matrix4f> other) {
        Bindings.bindBidirectional(this, other);
    }

    @Override
    public void unbindBidirectional(Property<Matrix4f> other) {
        Bindings.unbindBidirectional(this, other);
    }

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Matrix4f getValue() {
        valid = true;
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void setValue(Matrix4f value) {
        if (isBound()) {
            throw new RuntimeException("A bound value cannot be set.");
        }

        if (this.value == null || !this.value.equals(value)) {
            this.value = value;
            markInvalid();
        }
    }

    private static class Listener implements InvalidationListener {

        private final WeakReference<Matrix4fProperty> wref;

        public Listener(Matrix4fProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            Matrix4fProperty ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }
    }
}
