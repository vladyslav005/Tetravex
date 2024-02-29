package tetravex.consoleui;

import tetravex.core.Color;
import tetravex.core.Tile;

import java.util.List;

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
}
