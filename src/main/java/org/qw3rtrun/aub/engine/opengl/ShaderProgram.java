package org.qw3rtrun.aub.engine.opengl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL41;

public abstract class ShaderProgram extends GLObject {

    private final ShaderTypeEnum type;

    private final String glsl;

    public ShaderProgram(ShaderTypeEnum type, String glsl) {
        this.type = type;
        this.glsl = glsl;
    }

    public ShaderTypeEnum getType() {
        return type;
    }

    public String getGLSL() {
        return glsl;
    }

    @Override
    protected int glGen() {
        return GL41.glCreateShaderProgramv(type.getCode(), glsl);
    }

    @Override
    protected void glDelete(int pointer) {
        GL20.glDeleteProgram(pointer);
    }
}
