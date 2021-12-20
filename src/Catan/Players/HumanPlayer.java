package Catan.Players;

import java.util.HashMap;
import java.util.Scanner;
import Catan.Board.*;
import Catan.Card.*;
import Catan.Run.*;

public class HumanPlayer extends Player{
    public HumanPlayer() {
        super(Settings.askName());
    }



    public void  askAction(Board board, Deck d){
        System.out.println("Choose an action to make:");
        if(canBuildRoad()){
            System.out.println("[1]Build Roads");
        }
        if (canBuildSettlement()){
            System.out.println("[2]Build Settlements");
        }
        if(canBuildCity()){
            System.out.println("[3]Build City");
        }
        if (canBuyDevCard()){
            System.out.println("[4]Buy Develop Card");
        }
        System.out.println("\n[5]Trade" +
                             "\n[6]Continue");
        //Todo Trade
        int option=0;
        try {
            Scanner scanReply = new Scanner(System.in);
            option = scanReply.nextInt();
        } catch (Exception e){
            askAction(board,d);
        }
        executeAction(option, board, d);

    }

    @Override
    public void placeFirstSettlement(Board b, boolean b1) {
        Location loc = Settings.askLocation();
        Settlement settlement = new Settlement(this, loc);
        b.placeStructure(settlement);
        if (b1){
            b.getAdjacentTilesStructure(loc);
        }
    //todo if b1 true -> winresource

    }

    @Override
    public void placeFirstRoad(Board b) {
    //todo place les premières routes à côté des premiers settlements
    }

    public boolean canCommerce(String s){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put(s, price.get(s));
        return (hasResources(resNeeded));
    }
}