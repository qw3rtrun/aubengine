package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Scene {

    final private ObjectProperty<Camera> camera = new SimpleObjectProperty<>(new Camera());

    final private ObservableList<Object> objects = FXCollections.observableArrayList();

    {
        objects.addListener((ListChangeListener<Object>) c -> {
            c.getAddedSubList().forEach(Object::pushToContext);
            c.getRemoved().forEach(Object::popFromContext);
        });

        camera.addListener((observable, oldValue, newValue) -> {
            newValue.makeMain();
        });
    }

    public void render(){
        camera.get().use();
        objects.forEach(Object::render);
    }

}
