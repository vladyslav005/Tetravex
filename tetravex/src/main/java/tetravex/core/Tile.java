package tetravex.core;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    List<Colors> borderColors = new ArrayList<>(4);

    public Tile(Colors north, Colors south, Colors west, Colors east) {
        borderColors.add(north);
        borderColors.add(south);
        borderColors.add(west);
        borderColors.add(east);
    }

    public Colors getN() {return borderColors.get(0);}
    public Colors getS() {return borderColors.get(1);}
    public Colors getW() {return borderColors.get(2);}
    public Colors getE() {return borderColors.get(3);}


}
