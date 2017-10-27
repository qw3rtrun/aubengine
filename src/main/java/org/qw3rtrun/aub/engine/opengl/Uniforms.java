package org.qw3rtrun.aub.engine.opengl;

import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Uniforms {

    private final Map<String, Uniform> uniforms = new HashMap<>();

    void add(Uniform uniform) {
        uniforms.put(uniform.getName(), uniform);
    }

    public Uniform<?> get(String name) {
        return null;
    }

    public Uniform<Matrix4f> getMat4(String name) {
        return Optional.ofNullable(uniforms.get(name))
                .filter(UniformMat4.class::isInstance)
                .map(UniformMat4.class::cast)
                .orElse(null);
    }
}
