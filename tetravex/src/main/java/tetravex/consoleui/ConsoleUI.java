package tetravex.consoleui;

import tetravex.core.Colors;
import tetravex.core.Tile;

import java.util.List;

public class ConsoleUI {

    public static String reset = "\u001B[0m";


    public static void drawBoard(List<List<Tile>> board, int x, int y) {
        int height = board.size(), width = board.get(0).size();
        int start_x = x+1, start_y = y+1;

        for (int i = 0; i < height; i++) {
            ConsoleUI.setCursorPos(start_y + i * 4 + 1, start_x);
            drawLayer(board, 1, i);

            ConsoleUI.setCursorPos(start_y + i * 4 + 2, start_x);
            drawLayer(board, 2, i);

            ConsoleUI.setCursorPos(start_y + i * 4 + 3, start_x);
            drawLayer(board, 3, i);

            System.out.println("\n");
        }
    }

    private static void drawLayer(List<List<Tile>> board, int layer, int row) {
        int height = board.size(), width = board.get(0).size();

        for (int k = 0; k < width; k++) {
            Tile t = board.get(row).get(k);

            if (layer == 1) System.out.print(ConsoleUI.tileFirstLayer(t));
            else if (layer == 2) System.out.print(ConsoleUI.tileSecondLayer(t));
            else System.out.print(ConsoleUI.tileThirdLayer(t));

            System.out.print(" ");
        }
    }


    public static String tileFirstLayer(Tile tile) {
        return
            getSpace() +
                    tile.getN().getColoredBackground()+
                    tile.getN().getColoredBackground() +
                    tile.getN().getColoredBackground() +
            getSpace() ;

    }

    public static String tileSecondLayer(Tile tile) {
        return
            tile.getW().getColoredBackground()  +
                    getSpace() + getSpace() + getSpace() +
            tile.getE().getColoredBackground();
    }

    public static String tileThirdLayer(Tile tile) {
        return
            getSpace() +
                    tile.getS().getColoredBackground() +
                    tile.getS().getColoredBackground() +
                    tile.getS().getColoredBackground() +
            getSpace() ;
    }


    public static String getSpace() {
        return Colors.WHITE.getBackgroundColorCode() + " " + reset;
    }

    public static void setCursorPos(int row, int column) {
        char escCode = 0x1B;
        System.out.print(String.format("\u001B[%d;%dH",row,column));
        System.out.flush();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
