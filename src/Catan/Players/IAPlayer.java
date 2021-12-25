package Catan.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import Catan.Card.*;
import Catan.Board.*;
import Catan.Run.*;

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
        ArrayList<Location> loc = suggestedLocationStructures(b);
        int randLocPos = r.nextInt(loc.size()-1);
        Location randomLoc = loc.get(randLocPos);
        Settlement settlement = new Settlement(this, randomLoc);
        b.placeStructure(settlement);
        if (b1){
            ArrayList<Tile> tiles = b.getAdjacentTilesStructure(randomLoc);
            for (Tile t : tiles){
                this.winResource(t.getResource(),1);
            }
        }
    }

    @Override
    public void placeFirstRoad(Board b) {
        ArrayList<Location> loc = suggestedLocationRoads(b);
        int randLocPos = r.nextInt(loc.size()-1);
        Location randomLoc = loc.get(randLocPos);
        Road road = new Road(randomLoc,this);
        b.placeRoad(road);
    }
}
