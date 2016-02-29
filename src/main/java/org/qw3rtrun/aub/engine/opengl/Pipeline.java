package org.qw3rtrun.aub.engine.opengl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL41;

public class Pipeline extends GLObject {

    @Override
    protected int glGen() {
        return GL41.glGenProgramPipelines();
    }

    @Override
    protected void glDelete(int pointer) {
        GL41.glDeleteProgramPipelines(pointer);
    }

    public void bind() {
        assertAttached();
        GL20.glUseProgram(0);
        GL41.glBindProgramPipeline(pointer());
    }

    public void unbind() {
        assertAttached();
        if (GL41.glIsProgramPipeline(pointer())) {
            GL41.glBindProgramPipeline(0);
        }
    }

    public boolean isBind(){
        return isAttached() && GL41.glIsProgramPipeline(pointer());
    }
}
