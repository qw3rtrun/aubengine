package org.qw3rtrun.aub.engine.context;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Window {
    private final GLFWErrorCallback errorCallback = errorCallbackPrint(System.err);
    private final GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, GL11.GL_TRUE); // We will detect this in our rendering loop
        }
    };

    private long pointer = MemoryUtil.NULL;

    private int width = 800;
    private int heigth = 600;

    private String caption = "Auberengine";


    public Window() {
    }

    public Window(int width, int heigth, String caption) {
        this.width = width;
        this.heigth = heigth;
        this.caption = caption;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public String getCaption() {
        return caption;
    }

    public boolean isInit() {
        return pointer > 0;
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL11.GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_FALSE); // the window will be resizable

        // Create the window
        pointer = glfwCreateWindow(width, heigth, caption, MemoryUtil.NULL, MemoryUtil.NULL);
        if (pointer == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(pointer, keyCallback);

        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                pointer,
                (GLFWvidmode.width(vidmode) - width) / 2,
                (GLFWvidmode.height(vidmode) - heigth) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(pointer);
        // Enable v-sync
        glfwSwapInterval(1);
    }

    public void show() {
        // Make the window visible
        glfwShowWindow(pointer);
    }

    public void destroy() {
        if (isInit()) {
            glfwDestroyWindow(pointer);
            errorCallback.release();
        }
        glfwTerminate();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    public boolean isClosing() {
        return !isInit() || glfwWindowShouldClose(pointer) == GL_TRUE;
    }
}
