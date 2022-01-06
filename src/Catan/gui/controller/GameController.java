package Catan.gui.controller;

import Catan.gui.model.GameModel;
import Catan.gui.view.run.GameView;

import javax.swing.*;

public class GameController {
    GameModel model;
    GameView view;

    public GameController(GameModel gameModel, GameView gameView){
        model = gameModel;
        view = gameView;
    }

    public void executeAction(){

    }

}
