package budget;

import java.util.Scanner;

public class MyInput {
    private static Scanner sc = new Scanner(System.in);

    public static String readLine(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    public static int readInt(String message) {
        return Integer.parseInt(readLine(message));
    }

    public static double readDouble(String message) {
        return Double.parseDouble(readLine(message));
    }
}
