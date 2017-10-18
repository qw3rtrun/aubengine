package org.qw3rtrun.aub.engine.opengl;

public class FragmentShader extends ShaderProgram {
    public FragmentShader(String glsl) {
        super(ShaderTypeEnum.FRAGMENT, glsl);
    }
}
