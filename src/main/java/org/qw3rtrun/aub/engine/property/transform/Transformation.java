package org.qw3rtrun.aub.engine.property.transform;

import org.qw3rtrun.aub.engine.property.matrix.Matrix4fBinding;
import org.qw3rtrun.aub.engine.property.vector.ObservableVector;
import org.qw3rtrun.aub.engine.property.vector.Vector4fBinding;

public interface Transformation {

    Vector4fBinding apply(ObservableVector source);

    Matrix4fBinding asMatrix();

    Transformation invert();

}
