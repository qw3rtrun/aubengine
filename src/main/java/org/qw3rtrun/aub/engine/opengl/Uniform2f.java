package org.qw3rtrun.aub.engine.opengl;

import org.qw3rtrun.aub.engine.property.vector.Vector3fProperty;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import static org.lwjgl.opengl.GL41.glProgramUniform2f;

public class Uniform2f extends Uniform<Vector3f> {

    Uniform2f(String name, Shader shader, int location) {
        super(name, shader, location, new Vector3fProperty());
    }

    @Override
    protected void program(int program, int location, Vector3f value) {
        glProgramUniform2f(program, location, value.x, value.y);
    }
}
