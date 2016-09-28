package org.qw3rtrun.aub.engine.context;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
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

    public void logVersion() {
        System.out.println("LWJGL " + Version.getVersion() + "!");
        System.out.println("OpenGL 2.0 - " + GL.getCapabilities().OpenGL21);
        System.out.println("OpenGL 4.1 - " + GL.getCapabilities().OpenGL41);
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
        GL.createCapabilities();
        logVersion();
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CW);
    }
}
