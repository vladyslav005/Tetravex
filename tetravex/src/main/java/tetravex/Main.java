package tetravex;

import tetravex.consoleui.ConsoleUI;
import tetravex.core.Colors;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Tile;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Field field = new Field(5, 5, Complexity.MEDIUM);

        ConsoleUI.drawBoard(field.getSolved(), 5, 5);
    }
}