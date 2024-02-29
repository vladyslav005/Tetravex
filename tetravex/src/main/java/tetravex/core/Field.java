package tetravex.core;

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

    private Color generateColor(int tileX, int tileY, int colorIdx) {
        return null;
    }


    private void initBoards(int width, int height) {
        solved = Utils.initTwoDimensionalArray(width, height);
        shuffled = Utils.initTwoDimensionalArray(width, height);
        played = Utils.initTwoDimensionalArray(width, height);
    }

    //TODO : shuffle

    public List<List<Tile>> getSolved() {
        return solved;
    }

    public List<List<Tile>> getShuffled() {
        return shuffled;
    }

    public List<List<Tile>> getPlayed() {
        return played;
    }

}
