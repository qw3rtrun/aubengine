package org.qw3rtrun.aub.engine.property;

import com.sun.javafx.binding.BindingHelperObserver;
import com.sun.javafx.binding.ExpressionHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.ObservableList;

import java.util.function.Supplier;

public class BaseBinding<T> extends ObservableValueBase<T> implements Binding<T> {

    private final Supplier<T> func;
    private T value;
    private boolean valid = false;
    private ExpressionHelper<T> helper = null;

    public BaseBinding(Supplier<T> func, Observable... dependencies) {
        if (func == null || dependencies == null) throw new NullPointerException();
        this.func = func;
        BindingHelperObserver observer = new BindingHelperObserver(this);
        for (final Observable dep : dependencies) {
            dep.addListener(observer);
        }
    }

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    public void dispose() {
    }

    @Override
    public ObservableList<?> getDependencies() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final T getValue() {
        if (!valid) {
            value = func != null ? func.get() : null;
            valid = true;
        }
        return value;
    }

    protected void onInvalidating() {
    }

    @Override
    public final void invalidate() {
        if (valid) {
            valid = false;
            onInvalidating();
            ExpressionHelper.fireValueChangedEvent(helper);
        }
    }

    @Override
    public final boolean isValid() {
        return valid;
    }
}
