package Catan.gui.model;

import Catan.Board.Board;
import Catan.Players.Player;
import Catan.Run.GameRunner;

public class GameModel {
    Board board;
    Player[] settlers ;

    public GameModel(GameRunner runner){
        board = runner.board;
        settlers= runner.allPlayers;
    }

    public Board getBoard() {
        return board;
    }
}
