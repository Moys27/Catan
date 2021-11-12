package com.company;

public class HumanPlayer extends Player{
    public HumanPlayer() {
        super(Settings.askName());
    }


}