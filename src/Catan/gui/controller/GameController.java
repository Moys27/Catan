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

    public void insertLocation() {
        int [] location = new int [3];
        int i =0;
        for (JTextField f : view.getLocationTextField()){
                try{
                    location[i++]= Integer.parseInt(f.getText());
                    f.setText(null);
                }catch(NumberFormatException e){
                    view.showWarning("Wrong input format!");
                    f.setText(null);
                }
        }
    }
}
