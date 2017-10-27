package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.qw3rtrun.aub.engine.opengl.Pipeline;

public class Scene {

    final private ObjectProperty<Camera> camera = new SimpleObjectProperty<>(new Camera());

    final private ObservableList<SceneObject> objects = FXCollections.observableArrayList();

    private final Pipeline pipeline = new Pipeline(null, null);

    {
        objects.addListener((ListChangeListener<SceneObject>) c -> {
            c.getAddedSubList().forEach(SceneObject::pushToContext);
            c.getRemoved().forEach(SceneObject::popFromContext);
        });
    }

    public void bind() {
        pipeline.bind();
    }

    public void unbind() {
        pipeline.unbind();
        objects.clear();
    }

    public void render() throws InterruptedException {
        objects.forEach(SceneObject::render);
    }

}
