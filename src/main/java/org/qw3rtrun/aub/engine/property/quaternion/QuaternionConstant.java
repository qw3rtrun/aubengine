package org.qw3rtrun.aub.engine.property.quaternion;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import org.qw3rtrun.aub.engine.vectmath.Quaternion;

public class QuaternionConstant implements ObservableQuaternion, QuaternionExpression {

    private final Quaternion quaternion;

    public QuaternionConstant(Quaternion quaternion) {
        this.quaternion = quaternion;
    }

    @Override
    public Quaternion getValue() {
        return quaternion;
    }

    @Override
    public void addListener(ChangeListener<? super Quaternion> listener) {
    }

    @Override
    public void removeListener(ChangeListener<? super Quaternion> listener) {
    }

    @Override
    public void addListener(InvalidationListener listener) {
    }

    @Override
    public void removeListener(InvalidationListener listener) {
    }
}
