package tasks;

/**
 * Created by AUALAVE on 20/02/2017.
 */
public class BitWiseOperations {

    public static void main(String[] args) {
        BitWiseOperations ops = new BitWiseOperations();
//        System.out.println(ops.add(4, 5));
        System.out.println(ops.negate(-13));
//        System.out.println(ops.sub(4, 3));
//        System.out.println(ops.mul(4, 3));
//        System.out.println(ops.divide(13, 3));
    }


    int add(int x, int y) {
        int a, b;
        do {
            System.out.println(x);
            System.out.println(y);
            a = x & y;
            b = x ^ y;
            x = a << 1;
            y = b;
        } while (a != 0);
        return b;
    }

    int negate(int x) {
        System.out.println(Integer.toBinaryString(x));
        return add(~x, 1);
    }

    int sub(int x, int y) {
        return add(x, negate(y));
    }

    int mul(int x, int y) {
        int m = 1, z = 0;
        if (x < 0) {
            x = negate(x);
            y = negate(y);
        }

        while (x >= m && y != 0) {
            if ((x & m) != 0) z = add(y, z);
            y <<= 1;
            m <<= 1;
        }
        return z;
    }

    int divide(int x, int y) {
        int c = 0, sign = 0;

        if (x < 0) {
            x = negate(x);
            sign ^= 1;
        }

        if (y < 0) {
            y = negate(y);
            sign ^= 1;
        }

        if (y != 0) {
            while (x >= y) {
                x = sub(x, y);
                ++c;
            }
        }
        if (sign != 0) {
            c = negate(c);
        }
        return c;
    }

}
