package tetravex;


import tetravex.consoleui.ConsoleUI;
import tetravex.core.Color;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;
import tetravex.core.tile.Tile;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Tile t1 = new Tile(Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW);
        Tile t2 = new Tile(Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW);

        System.out.println(t1.equals(t2));

//        Field field = new Field(2, 2, Complexity.HARD);
//        Game game = new Game(field);
//        ConsoleUI consoleUI = new ConsoleUI(game);
//
//        consoleUI.start();

    }


}