package org.qw3rtrun.aub.engine.mixin;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import org.qw3rtrun.aub.engine.property.transform.Transformation;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector3f;
import org.qw3rtrun.aub.engine.property.vector.Vector3fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;

/**
 * Created by strunov on 9/23/2015.
 */
public interface Node {

    public ObjectProperty<? extends Node> parent();

    public ListProperty<? extends Node> children();

    public Vector3fProperty translation();

    public Vector3fProperty orientation();

    public Vector3fProperty scale();

    public Transformation localToAbsolute();

    public Transformation absoluteToLocal();

    default public Vector3fBinding toLocal(ObservableVector3f absolute) {
        return absoluteToLocal().apply(absolute);
    }

    default public Vector3fBinding toAbsolute(ObservableVector3f local) {
        return localToAbsolute().apply(local);
    }
}
