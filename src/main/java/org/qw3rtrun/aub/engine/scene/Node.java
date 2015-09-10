package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.transform.Transform;
import org.qw3rtrun.aub.engine.property.Vector3fProperty;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableList;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vXYZ;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.vZERO;

/**
 * Created by strunov on 9/4/2015.
 */
public class Node {

    private final Vector3fProperty scale = new Vector3fProperty(vXYZ) {
        @Override
        public Object getBean() {
            return Node.this;
        }

        @Override
        public String getName() {
            return "Node Transformation: Scale";
        }
    };
    private final Vector3fProperty rotation = new Vector3fProperty(vZERO) {
        @Override
        public Object getBean() {
            return Node.this;
        }

        @Override
        public String getName() {
            return "Node Transformation: Rotation";
        }
    };
    private final Vector3fProperty translation = new Vector3fProperty(vZERO) {
        @Override
        public String getName() {
            return "Node Transformation: Translation";
        }

        @Override
        public Object getBean() {
            return Node.this;
        }
    };

    private ObjectProperty<Transform> local = new SimpleObjectProperty<>();
    private ObjectProperty<Transform> absolute = new SimpleObjectProperty<>();

    private ObjectProperty<Node> parent = new SimpleObjectProperty<>();

    private ListProperty<Node> childs = new SimpleListProperty<>(observableList(new ArrayList<>()));

}

