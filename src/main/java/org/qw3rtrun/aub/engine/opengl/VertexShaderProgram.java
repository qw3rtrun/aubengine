package org.qw3rtrun.aub.engine.opengl;

public class VertexShaderProgram extends ShaderProgram {
    public VertexShaderProgram(String glsl) {
        super(ShaderTypeEnum.VERTEX, glsl);
    }
}
