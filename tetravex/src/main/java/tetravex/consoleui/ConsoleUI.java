package tetravex.consoleui;

import tetravex.core.Color;
import tetravex.core.Field;
import tetravex.core.Game;
import tetravex.core.Tile;

import java.io.IOException;
import java.util.List;

public class ConsoleUI {

    private final Cursor cursor;
    private final Game game;


    //TODO : render


    public ConsoleUI(Game game) {
        this.game = game;
        this.cursor = new Cursor(game, null, game.getField().getPlayed());
        mainloop();
    }

    public void mainloop() {
        DrawingUtils.clearScreen();

        enableRawMode();
        while (true) {
            render();
            int key = getInputChar();
            if (key == -1 || key == 'q') {
                DrawingUtils.clearScreen();
                break;
            }

            inputHandler(key);

        }

        disableRawMode();
    }


    private void inputHandler(int key) {
        Key asciKey = Key.getASCIIKeyCode(key);
        if (asciKey == null) return;

        switch (asciKey) {
            case LOWERCASE_W -> {cursor.moveUp();}
            case LOWERCASE_S -> {cursor.moveDown();}
            case LOWERCASE_D -> {cursor.moveRight();}
            case LOWERCASE_A -> {cursor.moveLeft();}
            case CARRIAGE_RETURN -> {cursor.pickOrDropTile();}
        }
    }

    private int getInputChar() {
        DrawingUtils.setCursorPos(0, 0);

        int key = 0;
        try {
            key = System.in.read();
        } catch (IOException e) {throw new RuntimeException(e);}

        DrawingUtils.setCursorPos(0, 0);
        System.out.print("     ");

        return key;
    }

    public void render() {
        int x = 2, y = 2;
        Field field = game.getField();
        int boardCharWidth = (field.getWidth() + 1) * 5 + 7;

        drawBoard(field.getPlayed(), x, y);
        drawBoard(field.getShuffled(), x + boardCharWidth, y);
        drawBoard(field.getSolved(), x + boardCharWidth * 2, y);
    }

    private void disableRawMode() {
        String[] cmd2 = {"/bin/sh", "-c", "stty cooked </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd2).waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void enableRawMode() {
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void drawBoard(List<List<Tile>> board, int x, int y) {
        int height = board.size(), width = board.get(0).size();
        int start_x = x + 1, start_y = y + 2;

        DrawingUtils.setCursorPos(start_y, start_x);
        drawGrid(width, height, start_x, start_y);

        for (int i = 0; i < height; i++) {
            DrawingUtils.setCursorPos(start_y + i * 4 + 1, start_x);
            drawLayer(board, 1, i);

            DrawingUtils.setCursorPos(start_y + i * 4 + 2, start_x);
            drawLayer(board, 2, i);

            DrawingUtils.setCursorPos(start_y + i * 4 + 3, start_x);
            drawLayer(board, 3, i);
        }

        DrawingUtils.setCursorPos(0, 0);
    }

    private  void drawGrid(int width, int height, int x, int y) {
        for (int i = 0; i < height + 1; i++) {
            DrawingUtils.setCursorPos(y + i * 4, x-1);

            for (int j = 0; j < width; j++) {
                DrawingUtils.setColor(Color.GRAY);

                if (j != width-1) System.out.print("+-----");
                else System.out.print("+-----+");

                DrawingUtils.resetColor();
            }

            if (height == i ) break;
            for (int k = 1; k < 4; k++) { // k - height of one tile (3)
                DrawingUtils.setCursorPos(y + i * 4 + k, x-1);
                for (int j = 0; j < width ; j++) {
                    DrawingUtils.setColor(Color.GRAY);
                    System.out.print("|");
                    DrawingUtils.resetColor();

                    if (j == width-1) {
                        DrawingUtils.setColor(Color.GRAY);
                        System.out.print("     |");
                        DrawingUtils.resetColor();
                    } else
                        System.out.print("     ");
                }
            }
        }

        DrawingUtils.resetColor();
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


    public String emptyTileLayer(List<List<Tile>> board,int y, int x) {
        return getSpace(board, y, x) +
                getSpace(board, y, x) +
                getSpace(board, y, x) +
                getSpace(board, y, x) +
                getSpace(board, y, x);
    }

    public String tileFirstLayer(List<List<Tile>> board,int y, int x) {
        Tile tile = board.get(y).get(x);
        if (tile == null) return emptyTileLayer(board, y, x);

        return
                getSpace(board, y, x) +
                    tile.getN().getColoredBackground() +
                    tile.getN().getColoredBackground() +
                    tile.getN().getColoredBackground() +
                    getSpace(board, y, x);
    }

    public String tileSecondLayer(List<List<Tile>> board,int y, int x) {
        Tile tile = board.get(y).get(x);
        if (tile == null) return emptyTileLayer(board, y, x);
        return
                tile.getW().getColoredBackground() +
                    getSpace(board, y, x) +
                    getSpace(board, y, x) +
                    getSpace(board, y, x) +
                tile.getE().getColoredBackground();
    }

    public String tileThirdLayer(List<List<Tile>> board,int y, int x) {
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

        if (tile == cursor.getSelectedTile().getTile())
            return Color.WHITE.getBackgroundColorCode() + " " + "\u001B[0m";

        return Color.TILE_BACKGROUND.getBackgroundColorCode() + " " + "\u001B[0m";
    }

}
