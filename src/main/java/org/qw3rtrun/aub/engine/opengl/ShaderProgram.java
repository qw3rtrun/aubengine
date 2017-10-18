package org.qw3rtrun.aub.engine.opengl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL41;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram extends GLObject {

    private final ShaderTypeEnum type;

    private final String glsl;

    ShaderProgram(ShaderTypeEnum type, String glsl) {
        super(() -> {
            int shader = GL41.glCreateShaderProgramv(type.getCode(), glsl);
            int status = glGetProgrami(shader, GL_LINK_STATUS);
            if (status == GL_FALSE) {
                int infoLogLength = glGetProgrami(shader, GL_INFO_LOG_LENGTH);
                String infoLog = glGetProgramInfoLog(shader, infoLogLength);
                throw new ShaderProgramCompileException(String.format("Compile failure in %s shader:\n%s\n", type, infoLog.trim()));
            }
            return shader;
        });
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
    protected void glDelete(int pointer) {
        GL20.glDeleteProgram(pointer);
    }
}
