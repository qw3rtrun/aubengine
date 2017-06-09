package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.*;
import org.qw3rtrun.aub.engine.mixin.*;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.transform.Transformation;
import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableList;

public class SceneObject implements Node, Shaped, Tangible {

    private final Vector3fProperty scale = new Vector3fProperty(Vector3f.XYZ) {
        @Override
        public java.lang.Object getBean() {
            return SceneObject.this;
        }

        @Override
        public String getName() {
            return "ScaleBinding of " + name.get();
        }
    };
    private final Vector3fProperty rotation = new Vector3fProperty(Vector3f.ZERO) {
        @Override
        public java.lang.Object getBean() {
            return SceneObject.this;
        }

        @Override
        public String getName() {
            return "Rotation of " + name.get();
        }
    };
    private final Vector3fProperty translation = new Vector3fProperty(Vector3f.ZERO) {
        @Override
        public String getName() {
            return "Translation of " + name.get();
        }

        @Override
        public java.lang.Object getBean() {
            return SceneObject.this;
        }
    };

    private final ObjectProperty<SceneObject> parent = new SimpleObjectProperty<>();
    private final ListProperty<SceneObject> childs = new SimpleListProperty<>(observableList(new ArrayList<>()));
    private final Transformation localToAbsolute = Transformation.start()
            .scale(scale)
            .rotate(QuaternionBinding.orientation(rotation))
            .translate(translation);
    private final Transformation absoluteToLocal = localToAbsolute.invert();

    private StringProperty name = new SimpleStringProperty(super.toString());

    public StringProperty name() {
        return name;
    }

    @Override
    public ObjectProperty<SceneObject> parent() {
        return parent;
    }

    @Override
    public ListProperty<SceneObject> children() {
        return childs;
    }

    @Override
    public Vector3fProperty translation() {
        return translation;
    }

    @Override
    public Vector3fProperty orientation() {
        return rotation;
    }

    @Override
    public Vector3fProperty scale() {
        return scale;
    }

    @Override
    public Transformation localToAbsolute() {
        return localToAbsolute;
    }

    @Override
    public Transformation absoluteToLocal() {
        return absoluteToLocal;
    }

    void pushToContext() {
        mesh().ifPresent(Mesh::push);
        material().ifPresent(Material::push);
    }

    void popFromContext() {
        mesh().ifPresent(Mesh::pop);
        material().ifPresent(Material::pop);
    }

    public void render() {
    }
}

