package Catan.gui.model;

import Catan.Board.Board;
import Catan.Players.Player;
import Catan.Run.GameRunner;

public class GameModel {
    GameRunner runner;
    Board board;
    Player[] settlers ;
    Player currentPlayer;


    public GameModel(GameRunner runner){
        this.runner=runner;
        board = runner.board;
        settlers= runner.allPlayers;
        currentPlayer= runner.getCurrent();
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getSettlers() {
        return settlers;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
