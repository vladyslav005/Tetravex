package tetravex.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetravex.core.tile.Tile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
    private static final int width = 10;
    private static final int height = 10;
    private static final Complexity complexity = Complexity.HARD;
    private Game game;

    @BeforeEach
    void setUp() {
        this.game = new Game(complexity, height, width);
    }

    @Test
    void stateTest1() {
        List<List<Tile>> solved = game.getField().getSolved();
        List<List<Tile>> played = game.getField().getPlayed();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                game.getField().placeTile(
                        solved.get(i).get(j),
                        played,
                        j, i
                );
            }
        }

        game.updateState();
        assertEquals(GameState.SOLVED, game.getState());
    }

    @Test
    void stateTest2() {
        List<List<Tile>> shuffled = game.getField().getShuffled();
        List<List<Tile>> played = game.getField().getPlayed();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                game.getField().placeTile(
                        shuffled.get(i).get(j),
                        played,
                        j, i
                );
            }
        }

        game.updateState();
        assertEquals(GameState.PLAYING, game.getState());
    }
}