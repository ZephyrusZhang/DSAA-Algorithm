package dsaa.Lab2;

import java.util.Scanner;

public class TowerOfHanoi {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        hanoi(n, 1, 3, 2);
    }

    public static void hanoi(int num, int fromRod, int targetRod, int auxRod) {
        if (num == 1) {
            System.out.println(fromRod + "->" + targetRod);
            return;
        }
        hanoi(num - 1, fromRod, auxRod, targetRod);
        System.out.println(fromRod + "->" + targetRod);
        hanoi(num - 1, auxRod, targetRod, fromRod);
    }
}
