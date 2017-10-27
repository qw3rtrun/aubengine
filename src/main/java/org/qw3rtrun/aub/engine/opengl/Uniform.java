package org.qw3rtrun.aub.engine.opengl;

import javafx.beans.property.Property;
import org.lwjgl.opengl.GL20;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;


public abstract class Uniform<T> {

    private final String name;

    private final Shader shader;

    private final int location;

    private final Property<T> value;

    protected Uniform(String name, Shader shader, int location, Property<T> value) {
        this.name = name;
        this.shader = shader;
        this.location = location;
        this.value = value;
        value.addListener((self, old, _new) -> program(shader.self(), location, _new));
    }

    public static Uniform<Matrix4f> getUniformMat4(Shader shader, String name) {
        return new UniformMat4(name, shader, GL20.glGetUniformLocation(shader.self(), name));
    }

    public static Uniform<Vector3f> getUniform2f(Shader shader, String name) {
        return new Uniform2f(name, shader, GL20.glGetUniformLocation(shader.self(), name));
    }

    public Property<T> valueProperty() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }

    protected abstract void program(int program, int location, T value);

}
