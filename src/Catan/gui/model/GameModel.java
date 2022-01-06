package Catan.gui.model;

import Catan.Board.Board;
import Catan.Players.Player;
import Catan.Run.GameRunner;

public class GameModel {
    GameRunner runner;
    Board board;
    Player[] settlers ;
    Player currentPlayer;
    public static int index = 0;


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

    public GameRunner getRunner() {
        return runner;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchCurrentPlayer(){
        currentPlayer = settlers[++index% settlers.length];
    }
}
