package com.company;

import java.util.Random;

public class IAPlayer extends Player{
    private Random r;
    public IAPlayer() {
        super(Settings.giveName());
        r = new Random();
    }


    @Override
    public void askAction(Board board, Deck d) {
        int i =r.nextInt(7);

    }
    @Override
    public void placeFirstSettlement(Board b, boolean b1) {
    //todo
    }



    @Override
    public void placeFirstRoad(Board b) {
    //todo
    }
}
