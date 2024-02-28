package tetravex.core;

import tetravex.consoleui.ConsoleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Field {

    private List<List<Tile>> solved;
    private List<List<Tile>> shufled;
    private List<List<Tile>> played;

    private int width, heigth;
    public Field(int width, int heigth, Complexity complexity) {
        this.width = width;
        this.heigth = heigth;
        initBoards(width, heigth);
        generate(complexity);



    }

    private void generate(Complexity complexity) {
        Colors N, S, W, E;

        for (int i = 0; i < solved.size(); i++) {
            for (int j = 0; j < solved.get(0).size(); j++) {

                if (i - 1 < 0 || solved.get(i-1).get(j) == null) {
                    N = Colors.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else N = solved.get(i-1).get(j).getS();

                if (i + 1 >= heigth || solved.get(i+1).get(j) == null) {
                    S = Colors.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else S = solved.get(i+1).get(j).getN();

                if (j - 1 < 0 || solved.get(i).get(j-1) == null) {
                    W = Colors.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else W = solved.get(i).get(j-1).getE();

                if (j + 1 >= width || solved.get(i).get(j+1) == null) {
                    E = Colors.values()[Utils.getRandInt(complexity.getNumberOfColors())];
                } else E = solved.get(i).get(j+1).getW();

                solved.get(i).set(j, new Tile(N, S, W, E));
            }
        }
    }

    private void initBoards(int width, int height) {
        solved = Utils.initTwoDimensionalArray(width, height);
        shufled = Utils.initTwoDimensionalArray(width, height);
        played = Utils.initTwoDimensionalArray(width, height);
    }


    public List<List<Tile>> getSolved() {
        return solved;
    }

    public List<List<Tile>> getShufled() {
        return shufled;
    }

    public List<List<Tile>> getPlayed() {
        return played;
    }

}
