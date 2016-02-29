package org.qw3rtrun.aub.engine.opengl;

public abstract class GLObject {

    private int pointer = -1;

    public final void gen() {
        pointer = glGen();
    }

    protected abstract int glGen();

    public final void delete() {
        assertAttached();
        glDelete(pointer);
    }

    protected abstract void glDelete(int pointer);

    public final boolean isAttached() {
        return pointer > 0;
    }

    public int pointer() {
        return pointer;
    }

    public void assertAttached() throws IllegalStateException {
        if (pointer < 1) {
            throw new IllegalStateException("GL Object isn't attached");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        delete();
        super.finalize();
    }
}
