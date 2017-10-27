package org.qw3rtrun.aub.engine.demos;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.Configuration;
import org.qw3rtrun.aub.engine.context.Window;
import org.qw3rtrun.aub.engine.opengl.Uniform;
import org.qw3rtrun.aub.engine.opengl.VertexShader;
import org.qw3rtrun.aub.engine.vectmath.Vector3f;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class SeparatesShaderDemo {


    public static void main(String[] args) throws URISyntaxException, IOException {
        Configuration.DEBUG.set(true);
        Window window = new Window(800, 600, "Window 1");
        window.init();
        window.show();

        GL.createCapabilities();

        VertexShader shader = new VertexShader(new String(Files.readAllBytes(
                Paths.get(SeparatesShaderDemo.class.getResource("matrix.vert").toURI()))));

        Uniform<Vector3f> offset = Uniform.getUniform2f(shader, "offset");
        offset.valueProperty().setValue(Vector3f.vect3f(1, 2, 0));

        //Pipeline pl = new Pipeline(shader, );

        while (!window.isClosing()) {
            glfwPollEvents();
        }
    }
}
