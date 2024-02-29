package tetravex;

import tetravex.consoleui.ConsoleUI;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Field field = new Field(5, 5, Complexity.HARD);
        Game game = new Game(field);
        ConsoleUI consoleUI = new ConsoleUI(game);
        consoleUI.render();

        Thread.sleep(5000);
    }
}