package org.qw3rtrun.aub.engine.opengl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL41;

import static org.lwjgl.opengl.GL41.*;

public class Pipeline extends GLObject {

    private final VertexShader vertexShader;
    private final FragmentShader fragmentShader;

    public Pipeline(VertexShader vertexShader, FragmentShader fragmentShader) {
        super(() -> {
            int p = GL41.glGenProgramPipelines();
            glUseProgramStages(p, GL_VERTEX_SHADER_BIT, vertexShader.self());
            glUseProgramStages(p, GL_FRAGMENT_SHADER_BIT, fragmentShader.self());
            return p;
        });
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }

    @Override
    protected void glDelete(int pointer) {
        GL41.glDeleteProgramPipelines(pointer);
    }

    public void bind() {
        GL20.glUseProgram(0);
        GL41.glBindProgramPipeline(self());
    }

    public void unbind() {
        if (GL41.glIsProgramPipeline(self())) {
            GL41.glBindProgramPipeline(0);
        }
    }

    public boolean isBind(){
        return isAttached() && GL41.glIsProgramPipeline(self());
    }
}
