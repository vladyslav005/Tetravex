package tetravex.consoleui;

import tetravex.core.Game;
import tetravex.core.tile.Tile;
import tetravex.core.tile.TileWrapper;

import java.util.List;

public class Cursor {
    private TileWrapper selectedTile;
    private TileWrapper pickedTile;
    private Game game;

    public Cursor(Game game, Tile selectedTile, List<List<Tile>> currentBoard) {
        this.selectedTile = new TileWrapper(
                selectedTile, currentBoard,
                0, 0);

        this.pickedTile = new TileWrapper(null, currentBoard, 0, 0);
        this.game = game;
    }

    public void pickOrDropTile() {
        if (pickedTile.getTile() == null) {
            pickTile();
        } else {
            dropTile();
        }
    }

    public void dropTile() {
        if (pickedTile.getTile() != null && selectedTile.getTile() == null) {
            game.getField().placeTile(pickedTile.getTile(), selectedTile.getBoard(),
                    selectedTile.getX(), selectedTile.getY());
            selectedTile.setTile(pickedTile.getTile());
            pickedTile.setTile(null);
        }
    }

    public void pickTile() {
        if (selectedTile.getTile() != null) {
            setSelectedToPicked();
        }
    }

    private void setSelectedToPicked() {
        pickedTile.setTile(selectedTile.getTile());
        pickedTile.setBoard(selectedTile.getBoard());
        pickedTile.setX(selectedTile.getX());
        pickedTile.setY(selectedTile.getY());
    }

    public void moveDown() {
        int height = selectedTile.getBoard().size();

        if (selectedTile.getY() + 1 >= height) {

        } else {
            selectedTile.setY(selectedTile.getY() + 1);
            selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
        }
    }

    public void moveUp() {
        if (selectedTile.getY() - 1 < 0) {
        } else {
            selectedTile.setY(selectedTile.getY() - 1);
            selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
        }
    }

    public void moveRight() {
        int width = selectedTile.getBoard().get(0).size();

        if (selectedTile.getX() + 1 >= width) {
            if (selectedTile.getBoard() == game.getField().getPlayed()) {
                selectedTile.setBoard(game.getField().getShuffled());
                selectedTile.setX(0);
                selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
            }
        } else {
            selectedTile.setX(selectedTile.getX() + 1);
            selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
        }
    }

    public void moveLeft() {
        int width = selectedTile.getBoard().get(0).size();

        if (selectedTile.getX() - 1 < 0) {
            if (selectedTile.getBoard() == game.getField().getShuffled()) {
                selectedTile.setBoard(game.getField().getPlayed());
                selectedTile.setX(width - 1);
                selectedTile.setTile(selectedTile.getBoard().get(selectedTile.getY()).get(selectedTile.getX()));
            }
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