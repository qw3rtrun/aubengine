package org.qw3rtrun.aub.engine.property.bind;

import com.sun.javafx.binding.BidirectionalBinding;
import com.sun.javafx.binding.Logging;
import javafx.beans.WeakListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.function.Function;

/**
 * Created by qw3rtrun on 6/10/17.
 */
public class GenericBidirectionalBinding<T, R> implements ChangeListener<Object>, WeakListener {

    private final WeakReference<Property<T>> tRef;
    private final WeakReference<Property<R>> rRef;
    private final Function<T, R> to;
    private final Function<R, T> from;
    private boolean updating;

    public GenericBidirectionalBinding(Property<T> t, Property<R> r,
                                                Function<T, R> forward, Function<R, T> backward) {
        this.tRef = new WeakReference<Property<T>>(t);
        t.addListener(this);
        this.rRef = new WeakReference<Property<R>>(r);
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
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
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
