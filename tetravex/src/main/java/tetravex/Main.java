package tetravex;

import tetravex.consoleui.ConsoleUI;
import tetravex.core.Complexity;
import tetravex.core.Field;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Field field = new Field(5, 5, Complexity.MEDIUM);

        ConsoleUI.drawBoard(field.getSolved(), 5, 3);
        Thread.sleep(5000);
    }
}