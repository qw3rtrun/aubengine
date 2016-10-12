package org.qw3rtrun.aub.engine.property.vector;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import java.lang.ref.WeakReference;

import static org.qw3rtrun.aub.engine.vectmath.Vector3f.vect3f;

/**
 * Created by strunov on 9/8/2015.
 */
public class Vector3fProperty extends ObservableVector3fBase implements Property<Vector3f>, Vector3fExpression {

    private Vector3f value = Vector3f.ZERO;
    private ObservableValue<? extends Vector3f> observable;
    private InvalidationListener listener = null;

    private boolean valid = true;

    public Vector3fProperty() {
        this(Vector3f.vect3f());
    }

    public Vector3fProperty(Vector3f value) {
        this.value = value;
    }

    public static Vector3fProperty vectProp(Vector3f init) {
        return new Vector3fProperty(init);
    }

    public static Vector3fProperty vectProp(float x, float y, float z) {
        return new Vector3fProperty(Vector3f.vect3f(x, y, z));
    }

    public static Vector3fProperty vectProp() {
        return new Vector3fProperty(Vector3f.vect3f());
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
    public void bind(ObservableValue<? extends Vector3f> newObservable) {
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
    public void bindBidirectional(Property<Vector3f> other) {
        Bindings.bindBidirectional(this, other);
    }

    @Override
    public void unbindBidirectional(Property<Vector3f> other) {
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
    public Vector3f getValue() {
        valid = true;
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void setValue(Vector3f value) {
        if (isBound()) {
            throw new RuntimeException("A bound value cannot be set.");
        }

        if (this.value == null || !this.value.equals(value)) {
            this.value = value;
            markInvalid();
        }
    }

    public void setValue(float x, float y, float z) {
        setValue(vect3f(x, y, z));
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

    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("Vector3fProperty [");
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

        private final WeakReference<Vector3fProperty> wref;

        public Listener(Vector3fProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            Vector3fProperty ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }
    }
}
