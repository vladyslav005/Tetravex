package tetravex.core;

import tetravex.core.tile.Tile;
import tetravex.core.tile.TileState;

import java.util.List;

public class Field {
    private final int width;
    private final int height;
    private final Complexity complexity;
    private List<List<Tile>> solved;
    private List<List<Tile>> shuffled;
    private List<List<Tile>> played;

    public Field(int width, int height, Complexity complexity) {
        this.width = width;
        this.height = height;
        this.complexity = complexity;

        initBoards(width, height);
        generate();
        shuffle();
    }

    public static Integer[] findTilePosition(Tile tile, List<List<Tile>> board) {
        Integer x = null, y = null;

        for (int i = 0; i < board.size(); i++) {
            if (!board.get(i).contains(tile)) continue;

            for (int j = 0; j < board.get(0).size(); j++) {
                if (board.get(i).get(j) == tile) {
                    x = j;
                    y = i;
                }
            }
        }

        return new Integer[]{x, y};
    }

    public static Tile findTile(List<List<Tile>> board, int x, int y) {
        return board.get(y).get(x);
    }

    public void placeTile(Tile tile, List<List<Tile>> destinationBoard, int x, int y) {
        List<List<Tile>> source = played;

        Integer[] pos = findTilePosition(tile, played);
        if (pos[0] == null) {
            pos = findTilePosition(tile, shuffled);
            source = shuffled;
        }

        if (destinationBoard.get(y).get(x) != null) return;

        destinationBoard.get(y).set(x, tile);
        source.get(pos[1]).set(pos[0], null);
        if (destinationBoard == shuffled) destinationBoard.get(y).set(x, tile).setState(TileState.UNTOUCHED);
        else updateTileState(x, y);
    }

    void updateTileState(int x, int y) {
        if (solved.get(y).get(x).equals(played.get(y).get(x))) {
            played.get(y).get(x).setState(TileState.CORRECT);
        } else {
            played.get(y).get(x).setState(TileState.WRONG);
        }
    }

    private void shuffle() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = solved.get(i).get(j);
                int x = Utils.getRandInt(width - 1), y = Utils.getRandInt(height - 1);

                while (shuffled.get(y).get(x) != null) {
                    int decide = Utils.getRandInt(1);
                    if (decide == 1) x = Utils.getRandInt(width - 1);
                    else y = Utils.getRandInt(height - 1);
                }

                shuffled.get(y).set(x, tile);
            }
        }
    }

    private void generate() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color N, S, W, E;
                N = generateColor(i, j, 0);
                S = generateColor(i, j, 1);
                W = generateColor(i, j, 2);
                E = generateColor(i, j, 3);
                solved.get(i).set(j, new Tile(N, S, W, E));
            }
        }
    }

    private Color generateColor(int tileY, int tileX, int colorIdx) {
        if (colorIdx < 0 || colorIdx > 3) return null;

        int neighborX = tileX, neighborY = tileY;

        switch (colorIdx) {
            case 0 -> neighborY = tileY - 1;
            case 1 -> neighborY = tileY + 1;
            case 2 -> neighborX = tileX - 1;
            case 3 -> neighborX = tileX + 1;
        }

        if (neighborX < 0 || neighborX >= width || neighborY < 0 || neighborY >= height
                || solved.get(neighborY).get(neighborX) == null)
            return Color.values()[Utils.getRandInt(complexity.getNumberOfColors())];

        return solved.get(neighborY).get(neighborX).getOpositeSideColor(colorIdx);
    }

    private void initBoards(int width, int height) {
        solved = Utils.initTwoDimensionalArray(width, height);
        shuffled = Utils.initTwoDimensionalArray(width, height);
        played = Utils.initTwoDimensionalArray(width, height);
    }

    public List<List<Tile>> getSolved() {
        return solved;
    }

    public List<List<Tile>> getShuffled() {
        return shuffled;
    }

    public List<List<Tile>> getPlayed() {
        return played;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
