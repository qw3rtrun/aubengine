package org.qw3rtrun.aub.engine.property;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

import java.lang.ref.WeakReference;

import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vect;

/**
 * Created by strunov on 9/8/2015.
 */
public class Vector4fProperty extends ObservableVectorBase implements Property<Vector4f>, VectorExpression {

    private Vector4f value = Vector4f.ZERO;
    private ObservableValue<? extends Vector4f> observable;
    private InvalidationListener listener = null;

    private boolean valid = true;

    public Vector4fProperty() {
        this(vect());
    }

    public Vector4fProperty(Vector4f value) {
        this.value = value;
    }

    public static Vector4fProperty vectProp(Vector4f init) {
        return new Vector4fProperty(init);
    }

    public static Vector4fProperty vectProp(float x, float y, float z, float w) {
        return new Vector4fProperty(vect(x, y, z, w));
    }

    public static Vector4fProperty vectProp(float x, float y, float z) {
        return new Vector4fProperty(vect(x, y, z));
    }

    public static Vector4fProperty vectProp() {
        return new Vector4fProperty(vect());
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
    public void bind(ObservableValue<? extends Vector4f> newObservable) {
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
    public void bindBidirectional(Property<Vector4f> other) {
        Bindings.bindBidirectional(this, other);
    }

    @Override
    public void unbindBidirectional(Property<Vector4f> other) {
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
    public Vector4f getValue() {
        valid = true;
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void setValue(Vector4f value) {
        if (isBound()) {
            throw new RuntimeException("A bound value cannot be set.");
        }

        if (this.value == null || !this.value.equals(value)) {
            this.value = value;
            markInvalid();
        }
    }

    public void setValue(float x, float y, float z, float w) {
        setValue(vect(x, y, z, w));
    }

    public void setX(float x) {
        setValue(getValue().x(x));
    }

    public void setY(float y) {
        setValue(getValue().y(y));
    }

    public void setZ(float z) {
        setValue(getValue().z(z));
    }

    public void setW(float w) {
        setValue(getValue().w(w));
    }

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("Vector4fProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(getValue());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(getValue());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener implements InvalidationListener {

        private final WeakReference<Vector4fProperty> wref;

        public Listener(Vector4fProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            Vector4fProperty ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }
    }
}
