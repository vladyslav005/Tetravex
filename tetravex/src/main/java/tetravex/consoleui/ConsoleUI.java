package tetravex.consoleui;

import tetravex.core.Color;
import tetravex.core.Field;
import tetravex.core.Game;
import tetravex.core.Tile;

import java.util.List;

public class ConsoleUI {

    Game game;


    //TODO : render


    public ConsoleUI(Game game) {
        this.game = game;
    }

    public void render() {
        int x = 2, y = 2;
        Field field = game.getField();
        int boordCharWidth = (field.getWidth() + 1) * 5 + 7;

        DrawingUtils.drawBoard(field.getPlayed(), x, y);

        DrawingUtils.drawBoard(field.getShuffled(), x + boordCharWidth, y);

        DrawingUtils.drawBoard(field.getSolved(), x + boordCharWidth*2, y);



    }


}
