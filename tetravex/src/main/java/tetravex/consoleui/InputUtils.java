package tetravex.consoleui;

import java.io.IOException;
import java.util.Scanner;

public class InputUtils {

    public static int getInputChar() {
        ConsoleUtils.setCursorPos(0, 0);
        int key;
        try {
            key = System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConsoleUtils.setCursorPos(0, 0);
        System.out.print("  ");

        return key;
    }


    public static String getStringInput() {
        System.out.print(">>>>>> ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static int getIntInput(int min, int max) {
        Scanner sc = new Scanner(System.in);

        int choosedOption = 0;
        for (int i = 0; true; i++) {
            try {
                System.out.print(">>>>>> ");
                choosedOption = sc.nextInt();

                if (choosedOption >= min && choosedOption <= max) {
                    break;
                } else {
                    System.out.println("Wrong input (");

                }
            } catch (Exception ignored) {
                System.out.println("Wrong input (");
                sc.next();
            }
        }

        return choosedOption;
    }
}
