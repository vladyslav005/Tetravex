package tetravex.core.tile;

import java.util.List;

public class TileWrapper {
    private Tile tile;
    private List<List<Tile>> board;
    private int y ;
    private int x ;

    public TileWrapper(Tile tile, List<List<Tile>> board, int x, int y) {
        this.tile = tile;
        this.board = board;
        this.y = y;
        this.x = x;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public List<List<Tile>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Tile>> board) {
        this.board = board;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "TileWrapper{" +
                "tile=" + tile +
                ", board=" + board +
                ", y=" + y +
                ", x=" + x +
                '}';
    }
}
