package org.qw3rtrun.aub.engine.scene;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.vectmath.Matrix4f;

import static org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding.binding;
import static org.qw3rtrun.aub.engine.vectmath.Matrix4f.matr;

public class Camera extends SceneObject {

    private final FloatProperty aspectRatio = new SimpleFloatProperty(this, "aspect ratio", 1f);

    private final FloatProperty zNear = new SimpleFloatProperty(this, "z near", 0.5f);

    private final FloatProperty zFar = new SimpleFloatProperty(this, "z far", 3f);

    private final FloatProperty frustumScale = new SimpleFloatProperty(this, "frustum scale", 1f);

    private final Matrix4fBinding cameraToClip = binding(
            () -> {
                final float aspect = aspectRatio.get();
                final float zn = zNear.get();
                final float zf = zFar.get();
                final float fScale = frustumScale.get();
                return matr(
                        aspect * fScale, 0,      0,                     0,
                        0,               fScale, 0,                     0,
                        0,               0,      (zf + zn) / (zn - zf), 2 * zf * zn / (zn - zf),
                        0,               0,      -1,                    1
                );
            },
            aspectRatio, zFar, zNear, frustumScale
    );

    private final Matrix4fBinding absoluteToClip = cameraToClip.concat(absoluteToLocal().asMatrix());

    public Matrix4f getAbsoluteToClip() {
        return absoluteToClip.get();
    }

    public Matrix4fBinding absoluteToClipProperty() {
        return absoluteToClip;
    }
}
