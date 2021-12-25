package Catan.Players;

import java.util.ArrayList;
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
            System.out.println("[1]Build Road");
        }
        if (canBuildSettlement()){
            System.out.println("[2]Build Settlement");
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
        try (Scanner scanReply = new Scanner(System.in)){
            option = scanReply.nextInt();
        } catch (Exception e){
            askAction(board,d);
        }
        executeAction(option, board, d);

    }



    @Override
    public String resourceExchanged(String wanted) {
        System.out.println("In exchange of what?");
        String s= Settings.choiseRessource();
        if (canPayPrice(s,wanted)){
            return s;
        }
        return null;
    }

    public String resourceWanted() {
        System.out.println("What do you want?");
        return Settings.choiseRessource();
    }

    @Override
    public void placeFirstSettlement(Board b, boolean b1) {
        Location loc = Settings.askLocation();
        Settlement settlement = new Settlement(this, loc);
        b.placeStructure(settlement);
        if (b1){ //if true -> the player win resources from the adjacent tiles for the first time
            ArrayList<Tile> tiles = b.getAdjacentTilesStructure(loc);
            for (Tile t : tiles){
                this.winResource(t.getResource(),1);
            }
        }

    }

    @Override
    public void placeFirstRoad(Board b) {
    //todo place les premières routes à côté des premiers settlements
        Location loc = Settings.askLocation();
        Road road = new Road(loc,this);
        b.placeRoad(road);
    }


}