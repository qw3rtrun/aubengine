package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.*;
import org.qw3rtrun.aub.engine.mixin.*;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.quaternion.QuaternionBinding;
import org.qw3rtrun.aub.engine.property.transform.Rotation;
import org.qw3rtrun.aub.engine.property.transform.Scaling;
import org.qw3rtrun.aub.engine.property.transform.Translation;
import org.qw3rtrun.aub.engine.property.vector.Vector4fProperty;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableList;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.XYZ;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.ZERO;

public class SceneObject implements Node, Shaped, Tangible {

    private final Vector4fProperty scale = new Vector4fProperty(XYZ) {
        @Override
        public java.lang.Object getBean() {
            return SceneObject.this;
        }

        @Override
        public String getName() {
            return "ScaleBinding of " + name.get();
        }
    };
    private final Vector4fProperty rotation = new Vector4fProperty(ZERO) {
        @Override
        public java.lang.Object getBean() {
            return SceneObject.this;
        }

        @Override
        public String getName() {
            return "Rotation of " + name.get();
        }
    };
    private final Vector4fProperty translation = new Vector4fProperty(ZERO) {
        @Override
        public String getName() {
            return "Translation of " + name.get();
        }

        @Override
        public java.lang.Object getBean() {
            return SceneObject.this;
        }
    };


    private final QuaternionBinding orientation = QuaternionBinding.orientation(rotation);
    private final ObjectProperty<SceneObject> parent = new SimpleObjectProperty<>();
    private final ListProperty<SceneObject> childs = new SimpleListProperty<>(observableList(new ArrayList<>()));
    private final Matrix4fBinding localToAbsolute = new Translation(translation).asMatrix()
            .concat(new Rotation(orientation).asMatrix())
            .concat(new Scaling(scale).asMatrix());
    //.concat(ori)
    private final Matrix4fBinding absoluteToLocal = new Scaling(scale).invert().asMatrix()
            .concat(new Rotation(orientation).invert().asMatrix())
            .concat(new Translation(translation).invert().asMatrix());
    private StringProperty name = new SimpleStringProperty(super.toString());

    public StringProperty name() {
        return name;
    }

    @Override
    public ObjectProperty<SceneObject> parent() {
        return parent;
    }

    @Override
    public ListProperty<SceneObject> childs() {
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

