package tetravex;


import tetravex.consoleui.ConsoleUI;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Field field = new Field(2, 2, Complexity.HARD);
        Game game = new Game(field);
        ConsoleUI consoleUI = new ConsoleUI(game);

        consoleUI.start();

    }


}