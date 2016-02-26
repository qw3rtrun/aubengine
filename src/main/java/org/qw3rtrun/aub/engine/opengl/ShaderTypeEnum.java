package org.qw3rtrun.aub.engine.opengl;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public enum ShaderTypeEnum {

    VERTEX(GL_VERTEX_SHADER),
    FRAGMENT(GL_FRAGMENT_SHADER),;

    private final int code;

    ShaderTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
