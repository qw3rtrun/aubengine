package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.lwjgl.opengl.GL41;

public class Scene {

    final private ObjectProperty<Camera> camera = new SimpleObjectProperty<>(new Camera());

    final private ObservableList<Object> objects = FXCollections.observableArrayList();

    private int pipeline = -1;

    {
        objects.addListener((ListChangeListener<Object>) c -> {
            c.getAddedSubList().forEach(Object::pushToContext);
            c.getRemoved().forEach(Object::popFromContext);
        });

        camera.addListener((observable, oldValue, newValue) -> {
            newValue.use(pipeline);
        });
    }

    public void bind() {
        pipeline = GL41.glGenProgramPipelines();
    }

    public void unbind() {
        if (pipeline > 0) {
            GL41.glDeleteProgramPipelines(pipeline);
            pipeline = -1;
        }
    }

    public void render(){
        GL41.glBindProgramPipeline(pipeline);
        objects.forEach(Object::render);
    }

}
