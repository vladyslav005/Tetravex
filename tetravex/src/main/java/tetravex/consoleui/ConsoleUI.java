package tetravex.consoleui;

import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;
import tetravex.data.entity.Comment;
import tetravex.data.entity.Rating;
import tetravex.data.entity.Score;
import tetravex.data.service.CommentService;
import tetravex.data.service.RatingService;
import tetravex.data.service.ScoreService;
import tetravex.data.service.serviceimpl.CommentServiceJDBC;
import tetravex.data.service.serviceimpl.RatingServiceJDBC;
import tetravex.data.service.serviceimpl.ScoreServiceJDBC;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleUI {
    private final String gameName = "tetravex";
    private final AtomicInteger playerScore = new AtomicInteger(0);
    private Thread threadToPrintTime = null;
    private Cursor cursor;
    private Game game;
    private BoardDrawer boardDrawer;
    private String playerName;
    private boolean showSolution = false;
    private boolean exit = false;
    private boolean hintsOn = false;

    private void play() {
        reset();

        enterPlayerName();
        int[] dimensions = inputWidthAndHeight();

        int width = dimensions[1], height = dimensions[0];
        Complexity complexity = inputComplexity();

        Game newGame = new Game(complexity, height, width);
        this.game = newGame;
        this.cursor = new Cursor(newGame, null, newGame.getField().getPlayed());
        boardDrawer = new BoardDrawer(this);
        mainLoop();
        mainMenu();
    }

    public void mainMenu() {
        System.out.println("Choose option: ");
        System.out.println("\t 1. New game");
        System.out.println("\t 2. Add comment");
        System.out.println("\t 3. Rate game");
        System.out.println("\t 4. Show TOP players");
        System.out.println("\t 5. Show comments");
        System.out.println("\t 6. Show average rating");
        System.out.println("\t 7. Exit");

        int option = InputUtils.getIntInput(1, 7);

        switch (option) {
            case 1 -> play();
            case 2 -> addPlayersComment();
            case 3 -> addPlayersRating();
            case 4 -> printBestPlayers();
            case 5 -> printComments();
            case 6 -> printAverageRating();
            case 7 -> {
            }
        }
    }

    public void mainLoop() {
        ConsoleUtils.enableRawMode();
        startThreadToPrintTime();

        int key;
        while (!exit && !game.isSolved()) {
            render();
            ConsoleUtils.printMessage("WASD - control, E - choose/put tile, Q - quit, H - hint, X - show solution (your score will be zero)");
            key = InputUtils.getInputChar();
            inputHandler(key);
            game.updateState();
        }

        stopThreadToPrintTime();

        if (game.isSolved()) {
            render();
            printVictoryMessage();
            ConsoleUtils.disableRawMode();
            if (playerScore.get() != 0) saveResultsToDB();
        } else
            ConsoleUtils.disableRawMode();
    }

    private void inputHandler(int key) {
        ASCIIKey asiKey = ASCIIKey.getASCIIKeyCode(key);
        if (asiKey == null) return;

        switch (asiKey) {
            case LOWERCASE_W, UPPERCASE_W -> cursor.moveUp();
            case LOWERCASE_S, UPPERCASE_S -> cursor.moveDown();
            case LOWERCASE_D, UPPERCASE_D -> cursor.moveRight();
            case LOWERCASE_A, UPPERCASE_A -> cursor.moveLeft();
            case LOWERCASE_E, UPPERCASE_E -> cursor.pickOrDropTile();
            case LOWERCASE_H, UPPERCASE_H -> hintsOn = !hintsOn;
            case LOWERCASE_X, UPPERCASE_X -> {
                stopThreadToPrintTime();
                playerScore.set(0);
                showSolution = !showSolution;
            }
            case LOWERCASE_Q, UPPERCASE_Q -> {
                exit = true;
                stopThreadToPrintTime();
                ConsoleUtils.clearScreen();
            }
        }
    }

    public synchronized void render() {
        if (boardDrawer == null) boardDrawer = new BoardDrawer(this);

        ConsoleUtils.clearScreen();
        int x = 4, y = 4;
        Field field = game.getField();
        int boardCharWidth = (field.getWidth() + 1) * 5 + 7;

        boardDrawer.drawBoard(field.getPlayed(), x, y);
        boardDrawer.drawBoard(field.getShuffled(), x + boardCharWidth, y);

        if (showSolution) boardDrawer.drawBoard(field.getSolved(), x + boardCharWidth * 2, y);
    }

    private void addPlayersScore() {
        enterPlayerName();

        ScoreService scoreService = new ScoreServiceJDBC();
        Score score = new Score(gameName, playerName, playerScore.get(), new Timestamp(new Date().getTime()));

        scoreService.addScore(score);
        System.out.println("\nYour score was written to database\n");
    }

    private void printAverageRating() {
        RatingService ratingService = new RatingServiceJDBC();
        int averageRating = ratingService.getAverageRating(gameName);

        if (averageRating != 0) {
            System.out.print("\nAverage " + averageRating);
            for (int i = 0; i < averageRating; i++) System.out.print(" ★");
            System.out.println("\n");
        } else
            System.out.println("\nGame is not rated\n");
        mainMenu();
    }


    private void printBestPlayers() {
        ScoreService scoreService = new ScoreServiceJDBC();
        List<Score> scoreList = scoreService.getTopScores(gameName);
        if (scoreList.isEmpty()) {
            System.out.println("\nThere is no played games\n");
        } else {
            System.out.println();
            for (int i = 0; i < scoreList.size(); i++) {
                Score s = scoreList.get(i);
                String formatted = String.format("%d. %s %d", i + 1, s.getPlayer(), s.getPoints());
                System.out.println(formatted);
            }
            System.out.println();
        }

        mainMenu();
    }

    private void printComments() {
        CommentService commentService = new CommentServiceJDBC();
        List<Comment> commentList = commentService.getComments(gameName);
        if (commentList.isEmpty()) {
            System.out.println("\nThere is no comments\n");
        } else {
            System.out.println();
            for (Comment s : commentList) {
                String formatted = String.format("%s : %s >>> %s",
                        s.getCommentedOn().toString(), s.getPlayer(), s.getComment());
                System.out.println(formatted);
            }
            System.out.println();
        }

        mainMenu();
    }


    private void addPlayersComment() {
        enterPlayerName();
        System.out.println("Enter your comment: ");

        CommentService commentService = new CommentServiceJDBC();
        String commentString = InputUtils.getStringInput();
        Comment comment = new Comment(gameName, playerName, commentString, new Timestamp(new Date().getTime()));

        commentService.addComment(comment);
        System.out.println("\nYour comment was added\n");
        mainMenu();
    }

    private void addPlayersRating() {
        enterPlayerName();
        System.out.println("Enter your rating (1 - 5)");
        RatingService ratingService = new RatingServiceJDBC();
        int ratingValue = InputUtils.getIntInput(1, 5);
        Rating rating = new Rating(gameName, playerName, ratingValue, new Timestamp(new Date().getTime()));
        ratingService.setRating(rating);
        System.out.println("\nYour rating was added\n");
        mainMenu();
    }

    private void saveResultsToDB() {
        System.out.println("Save your results to database? 1 - yes, 2 - no:");
        int save = InputUtils.getIntInput(1, 2);

        if (save == 1) {
            addPlayersScore();
        }
    }

    private Complexity inputComplexity() {
        Complexity complexity;
        System.out.println("Choose complexity (1 - EASY, 2 - MEDIUM, 3 - HARD): ");
        int input = InputUtils.getIntInput(1, 3);
        complexity = Complexity.values()[input - 1];

        return complexity;
    }

    private void enterPlayerName() {
        if (playerName == null) {
            System.out.println("Enter your name: ");
            playerName = InputUtils.getStringInput();
        }
    }

    private int[] inputWidthAndHeight() {
        int width, height;

        System.out.println("Enter width of the field (3 - 6): ");
        width = InputUtils.getIntInput(3, 6);
        System.out.println("Enter height of the field (3 - 6): ");
        height = InputUtils.getIntInput(3, 6);

        return new int[]{height, width};
    }

    private void reset() {
        playerScore.set(0);
        exit = false;
        cursor = null;
        game = null;
    }

    void printVictoryMessage() {
        ConsoleUtils.deleteMessage();
        ConsoleUtils.printMessage("Congratulations!!!   Press any key to quit");
        InputUtils.getInputChar();
        ConsoleUtils.clearScreen();
    }

    private void stopThreadToPrintTime() {
        if (threadToPrintTime != null) threadToPrintTime.interrupt();
    }

    private void startThreadToPrintTime() {
        stopThreadToPrintTime();
        Runnable runnable = () -> {
            try {
                while (true) {
                    synchronized (this) {
                        ConsoleUtils.printMessage("Your time/score: " + playerScore.get(), 4, 1);
                    }

                    playerScore.set((int) (System.currentTimeMillis() / 1000 - game.getStart().getTime() / 1000));
                    Thread.sleep(100);
                }
            } catch (InterruptedException ignored) {}
        };

        threadToPrintTime = new Thread(runnable);
        threadToPrintTime.start();
    }

    public boolean isHintsOn() {
        return hintsOn;
    }

    public Cursor getCursor() {
        return cursor;
    }

}
