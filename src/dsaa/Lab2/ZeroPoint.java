package dsaa.Lab2;

import java.util.Scanner;

import static java.lang.Math.*;

public class ZeroPoint {
    public static double A;
    public static double B;
    public static double C;
    public static double lowerBound = -10;
    public static double upperBound = 10;
    public static double e = 0.00001;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        A = in.nextDouble();
        B = in.nextDouble();
        C = in.nextDouble();

        double y0 = f(lowerBound);
        double x = 0, y;
        while ((upperBound - lowerBound) >= e) {
            x = (lowerBound + upperBound) / 2;
            y = f(x);
            if (y0 * y > 0) {
                lowerBound = x;
            } else {
                upperBound = x;
            }
        }
        System.out.printf("%.4f\n", x);
    }

    public static double f(double x) {
        return (A * x * x + B * x + C) * exp(x) - pow(PI, 1.14514);
    }
}
