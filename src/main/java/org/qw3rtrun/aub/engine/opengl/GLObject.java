package org.qw3rtrun.aub.engine.opengl;

public abstract class GLObject {

    private int pointer = -1;

    public final void gen() {
        pointer = glGen();
    }

    protected abstract int glGen();

    public final void delete() {
        if (isBind()) {
            glDelete(pointer);
        }
    }

    protected abstract void glDelete(int pointer);

    public final boolean isBind() {
        return pointer > 0;
    }

    public int pointer() {
        return pointer;
    }

    @Override
    protected void finalize() throws Throwable {
        delete();
        super.finalize();
    }
}
