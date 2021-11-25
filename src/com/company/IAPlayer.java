package com.company;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class IAPlayer extends Player{
    private Random r;
    public IAPlayer() {
        super(Settings.giveName());
        r = new Random();
    }


    @Override
    public void  askAction(Board board, Deck d){
        HashMap<Integer,Integer> option= new HashMap<>();
        int i=1;
        if(canBuildRoad()){
                option.put(i,1);
                i++;
        }
        if (canBuildSettlement()){
            option.put(i,2);
            i++;        }
        if(canBuildCity()){
            option.put(i,3);
            i++;        }
        if (canBuyDevCard()){
            option.put(i,4);
            i++;        }
        //Todo trade
        //Todo conUseDevCard

        Random r= new Random();
        int randomOption= r.nextInt(i);
        executeAction(6, board, d);
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
