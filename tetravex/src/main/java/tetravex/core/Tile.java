package tetravex.core;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    final List<Color> borderColors = new ArrayList<>(4);

    public Tile(Color north, Color south, Color west, Color east) {
        borderColors.add(north);
        borderColors.add(south);
        borderColors.add(west);
        borderColors.add(east);
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


}
