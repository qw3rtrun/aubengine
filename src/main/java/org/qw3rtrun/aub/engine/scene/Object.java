package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.*;
import org.qw3rtrun.aub.engine.mixin.Node;
import org.qw3rtrun.aub.engine.mixin.Shaped;
import org.qw3rtrun.aub.engine.property.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.Vector4fProperty;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableList;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vXYZ;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vZERO;

/**
 * Created by strunov on 9/4/2015.
 */
public class Object implements Node, Shaped {

    private final Matrix4fBinding localToAbsolute = null;
    private final Matrix4fBinding absoluteToLocal = null;
    private final ObjectProperty<Object> parent = new SimpleObjectProperty<>();
    private final ListProperty<Object> childs = new SimpleListProperty<>(observableList(new ArrayList<>()));
    private StringProperty name = new SimpleStringProperty(super.toString());
    private final Vector4fProperty scale = new Vector4fProperty(vXYZ) {
        @Override
        public java.lang.Object getBean() {
            return Object.this;
        }

        @Override
        public String getName() {
            return "Scale of " + name.get();
        }
    };
    private final Vector4fProperty rotation = new Vector4fProperty(vZERO) {
        @Override
        public java.lang.Object getBean() {
            return Object.this;
        }

        @Override
        public String getName() {
            return "Rotation of " + name.get();
        }
    };
    private final Vector4fProperty translation = new Vector4fProperty(vZERO) {
        @Override
        public String getName() {
            return "Translation of " + name.get();
        }

        @Override
        public java.lang.Object getBean() {
            return Object.this;
        }
    };

    public StringProperty name() {
        return name;
    }

    @Override
    public ObjectProperty<Object> parent() {
        return parent;
    }

    @Override
    public ListProperty<Object> childs() {
        return childs;
    }

    @Override
    public Vector4fProperty translation() {
        return translation;
    }

    @Override
    public Vector4fProperty rotation() {
        return rotation;
    }

    @Override
    public Vector4fProperty scale() {
        return scale;
    }

    @Override
    public Matrix4fBinding localToAbsolute() {
        return localToAbsolute;
    }

    @Override
    public Matrix4fBinding absoluteToLocal() {
        return absoluteToLocal;
    }
}
