package tetravex.consoleui;

import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleUI {

    private Cursor cursor;
    private Game game;
    private BoardDrawer boardDrawer;
    private boolean consoleRawMode = false;
    private boolean showSolution = false;
    private boolean playAgain = false;
    private boolean exit = false;


    public boolean start() {
        reset();
        int[] dimensions = inputWidthAndHeight();

        int width = dimensions[1], height = dimensions[0];
        Complexity complexity = inputComplexity();

        Game game = new Game(complexity, height, width);
        this.game = game;
        this.cursor = new Cursor(game, null, game.getField().getPlayed());
        boardDrawer = new BoardDrawer(cursor);

        mainLoop();

        return playAgain;
    }


    public void mainLoop() {

        ConsoleUtils.clearScreen();
        if (!consoleRawMode) enableRawMode();

        int key;
        while (true) {
            render();
            ConsoleUtils.printMessage("WASD - control, E - choose tile, Q - quit, E, X - show solution");

            if (game.isSolved()) {
                printVictoryMessage();
                break;
            }

            key = getInputChar();
            inputHandler(key);
            if (exit) break;
            game.updateState();

        }

        disableRawMode();
    }


    private void inputHandler(int key) {
        Key asciKey = Key.getASCIIKeyCode(key);
        if (asciKey == null) return;

        switch (asciKey) {
            case LOWERCASE_W, UPPERCASE_W -> cursor.moveUp();
            case LOWERCASE_S, UPPERCASE_S -> cursor.moveDown();
            case LOWERCASE_D, UPPERCASE_D -> cursor.moveRight();
            case LOWERCASE_A, UPPERCASE_A -> cursor.moveLeft();
            case LOWERCASE_E, UPPERCASE_E -> cursor.pickOrDropTile();
            case LOWERCASE_R, UPPERCASE_R -> playAgain = game.isSolved();
            case LOWERCASE_X, UPPERCASE_X -> showSolution = !showSolution;
            case LOWERCASE_Q, UPPERCASE_Q -> {
                exit = true;
                ConsoleUtils.clearScreen();
                playAgain = false;
            }
            default -> {}
        }
    }


    public void render() {
        if (boardDrawer == null) boardDrawer = new BoardDrawer(cursor);

        ConsoleUtils.clearScreen();
        int x = 4, y = 4;
        Field field = game.getField();
        int boardCharWidth = (field.getWidth() + 1) * 5 + 7;

        boardDrawer.drawBoard(field.getPlayed(), x, y);
        boardDrawer.drawBoard(field.getShuffled(), x + boardCharWidth, y);

        if (showSolution) boardDrawer.drawBoard(field.getSolved(), x + boardCharWidth * 2, y);
    }


    private int getInputChar() {
        ConsoleUtils.setCursorPos(0, 0);

        int key;
        try {
            key = System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ConsoleUtils.setCursorPos(0, 0);
        System.out.print("  ");

        return key;
    }

    private Complexity inputComplexity() {
        Complexity complexity;
        Scanner sc = new Scanner(System.in);

        for (int input, i = 0; true; i++) {
            try {
                if (i != 0) System.out.println("Wrong input");
                System.out.print("Choose complexity (1 - EASY, 2 - MEDIUM, 3 - HARD): ");
                input = sc.nextInt();

                if (input > 0 && input < 4) {
                    complexity = Complexity.values()[input - 1];
                    break;
                }
            } catch (Exception ignored) {
                sc.next();
            }
        }

        return complexity;
    }

    private int[] inputWidthAndHeight() {
        int width = 0, height = 0;

        Scanner sc = new Scanner(System.in);
        for (int i = 0; !(width > 1 && width < 11 && height > 1 && height < 11); i++) {
            try {
                if (i != 0) System.out.println("Wrong input");
                System.out.print("Type width of the field (2 - 10): ");
                width = sc.nextInt();
                System.out.print("Type height of the field (2 - 10): ");
                height = sc.nextInt();
            } catch (Exception ignored) {
                if (sc.hasNext()) sc.next();
            }
        }

        return new int[]{height, width};
    }


    private void disableRawMode() {

        String[] cmd2 = {"/bin/sh", "-c", "stty cooked </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd2).waitFor();
            consoleRawMode = false;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void enableRawMode() {
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
            consoleRawMode = true;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void reset() {
        playAgain = false;
        exit = false;
        cursor = null;
        game = null;
    }

    void printVictoryMessage() {
        ConsoleUtils.printMessage("Congratulations!!!");
        ConsoleUtils.printMessage("Press R if you want to play again or any key to quit", 49, 20);
        int key = getInputChar();
        inputHandler(key);
        ConsoleUtils.clearScreen();
        disableRawMode();
    }

}
