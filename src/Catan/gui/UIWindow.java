package Catan.gui;

import Catan.Run.GameRunner;

public class UIWindow {
    private View wind;
    private GameRunner game;

    UIWindow(){
        game= new GameRunner();
        wind = new View(game);
        wind.setVisible(true);
    }

    public static void main(String[] args) {
        UIWindow ui = new UIWindow();
    }
}
