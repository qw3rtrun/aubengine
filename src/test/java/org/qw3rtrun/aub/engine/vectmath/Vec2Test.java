package org.qw3rtrun.aub.engine.vectmath;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.qw3rtrun.aub.engine.Matchers.nearTo;
import static org.qw3rtrun.aub.engine.vectmath.Vec2.vec2;

/**
 * Created by strunov on 10/31/2017.
 */
public class Vec2Test {
    @Test
    public void lerp() throws Exception {
        assertThat(vec2(0, 0).lerp(1, 1, 0.5f), nearTo(vec2(0.5f, 0.5f)));
        assertThat(vec2(0, 0).lerp(1, 1, -0.5f), nearTo(vec2(-0.5f, -0.5f)));
        assertThat(vec2(1, 1).lerp(2, 2, -0.5f), nearTo(vec2(.5f, .5f)));
    }

}