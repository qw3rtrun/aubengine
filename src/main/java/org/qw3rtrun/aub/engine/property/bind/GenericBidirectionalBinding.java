package org.qw3rtrun.aub.engine.property.bind;

import javafx.beans.WeakListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;
import java.util.function.Function;

public class GenericBidirectionalBinding<T, R> implements ChangeListener<Object>, WeakListener {

    private final WeakReference<Property<T>> tRef;
    private final WeakReference<Property<R>> rRef;
    private final Function<T, R> to;
    private final Function<R, T> from;
    private boolean updating;

    public GenericBidirectionalBinding(Property<T> t, Property<R> r,
                                       Function<T, R> forward, Function<R, T> backward) {
        this.tRef = new WeakReference<>(t);
        t.addListener(this);
        this.rRef = new WeakReference<>(r);
        r.addListener(this);
        this.to = forward;
        this.from = backward;
    }

    protected Object getT() {
        return tRef.get();
    }

    protected Object getR() {
        return rRef.get();
    }

    @Override
    public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
        if (!updating) {
            final Property<T> t = tRef.get();
            final Property<R> r = rRef.get();
            if ((t == null) || (r == null)) {
                if (t != null) {
                    t.removeListener(this);
                }
                if (r != null) {
                    r.removeListener(this);
                }
            } else {
                try {
                    updating = true;
                    if (t == observable) {
                        try {
                            r.setValue(to.apply(t.getValue()));
                        } catch (Exception e) {
                            r.setValue(null);
                        }
                    } else {
                        try {
                            t.setValue(from.apply(r.getValue()));
                        } catch (Exception e) {
                            t.setValue(null);
                        }
                    }
                } finally {
                    updating = false;
                }
            }
        }
    }

    @Override
    public boolean wasGarbageCollected() {
        return (getT() == null) || (getR() == null);
    }
}
