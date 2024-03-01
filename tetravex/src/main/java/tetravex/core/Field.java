package tetravex.core;

import tetravex.core.tile.Tile;
import tetravex.core.tile.TileState;

import java.util.List;

public class Field {

    private final int width;
    private final int height;
    private List<List<Tile>> solved;
    private List<List<Tile>> shuffled;
    private List<List<Tile>> played;

    public Field(int width, int height, Complexity complexity) {
        this.width = width;
        this.height = height;
        initBoards(width, height);
        generate(complexity);
        shuffle();

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
        if (destinationBoard == shuffled) destinationBoard.get(y).set(x, tile).setState(TileState.UNPOSSITIONED);
        else updateState(x, y);
    }

    void updateState(int x, int y) {
        if (solved.get(y).get(x) == played.get(y).get(x)) {
            played.get(y).get(x).setState(TileState.CORRECT);
        } else {
            played.get(y).get(x).setState(TileState.WRONG);
        }
    }

    private void shuffle() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = solved.get(i).get(j);
                int x = Utils.getRandInt(width-1), y = Utils.getRandInt(height-1);

                while (shuffled.get(y).get(x) != null) {
                    int decide = Utils.getRandInt(1);
                    if (decide == 1) x = Utils.getRandInt(width - 1);
                    else y = Utils.getRandInt(height - 1);
                }

                shuffled.get(y).set(x, tile);
            }
        }
    }
    private void generate(Complexity complexity) {
        for (int i = 0; i < solved.size(); i++) {
            for (int j = 0; j < solved.get(0).size(); j++) {
                Color N, S, W, E;

                if (i - 1 < 0 || solved.get(i - 1).get(j) == null) {
                    N = Color.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else N = solved.get(i - 1).get(j).getS();

                if (i + 1 >= height || solved.get(i + 1).get(j) == null) {
                    S = Color.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else S = solved.get(i + 1).get(j).getN();

                if (j - 1 < 0 || solved.get(i).get(j - 1) == null) {
                    W = Color.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else W = solved.get(i).get(j - 1).getE();

                if (j + 1 >= width || solved.get(i).get(j + 1) == null) {
                    E = Color.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else E = solved.get(i).get(j + 1).getW();

                solved.get(i).set(j, new Tile(N, S, W, E));
            }
        }
    }

    //TODO: eliminate repeating ^^^^^^
    private Color generateColor(int tileX, int tileY, int colorIdx) {
        return null;
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


    public static Integer[] findTilePosition(Tile tile, List<List<Tile>> board) {
        Integer x = null, y = null;

        for (int i = 0; i < board.size(); i++) {
            if (!board.get(i).contains(tile)) continue;

            for (int j = 0; j < board.get(0).size(); j++) {
                if (board.get(i).get(j) == tile) {
                    x = j; y = i;
                }
            }
        }

        return new Integer[] {x, y};
    }

    public static Tile findTile(List<List<Tile>> board, int x, int y) {
        return board.get(y).get(x);
    }
}
