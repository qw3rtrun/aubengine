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

    private final Pipeline pipeline = new Pipeline();

    {
        objects.addListener((ListChangeListener<SceneObject>) c -> {
            c.getAddedSubList().forEach(SceneObject::pushToContext);
            c.getRemoved().forEach(SceneObject::popFromContext);
        });

        camera.addListener((observable, oldValue, newValue) -> {
            if (pipeline.isAttached()) {
                newValue.useWith(pipeline);
            }
        });
    }

    public void bind() {
        pipeline.gen();
        camera.get().useWith(pipeline);
    }

    public void unbind() {
        pipeline.delete();
        objects.clear();
    }

    public void render() throws InterruptedException {
        pipeline.bind();
        objects.forEach(SceneObject::render);
    }

}
