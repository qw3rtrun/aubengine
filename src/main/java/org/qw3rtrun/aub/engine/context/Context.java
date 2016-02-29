package org.qw3rtrun.aub.engine.context;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GLContext;
import org.qw3rtrun.aub.engine.scene.Scene;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

public class Context extends Thread {

    private final Window window;

    private final Scene scene;

    public Context(Window window, Scene scene) {
        this.window = window;
        this.scene = scene;
    }

    public static void main(String[] args) {
        Context context = new Context(new Window(), new Scene());
        context.start();
    }

    @Override
    public void run() {

        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

        try {
            window.init();
            init();
            window.show();
            scene.bind();
            loop();
            // Release window and window callbacks
        } catch (InterruptedException e) {
            System.out.println("See u.");
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            scene.unbind();
            window.destroy();
        }
    }

    private void loop() throws InterruptedException {
        while (!isInterrupted()) {
            if (window.isClosing()) {
                throw new InterruptedException();
            }
            scene.render();
            glfwPollEvents();
        }
    }

    private void init() {
        GLContext.createFromCurrent();
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CW);
    }
}
