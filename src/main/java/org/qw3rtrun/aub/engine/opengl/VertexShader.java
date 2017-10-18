package org.qw3rtrun.aub.engine.opengl;

public class VertexShader extends ShaderProgram {
    public VertexShader(String glsl) {
        super(ShaderTypeEnum.VERTEX, glsl);
    }
}
