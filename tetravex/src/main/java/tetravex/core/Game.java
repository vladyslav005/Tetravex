package tetravex.core;

import tetravex.core.tile.TileState;

public class Game {

    private Field field;
    private GameState state = GameState.PLAYING;

    public Game(Field field) {
        this.field = field;
    }

    public Game(Complexity complexity, int height, int width) {
        this.field = new Field(width, height, complexity);
    }

    public void updateState() {
        for (int i = 0; i < field.getPlayed().size(); i++) {
            for (int j = 0; j < field.getPlayed().get(0).size(); j++) {
                if (field.getPlayed().get(i).get(j) == null ||
                        field.getPlayed().get(i).get(j).getState() == TileState.WRONG) {
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

}
