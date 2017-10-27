package org.qw3rtrun.aub.engine.opengl;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fProperty;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4fv;

public class UniformMat4 extends Uniform<Matrix4f> {

    UniformMat4(String name, Shader shader, int location) {
        super(name, shader, location, new Matrix4fProperty());
    }

    @Override
    protected void program(int program, int location, Matrix4f value) {
        glProgramUniformMatrix4fv(program, location, false, value.toBuffer());
    }
}
