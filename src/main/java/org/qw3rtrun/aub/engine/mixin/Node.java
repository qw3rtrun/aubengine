package org.qw3rtrun.aub.engine.mixin;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector4f;

/**
 * Created by strunov on 9/23/2015.
 */
public interface Node {

    public ObjectProperty<? extends Node> parent();

    public ListProperty<? extends Node> childs();

    public Vector3fProperty translation();

    public Vector3fProperty orientation();

    public Vector3fProperty scale();

    public Matrix4fBinding localToAbsolute();

    public Matrix4fBinding absoluteToLocal();

    default public Vector4f toLocal(Vector4f absolute) {
        return absoluteToLocal().getValue().multiply(absolute);
    }

    default public Vector4f toAbsolute(Vector4f local) {
        return localToAbsolute().getValue().multiply(local);
    }
}
