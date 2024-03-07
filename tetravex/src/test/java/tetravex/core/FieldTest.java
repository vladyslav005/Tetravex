package tetravex.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetravex.core.tile.Tile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldTest {
    private static final int width = 10;
    private static final int height = 10;
    private static final Complexity complexity = Complexity.HARD;

    private Game game;

    @BeforeEach
    void setUp() {
        this.game = new Game(complexity, height, width);
    }

    @Test
    void placeTileTest() {
        int xSource = Utils.getRandInt(9);
        int ySource = Utils.getRandInt(9);
        int xDest = Utils.getRandInt(9);
        int yDest = Utils.getRandInt(9);

        Field field = game.getField();
        List<List<Tile>> sourceBoard = game.getField().getShuffled();
        List<List<Tile>> destBoard = game.getField().getPlayed();

        Tile tileToMove = sourceBoard.get(ySource).get(xSource);

        field.placeTile(tileToMove, destBoard, xDest, yDest);

        assertEquals(tileToMove, destBoard.get(yDest).get(xDest));
    }

    @Test
    void generationTest() {
        List<List<Tile>> generatedBoard = game.getField().getSolved();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile currentTile = generatedBoard.get(i).get(j);

                int[][] indexesShift = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

                for (int k = 0; k < 3; k++) {
                    int y = i + indexesShift[k][0];
                    int x = j + indexesShift[k][1];
                    if (!(y < height && y >= 0 && x < width && x >= 0)) continue;
                    Tile neighborTile = generatedBoard.get(y).get(x);

                    assertEquals(currentTile.getBorderColors().get(k),
                            neighborTile.getOpositeSideColor(k));
                }
            }
        }
    }



}