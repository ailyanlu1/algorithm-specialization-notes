/**
 * Created by Tankc on 2017/12/11.
 */

import java.math.BigInteger;

public class Karatsuba {
    public static BigInteger karatsuba(BigInteger x, BigInteger y) {
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 2000) return x.multiply(y);
        N = (N / 2) + (N % 2);

        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        BigInteger ac    = karatsuba(a, c);
        BigInteger bd    = karatsuba(b, d);
        BigInteger abcd  = karatsuba(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
    }


    public static void main(String[] args) {
        BigInteger a = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
        BigInteger b = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");
        BigInteger c = karatsuba(a, b);
        System.out.println(c.toString());
    }
}
