package org.qw3rtrun.aub.engine.vectmath;

public class MathUtils {

    private MathUtils() {
    }

    public static float max(float... a) {
        switch (a.length) {
            case 0:
                return Float.NaN;
            case 1:
                return a[0];
            case 2:
                return Math.max(a[0], a[1]);
            case 3:
                return Math.max(a[0], Math.max(a[1], a[2]));
            case 4:
                return Math.max(Math.max(a[0], a[1]), Math.max(a[2], a[3]));
            default: {
                float m = a[0];
                for (int i = 1; i < a.length; i++) {
                    if (a[i] > m) m = a[i];
                }
                return m;
            }
        }
    }
}
