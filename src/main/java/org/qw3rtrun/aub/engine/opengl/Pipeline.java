package org.qw3rtrun.aub.engine.opengl;

import org.lwjgl.opengl.GL41;

/**
 * Created by strunov on 2/26/2016.
 */
public class Pipeline extends GLObject {
    @Override
    protected int glGen() {
        return GL41.glGenProgramPipelines();
    }

    @Override
    protected void glDelete(int pointer) {
        GL41.glDeleteProgramPipelines(pointer);
    }
}
