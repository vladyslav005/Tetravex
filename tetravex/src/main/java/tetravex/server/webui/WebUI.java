package tetravex.server.webui;


import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import tetravex.core.Complexity;
import tetravex.core.Game;
import tetravex.core.GameState;
import tetravex.core.Utils;
import tetravex.core.tile.Tile;
import tetravex.server.dto.ClientRequestDto;

import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WebUI {

    @Getter
    private Game game;


    public void newGame(Complexity complexity, int width, int height) {
        game = new Game(complexity, height, width);
    }

    public void swapTiles(ClientRequestDto sourceTile, ClientRequestDto destTile) {
        List<List<Tile>> sourceBoard;
        List<List<Tile>> destBoard;

        if (sourceTile.getBoard().equals("shuffled")) {
            sourceBoard = game.getField().getShuffled();
        } else sourceBoard = game.getField().getPlayed();

        if (destTile.getBoard().equals("shuffled")) {
            destBoard = game.getField().getShuffled();
        } else destBoard = game.getField().getPlayed();

        game.getField().swapTiles(
                sourceBoard, destBoard,
                sourceTile.getX(), sourceTile.getY(),
                destTile.getX(), destTile.getY());
        game.updateState();
    }

    public GameState getGameState() {
        return game.getState();
    }

    public List<List<TileWebModel>> getThymeleafAttributeShuffled() {
        return createDataStructureForTh(game.getField().getShuffled());
    }

    public List<List<TileWebModel>> getThymeleafAttributeSolved() {
        return createDataStructureForTh(game.getField().getSolved());
    }

    private List<List<TileWebModel>> createDataStructureForTh(List<List<Tile>> board) {

        List<List<TileWebModel>> output = Utils.initTwoDimensionalArray(board.get(0).size(), board.size());

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                Tile tile = board.get(i).get(j);
                output.get(i).set(j, new TileWebModel(tile, i, j));
            }
        }

        return output;
    }
}