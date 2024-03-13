package tetravex.consoleui;

import tetravex.core.Color;
import tetravex.core.tile.Tile;
import tetravex.core.tile.TileState;

import java.util.List;

public class BoardDrawer {

    private final Cursor cursor;
    private final ConsoleUI consoleUI;

    public BoardDrawer(ConsoleUI consoleUI) {
        this.cursor = consoleUI.getCursor();
        this.consoleUI = consoleUI;
    }


    public void drawBoard(List<List<Tile>> board, int x, int y) {
        int height = board.size(), width = board.get(0).size();
        int start_x = x + 1, start_y = y + 2;

        ConsoleUtils.setCursorPos(start_y, start_x);
        drawGrid(width, height, start_x, start_y);

        for (int i = 0; i < height; i++) {
            ConsoleUtils.setCursorPos(start_y + i * 4 + 1, start_x);
            drawLayer(board, 1, i);

            ConsoleUtils.setCursorPos(start_y + i * 4 + 2, start_x);
            drawLayer(board, 2, i);

            ConsoleUtils.setCursorPos(start_y + i * 4 + 3, start_x);
            drawLayer(board, 3, i);
        }

        ConsoleUtils.setCursorPos(0, 0);
    }

    private void drawGrid(int width, int height, int x, int y) {
        for (int i = 0; i < height + 1; i++) {
            ConsoleUtils.setCursorPos(y + i * 4, x - 1);

            for (int j = 0; j < width; j++) {
                ConsoleUtils.setColor(Color.GRID_COLOR);

                if (j != width - 1) System.out.print("+-----");
                else System.out.print("+-----+");

                ConsoleUtils.resetColor();
            }

            if (height == i) break;
            for (int k = 1; k < 4; k++) { // k - height of one tile (3)
                ConsoleUtils.setCursorPos(y + i * 4 + k, x - 1);
                for (int j = 0; j < width; j++) {
                    ConsoleUtils.setColor(Color.GRID_COLOR);
                    System.out.print("|");
                    ConsoleUtils.resetColor();

                    if (j == width - 1) {
                        ConsoleUtils.setColor(Color.GRID_COLOR);
                        System.out.print("     |");
                        ConsoleUtils.resetColor();
                    } else
                        System.out.print("     ");
                }
            }
        }

        ConsoleUtils.resetColor();
    }

    private void drawLayer(List<List<Tile>> board, int layer, int row) {
        int width = board.get(0).size();

        for (int k = 0; k < width; k++) {
            if (layer == 1) System.out.print(tileFirstLayer(board, row, k));
            else if (layer == 2) System.out.print(tileSecondLayer(board, row, k));
            else if (layer == 3) System.out.print(tileThirdLayer(board, row, k));

            System.out.print("\u001b[1C");
        }
    }

    public String emptyTileLayer(List<List<Tile>> board, int y, int x) {
        return getSpace(board, y, x) +
                getSpace(board, y, x) +
                getSpace(board, y, x) +
                getSpace(board, y, x) +
                getSpace(board, y, x);
    }

    public String tileFirstLayer(List<List<Tile>> board, int y, int x) {
        Tile tile = board.get(y).get(x);
        if (tile == null) return emptyTileLayer(board, y, x);

        return
                getSpace(board, y, x) +
                        tile.getN().getColoredBackground() +
                        tile.getN().getColoredBackground() +
                        tile.getN().getColoredBackground() +
                        getSpace(board, y, x);
    }

    public String tileSecondLayer(List<List<Tile>> board, int y, int x) {
        Tile tile = board.get(y).get(x);
        if (tile == null) return emptyTileLayer(board, y, x);
        return
                tile.getW().getColoredBackground() +
                        getSpace(board, y, x) +
                        getSpace(board, y, x) +
                        getSpace(board, y, x) +
                        tile.getE().getColoredBackground();
    }

    public String tileThirdLayer(List<List<Tile>> board, int y, int x) {
        Tile tile = board.get(y).get(x);

        if (tile == null) return emptyTileLayer(board, y, x);
        return
                getSpace(board, y, x) +
                        tile.getS().getColoredBackground() +
                        tile.getS().getColoredBackground() +
                        tile.getS().getColoredBackground() +
                        getSpace(board, y, x);
    }

    public String getSpace(List<List<Tile>> board, int y, int x) {
        Tile tile = board.get(y).get(x);

        if (tile == null) {
            if (board == cursor.getSelectedTile().getBoard() && y == cursor.getSelectedTile().getY()
                    && x == cursor.getSelectedTile().getX())
                return Color.WHITE.getBackgroundColorCode() + " " + "\u001B[0m";
            else return " ";
        }

        if (cursor.getPickedTile().getTile() == tile)
            return Color.BLACK.getBackgroundColorCode() + " " + "\u001B[0m";
        else if (tile == cursor.getSelectedTile().getTile())
            return Color.WHITE.getBackgroundColorCode() + " " + "\u001B[0m";

        return
                consoleUI.isHintsOn() && tile.getState() == TileState.CORRECT ?
                        Color.HIGHLOGHT_COLOR.getBackgroundColorCode() + " " + "\u001B[0m" :
                        Color.TILE_BACKGROUND.getBackgroundColorCode() + " " + "\u001B[0m";
    }
}
