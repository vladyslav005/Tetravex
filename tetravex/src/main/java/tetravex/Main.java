package tetravex;


import tetravex.consoleui.ConsoleUI;
import tetravex.consoleui.DrawingUtils;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Field field = new Field(5, 5, Complexity.HARD);
        Game game = new Game(field);
        ConsoleUI consoleUI = new ConsoleUI(game);

        consoleUI.mainloop();

    }


}