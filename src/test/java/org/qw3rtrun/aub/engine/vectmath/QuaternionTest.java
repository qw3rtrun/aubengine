package org.qw3rtrun.aub.engine.vectmath;

import org.junit.Test;

public class QuaternionTest {

    @Test
    public void test() {
        Quaternion q = Quaternion.quaternion(1, 0, 0, (float) Math.PI);
        Quaternion p = q.conjugate();
        Quaternion n = q.normalize();
        System.out.println(q);
        System.out.println(p);
        System.out.println(q.norm());
        System.out.println(n);
        System.out.println(n.product(Quaternion.quaternion(0, 1, 0, 0).product(n.conjugate())));

        q = Quaternion.quaternion((float) Math.sin(Math.PI / 4), 0, 0, (float) Math.cos(Math.PI / 4)).normalize();
        System.out.println(q.norm());
        System.out.println(q.product(Quaternion.quaternion(0, 1, 0, 0).product(q.conjugate())));
        System.out.println(q.product(Quaternion.quaternion(1, 0, 0, 0).product(q.conjugate())));
        System.out.println(q.product(Quaternion.quaternion(0, 0, 1, 0).product(q.conjugate())));

        Quaternion dq = q.product(q).normalize();
        System.out.println(dq.norm());
        System.out.println(dq.product(Quaternion.quaternion(0, 1, 0, 0).product(dq.conjugate())));
        System.out.println(dq.product(Quaternion.quaternion(1, 0, 0, 0).product(dq.conjugate())));
        System.out.println(dq.product(Quaternion.quaternion(0, 0, 1, 0).product(dq.conjugate())));

    }
}
