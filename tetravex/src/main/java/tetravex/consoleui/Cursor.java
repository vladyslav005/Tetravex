package tetravex.consoleui;

import tetravex.core.Field;
import tetravex.core.Game;
import tetravex.core.Tile;

import java.util.List;

public class Cursor {
//    private Tile selectedTile;
//    private Tile pickedTile;

    private TileWrapper selectedTile;
    private TileWrapper pickedTile;
    private Game game;


    public Cursor(Game game, Tile selectedTile, List<List<Tile>> currentBoard) {
        this.selectedTile = new TileWrapper(
                selectedTile, currentBoard,
                0, 0);


        this.game = game;
    }


    public void pickOrDropTile() {
        if (pickedTile == null) pickedTile = selectedTile;
        else {

        }

    }

    public void moveDown()  {
        int height = selectedTile.getBoard().size(), width = selectedTile.getBoard().get(0).size();

        if (selectedTile.getY() + 1 >= height) return;
        else {
            selectedTile.setY(selectedTile.getY() + 1);
            selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
        }
    }

    public void moveUp()  {
        int height = selectedTile.getBoard().size(), width = selectedTile.getBoard().get(0).size();

        if (selectedTile.getY() - 1 < 0) return;
        else {
            selectedTile.setY(selectedTile.getY() - 1);
            selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
        }
    }

    public void moveRight() {
        int height = selectedTile.getBoard().size(), width = selectedTile.getBoard().get(0).size();

        if (selectedTile.getX() + 1 >= width) {
            if (selectedTile.getBoard() == game.getField().getPlayed()) {
                selectedTile.setBoard(game.getField().getShuffled());
                selectedTile.setX(0);
                selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
            } else return;

        } else {
            selectedTile.setX(selectedTile.getX() + 1);
            selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
        }
    }

    public void moveLeft() {
        int height = selectedTile.getBoard().size(), width = selectedTile.getBoard().get(0).size();

        if (selectedTile.getX() - 1 < 0) {
            if (selectedTile.getBoard() == game.getField().getShuffled()) {
                selectedTile.setBoard(game.getField().getPlayed());
                selectedTile.setX(width-1);
                selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
            } else return;

        } else {
            selectedTile.setX(selectedTile.getX() - 1);
            selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
        }
    }


    public TileWrapper getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(TileWrapper selectedTile) {
        this.selectedTile = selectedTile;
    }

    public TileWrapper getPickedTile() {
        return pickedTile;
    }

    public void setPickedTile(TileWrapper pickedTile) {
        this.pickedTile = pickedTile;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
