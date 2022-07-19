package dsaa.Lab2;

import java.util.Scanner;

public class CrazyFunction {
    public static long[][][][] memory = new long[31][31][31][31];

    static {
        memory[1][1][1][1] = 1;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int A = in.nextInt();
        int B = in.nextInt();
        int C = in.nextInt();
        int D = in.nextInt();
        System.out.println(W(A, B, C, D));
    }

    public static long W(int A, int B, int C, int D) {
        if (A == 1 && B == 1 && C == 1 && D == 1) {
            return 1;
        }
        if (memory[A][B][C][D] == 0) {
            if (A > 1 && B > 1 && C > 1 && D > 1) {
                memory[A][B][C][D] = W(A - 1, B, C, D) + W(A, B - 1, C, D) + W(A, B, C - 1, D) + W(A, B, C, D - 1);
            } else {
                memory[A][B][C][D] = (A == 1 ? 0 : W(A - 1, B, C, D)) + (B == 1 ? 0 : W(A, B - 1, C, D)) + (C == 1 ? 0 : W(A, B, C - 1, D)) + (D == 1 ? 0 : W(A, B, C, D - 1));
            }
        }
        return memory[A][B][C][D] % 998244353L;
    }
}
