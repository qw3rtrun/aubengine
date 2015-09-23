package org.qw3rtrun.aub.engine.property;

import com.sun.javafx.binding.BindingHelperObserver;
import com.sun.javafx.binding.ExpressionHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import java.util.function.Supplier;

/**
 * Created by strunov on 9/23/2015.
 */
public class Matrix4fBinding extends ObservableValueBase<Matrix4f> implements Binding<Matrix4f> {

    private Supplier<Matrix4f> func;
    private Matrix4f value;
    private boolean valid = false;
    private BindingHelperObserver observer;
    private ExpressionHelper<Matrix4f> helper = null;

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Matrix4f> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Matrix4f> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected final void bind(Supplier<Matrix4f> func, Observable... dependencies) {
        if (func != null && dependencies != null && dependencies.length > 0) {
            this.func = func;
            if (observer == null) {
                observer = new BindingHelperObserver(this);
            }
            for (final Observable dep : dependencies) {
                dep.addListener(observer);
            }
        }
    }

    protected final void unbind(Observable... dependencies) {
        if (observer != null) {
            for (final Observable dep : dependencies) {
                dep.removeListener(observer);
            }
            func = null;
            observer = null;
        }
    }

    public void dispose() {
    }

    @Override
    public ObservableList<?> getDependencies() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public final Matrix4f getValue() {
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

    @Override
    public String toString() {
        return valid ? "Matrix4fBinding [value: " + getValue() + "]"
                : "Matrix4fBinding [invalid]";
    }
}
