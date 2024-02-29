package tetravex.consoleui;

import tetravex.core.Color;
import tetravex.core.Tile;

import java.util.List;

public class ConsoleUI {

    //TODO : render, grid

    public static void drawBoard(List<List<Tile>> board, int x, int y) {
        int height = board.size(), width = board.get(0).size();
        int start_x = x + 1, start_y = y + 2;

        ConsoleUI.setCursorPos(start_y, start_x);

        drawGrid(width, height, start_x, start_y);

        for (int i = 0; i < height; i++) {
            ConsoleUI.setCursorPos(start_y + i * 4 + 1, start_x);
            drawLayer(board, 1, i);

            ConsoleUI.setCursorPos(start_y + i * 4 + 2, start_x);
            drawLayer(board, 2, i);

            ConsoleUI.setCursorPos(start_y + i * 4 + 3, start_x);
            drawLayer(board, 3, i);
        }

    }


    private static void drawGrid(int width, int height, int x, int y) {


        for (int i = 0; i < height + 1; i++) {
            ConsoleUI.setCursorPos(y + i * 4, x-1);

            for (int j = 0; j < width + 1; j++) {
                System.out.print("+-----");
            }

            for (int k = 1; k < 4; k++) {
                ConsoleUI.setCursorPos(y + i * 4 + k, x-1);
                for (int j = 0; j < width + 1; j++) {
                    if (k == 2 && j == width && i != height)
                        System.out.print("|  " + i);
                    else if (k == 2 && i == height && j != width)
                        System.out.print("|  " + j + "  ");

                    else
                        System.out.print("|     ");
                }
            }
        }

    }

    private static void drawLayer(List<List<Tile>> board, int layer, int row) {
        int width = board.get(0).size();

        for (int k = 0; k < width; k++) {
            Tile t = board.get(row).get(k);
            if (layer == 1) System.out.print(ConsoleUI.tileFirstLayer(t));
            else if (layer == 2) System.out.print(ConsoleUI.tileSecondLayer(t));
            else if (layer == 3) System.out.print(ConsoleUI.tileThirdLayer(t));

            System.out.print("\u001b[1C");
        }
    }


    public static String tileFirstLayer(Tile tile) {
        return
                getSpace() +
                        tile.getN().getColoredBackground() +
                        tile.getN().getColoredBackground() +
                        tile.getN().getColoredBackground() +
                        getSpace();

    }

    public static String tileSecondLayer(Tile tile) {
        return
                tile.getW().getColoredBackground() +
                        getSpace() + getSpace() + getSpace() +
                        tile.getE().getColoredBackground();
    }

    public static String tileThirdLayer(Tile tile) {
        return
                getSpace() +
                        tile.getS().getColoredBackground() +
                        tile.getS().getColoredBackground() +
                        tile.getS().getColoredBackground() +
                        getSpace();
    }


    public static String getSpace() {
        return Color.WHITE.getBackgroundColorCode() + " " + "\u001B[0m";
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
