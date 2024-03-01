package tetravex.consoleui;

import tetravex.core.Color;

public class DrawingUtils {

    public static void setColor(Color color) {
        setTextColor(color);
        setBackColor(color);
    }

    public static void setBackColor(Color color) {
        System.out.print(color.getBackgroundColorCode());
    }

    public static void setTextColor(Color color) {
        System.out.print(color.getSymbolColorCode());
    }


    public static void resetColor() {
        System.out.print("\u001B[0m");
    }


    public static void setCursorPos(int row, int column) {
        System.out.printf("\u001B[%d;%dH", row, column);
        System.out.flush();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printMessage(String message) {
        DrawingUtils.deleteMessage();
        DrawingUtils.setCursorPos(49, 0);
        System.out.print(message);
        setCursorPos(1, 1);
    }

    public static void printMessage(String message, int y, int x) {
        DrawingUtils.deleteMessage(y, x);
        DrawingUtils.setCursorPos(y, x);
        System.out.print(message);
        setCursorPos(1, 1);
    }


    public static void deleteMessage(int y, int x) {
        DrawingUtils.setCursorPos(y, x);
        System.out.print("                                            ");
    }
    public static void deleteMessage() {
        DrawingUtils.setCursorPos(49, 0);
        System.out.print("                                            ");
    }
}