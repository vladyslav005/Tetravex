package tetravex.core;

import tetravex.core.tile.TileState;

import java.sql.Timestamp;
import java.util.Date;

public class Game {

    private final Field field;
    private final Timestamp start;
    private GameState state = GameState.PLAYING;

    public Game(Field field) {
        this.start = new Timestamp(new Date().getTime());
        this.field = field;
    }


    public Game(Complexity complexity, int height, int width) {
        this(new Field(width, height, complexity));
    }

    public void updateState() {
        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getHeight(); j++) {
                if (field.getPlayed().get(i).get(j) == null ||
                        field.getPlayed().get(i).get(j).getState() != TileState.CORRECT) {
                    state = GameState.PLAYING;
                    return;
                }
            }
        }

        state = GameState.SOLVED;
    }

    public boolean isSolved() {
        return state == GameState.SOLVED;
    }

    public Field getField() {
        return field;
    }

    public GameState getState() {
        return state;
    }

    public Timestamp getStart() {
        return start;
    }
}
