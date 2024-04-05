package tetravex.core.tile;

import tetravex.core.Color;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    final List<Color> borderColors = new ArrayList<>(4);
    private TileState state = TileState.UNTOUCHED;

    public Tile(Color north, Color south, Color west, Color east) {
        borderColors.add(north);
        borderColors.add(south);
        borderColors.add(west);
        borderColors.add(east);
    }


    public Color getOpositeSideColor(int colorIdx) {
        return switch (colorIdx) {
            case 0 -> borderColors.get(1);
            case 1 -> borderColors.get(0);
            case 2 -> borderColors.get(3);
            case 3 -> borderColors.get(2);
            default -> null;
        };
    }

    public List<Color> getBorderColors() {
        return borderColors;
    }

    public Color getN() {
        return borderColors.get(0);
    }

    public Color getS() {
        return borderColors.get(1);
    }

    public Color getW() {
        return borderColors.get(2);
    }

    public Color getE() {
        return borderColors.get(3);
    }



    public TileState getState() {
        return state;
    }


    public void setState(TileState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;

        return tile.getN() == getN() &&
                tile.getS() == getS() &&
                tile.getW() == getW() &&
                tile.getE() == getE();
    }


    @Override
    public String toString() {
        return borderColors.toString();
    }
}
