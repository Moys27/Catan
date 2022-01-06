package Catan.gui.controller;

import Catan.Board.Location;
import Catan.Run.Settings;
import Catan.gui.model.GameModel;
import Catan.gui.view.run.GameView;

import javax.swing.*;
import java.util.ArrayList;

public class GameController {
    GameModel model;
    GameView view;

    public GameController(GameModel gameModel, GameView gameView){
        model = gameModel;
        view = gameView;
    }

    public void executeAction(int option){
        switch (option){
            case 1: popLocationUp(model.getCurrentPlayer().suggestedLocationRoads(model.getBoard())); break;
            case 2: popLocationUp(model.getCurrentPlayer().suggestedLocationSettlements(model.getBoard()));break;
            case 3: popLocationUp(model.getCurrentPlayer().suggestedLocationCities(model.getBoard()));break;
            case 4:
            case 5:
            case 6:
            case 7:
            default: model.switchCurrentPlayer(); break;
        }

    }

    private void popLocationUp(ArrayList<Location> suggestion) {
        view.popUp(suggestion);
    }

    public void generateIAAction() {
        model.getCurrentPlayer().askAction(model.getBoard(),model.getRunner().getDeckCard());
        model.switchCurrentPlayer();
    }
}
