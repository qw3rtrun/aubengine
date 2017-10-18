package org.qw3rtrun.aub.engine.opengl;

import java.util.function.IntSupplier;

public abstract class GLObject {

    private int self = -1;

    protected GLObject(IntSupplier glGen) {
        self = glGen.getAsInt();
    }

    public final void delete() {
        glDelete(self);
    }

    protected abstract void glDelete(int pointer);

    public final boolean isAttached() {
        return self > 0;
    }

    public int self() {
        return self;
    }

    @Override
    protected void finalize() throws Throwable {
        delete();
        super.finalize();
    }
}
