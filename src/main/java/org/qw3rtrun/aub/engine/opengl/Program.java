package org.qw3rtrun.aub.engine.opengl;

public class Program<T extends Shader> {

    private final T shader;
    private final Uniforms uniforms;

    public Program(T shader, Uniforms uniforms) {
        this.shader = shader;
        this.uniforms = uniforms;
    }

    public T getShader() {
        return shader;
    }

    public Uniforms getUniforms() {
        return uniforms;
    }
}
