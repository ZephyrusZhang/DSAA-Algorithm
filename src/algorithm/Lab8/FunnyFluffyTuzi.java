package algorithm.Lab8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Math.*;

public class FunnyFluffyTuzi {
    private static final QReader in = new QReader();

    public static void main(String[] args) {
        final int exp = in.nextInt();
        int n = 1 << exp;
        double[] array = new double[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextDouble();
        }
        Complex[] y = fft(array, n);
        for (Complex complex : y) {
            System.out.printf("%.10f\n", complex.mod());
        }
    }

    public static double[] bit_reverse(double[] array, int length) {
        double[] res = Arrays.copyOf(array, length);
        long offset = (long) (log(length) / log(2)) - 1L;
        int[] rev = new int[length];
        for (int i = 0; i < length; i++) {
            rev[i] = (rev[i >> 1] >> 1);
            if ((i & 1) == 1) {
                rev[i] |= 1 << offset;
            }
        }

        double temp;
        for (int i = 0; i < length; i++) {
            if (rev[i] < i) {
                temp = res[i];
                res[i] = res[rev[i]];
                res[rev[i]] = temp;
            }
        }
        return res;
    }

    public static Complex[] fft(double[] array, int length) {
        double[] rev = bit_reverse(array, length);
        Complex[] y = new Complex[length];
        for (int i = 0; i < y.length; i++) {
            y[i] = new Complex(rev[i], 0.0);
        }
        int level = (int) (log(length) / log(2));
        int n = 2;
        Complex w0 = new Complex(0.0, 0.0);
        Complex wk = new Complex(0.0, 0.0);
        Complex temp = new Complex(0.0, 0.0);
        Complex yk = new Complex(0.0, 0.0);
        for (int i = 0; i < level; i++, n <<= 1) {
            w0.real = cos(2 * PI / n);
            w0.imag = sin(2 * PI / n);
            for (int j = 0; j < length; j += n) {
                wk.real = 1.0;
                wk.imag = 0.0;
                for (int k = j; k < j + n / 2; k++) {
                    yk.real = y[k].real;
                    yk.imag = y[k].imag;
                    Complex.mul(wk, y[k + (n >> 1)], temp);

                    Complex.add(yk, temp, y[k]);
                    Complex.sub(yk, temp, y[k + (n >> 1)]);

                    Complex.mul(wk, w0, wk);
                }
            }
        }
        return y;
    }

    static class Complex {
        double real;
        double imag;

        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public static void add(Complex c1, Complex c2, Complex res) {
            res.real = c1.real + c2.real;
            res.imag = c1.imag + c2.imag;
        }

        public static void sub(Complex c1, Complex c2, Complex res) {
            res.real = c1.real - c2.real;
            res.imag = c1.imag - c2.imag;
        }

        public static double tempReal;
        public static double tempImag;

        public static void mul(Complex c1, Complex c2, Complex res) {
            tempReal = c1.real * c2.real - c1.imag * c2.imag;
            tempImag = c1.imag * c2.real + c1.real * c2.imag;
            res.real = tempReal;
            res.imag = tempImag;
        }

        public Complex add(Complex o) {
            return new Complex(real + o.real, imag + o.imag);
        }

        public double mod() {
            return sqrt(pow(real, 2.0) + pow(imag, 2.0));
        }
    }

    static class QReader {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public void hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

    }

}
