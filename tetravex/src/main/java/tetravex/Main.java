package tetravex;


import tetravex.consoleui.ConsoleUI;
import tetravex.core.Color;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;
import tetravex.core.tile.Tile;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        while (consoleUI.start());
    }


}