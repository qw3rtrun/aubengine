package org.qw3rtrun.aub.engine.vectmath;

import com.sun.javafx.binding.DoubleConstant;
import org.junit.Test;
import org.qw3rtrun.aub.engine.property.Bindings;

import static org.qw3rtrun.aub.engine.property.vector.Vector4fConstant.CONST_X;
import static org.qw3rtrun.aub.engine.vectmath.Vector4f.*;

public class QuaternionTest {

    @Test
    public void test() {
        Quaternion q = Quaternion.quaternion((float) Math.sin(Math.PI / 4), 0, 0, (float) Math.cos(Math.PI / 4)).normalize();
        System.out.println(q.norm());
        System.out.println(q);
        System.out.println(q.product(Quaternion.quaternion(0, 1, 0, 0).product(q.conjugate())));
        System.out.println(q.product(Quaternion.quaternion(1, 0, 0, 0).product(q.conjugate())));
        System.out.println(q.product(Quaternion.quaternion(0, 0, 1, 0).product(q.conjugate())));

        Quaternion dq = q.product(q).normalize();
        System.out.println(dq.norm());
        System.out.println(dq.product(Quaternion.quaternion(0, 1, 0, 0).product(dq.conjugate())));
        System.out.println(dq.product(Quaternion.quaternion(1, 0, 0, 0).product(dq.conjugate())));
        System.out.println(dq.product(Quaternion.quaternion(0, 0, 1, 0).product(dq.conjugate())));
        System.out.println(dq);
        Quaternion p2 = Quaternion.quaternion((float) Math.sin(Math.PI / 2), 0, 0, (float) Math.cos(Math.PI / 2));
        System.out.println(p2);
        System.out.println(p2.normalize());
        System.out.println(p2.product(Quaternion.quaternion(0, 1, 0, 0).product(p2.conjugate())));
        System.out.println(p2.product(Quaternion.quaternion(1, 0, 0, 0).product(p2.conjugate())));
        System.out.println(p2.product(Quaternion.quaternion(0, 0, 1, 0).product(p2.conjugate())));


        dq = Quaternion.quaternion((float) Math.sin(Math.PI / 4), 0, 0, (float) Math.cos(Math.PI / 4)).normalize();
        float qr = dq.a;
        float qi = dq.x;
        float qj = dq.y;
        float qk = dq.z;

        Matrix4f m = Matrix4f.matr(
                1 - 2 * qj * qj - 2 * qk * qk, 2 * (qi * qj - qk * qr), 2 * (qi * qk + qj * qr),
                2 * (qi * qj + qk * qr), 1 - 2 * qi * qi - 2 * qk * qk, 2 * (qj * qk - qi * qr),
                2 * (qi * qk - qj * qr), 2 * (qj * qk + qi * qr), 1 - 2 * qi * qi - 2 * qj * qj,
                1);
        System.out.print(m.multiply(X));
        System.out.print(m.multiply(Y));
        System.out.print(m.multiply(Z));
    }

    @Test
    public void test2() {
        Quaternion nq = Bindings.axisRotation(CONST_X, DoubleConstant.valueOf(Math.PI / 2)).get().normalize();
        Quaternion dnq = nq.product(nq);

        System.out.println(nq.product(Quaternion.QX0).product(nq.conjugate()));
        System.out.println(nq.product(Quaternion.QY0).product(nq.conjugate()));
        System.out.println(nq.product(Quaternion.QZ0).product(nq.conjugate()));

        System.out.println(dnq.product(Quaternion.QX0).product(dnq.conjugate()));
        System.out.println(dnq.product(Quaternion.QY0).product(dnq.conjugate()));
        System.out.println(dnq.product(Quaternion.QZ0).product(dnq.conjugate()));
    }
}

