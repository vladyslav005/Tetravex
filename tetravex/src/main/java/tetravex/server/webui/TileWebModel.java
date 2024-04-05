package tetravex.server.webui;

import lombok.ToString;
import tetravex.core.tile.Tile;

@ToString
class TileWebModel {
    public String right;
    public String left;
    public String top;
    public String bottom;
    public TileWebModel(Tile tile) {
        this. top = tile.getN().name().toLowerCase() + " " + "top";
        this.bottom = tile.getS().name().toLowerCase() + " " + "bottom";
        this.right = tile.getE().name().toLowerCase() + " " + "right";
        this.left = tile.getW().name().toLowerCase() + " " + "left";
    }
}
