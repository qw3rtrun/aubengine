package org.qw3rtrun.aub.engine.property.quaternion;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;

import java.lang.ref.WeakReference;

import static org.qw3rtrun.aub.engine.vectmath.Quaternion.Q1;
import static org.qw3rtrun.aub.engine.vectmath.Quaternion.quaternion;

/**
 * Created by strunov on 9/8/2015.
 */
public class Quaternion4fProperty extends ObservableQuaternionBase implements Property<Quaternion>, QuaternionExpression {

    private Quaternion value = Q1;
    private ObservableValue<? extends Quaternion> observable;
    private InvalidationListener listener = null;

    private boolean valid = true;

    public Quaternion4fProperty() {
        this(Q1);
    }

    public Quaternion4fProperty(Quaternion value) {
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
    public void bind(ObservableValue<? extends Quaternion> newObservable) {
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
    public void bindBidirectional(Property<Quaternion> other) {
        Bindings.bindBidirectional(this, other);
    }

    @Override
    public void unbindBidirectional(Property<Quaternion> other) {
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
    public Quaternion getValue() {
        valid = true;
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void setValue(Quaternion value) {
        if (isBound()) {
            throw new RuntimeException("A bound value cannot be set.");
        }

        if (this.value == null || !this.value.equals(value)) {
            this.value = value;
            markInvalid();
        }
    }

    public void setValue(float x, float y, float z, float a) {
        setValue(quaternion(x, y, z, a));
    }

    public void setX(float x) {
        setValue(getValue().withX(x));
    }

    public void setY(float y) {
        setValue(getValue().withY(y));
    }

    public void setZ(float z) {
        setValue(getValue().withZ(z));
    }

    public void setA(float a) {
        setValue(getValue().withA(a));
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

        private final WeakReference<Quaternion4fProperty> wref;

        public Listener(Quaternion4fProperty ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            Quaternion4fProperty ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }
    }
}
