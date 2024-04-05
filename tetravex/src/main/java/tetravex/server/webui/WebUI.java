package tetravex.server.webui;


import org.springframework.stereotype.Component;
import tetravex.core.Complexity;
import tetravex.core.Field;
import tetravex.core.Game;
import tetravex.core.Utils;
import tetravex.core.tile.Tile;

import java.util.List;

@Component
public class WebUI {
    private Game game;


    public void newGame(Complexity complexity, int width, int height) {
        game = new Game(complexity, height, width);
    }



    public List<List<TileWebModel>> getThymeleafAttributeShuffled() {
        return createDataStructureForTh(game.getField().getShuffled());
    }

    public List<List<TileWebModel>> getThymeleafAttributeSolved() {
        return createDataStructureForTh(game.getField().getShuffled());
    }

    private List<List<TileWebModel>> createDataStructureForTh(List<List<Tile>> board) {

        List<List<TileWebModel>> output = Utils.initTwoDimensionalArray(board.size(), board.get(0).size());

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size() ; j++) {
                Tile tile = board.get(i).get(j);
                output.get(i).set(j, new TileWebModel(tile));
            }
        }

        return output;
    }

}
