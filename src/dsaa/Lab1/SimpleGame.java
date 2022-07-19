package dsaa.Lab1;

import java.util.Scanner;

public class SimpleGame {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        int[] turns = new int[t];
        for (int i = 0; i < t; ++i) {
            turns[i] = input.nextInt();
        }

        for (int i = 0; i < t; ++i) {
            if (turns[i] % 6 == 0) {
                System.out.println("Bob");
            } else {
                System.out.println("Alice");
            }
        }
    }
}
