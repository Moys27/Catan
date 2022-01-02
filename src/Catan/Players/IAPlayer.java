package Catan.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    //Todo Creer quelque chose empechant les numeros magiques et les strings magiques
    @Override
    public void  askAction(Board board, Deck d){
        HashMap<Integer,Integer> option= new HashMap<>();
        ArrayList<Integer> possibilities= new ArrayList<>();
        if(canBuildRoad()){
                possibilities.add(1);
        }
        if (canBuildSettlement()){
            possibilities.add(2);
        }
        if(canBuildCity()){
            possibilities.add(3);
        }
        if (canBuyDevCard()){
            possibilities.add(4);
        }
        //Todo conUseDevCard
        if (resourceWanted()!=null){
            possibilities.add(6);
        }
        Random r= new Random();
        if(possibilities.size()==0){
            executeAction( 7, board, d);
            return;
        }
        int randomOption= r.nextInt(possibilities.size());
        executeAction( possibilities.get(randomOption), board, d);

    }

    /**
     * Helps to the IAPlayer to choise between differents options
     */
    public String randomChoiseString(ArrayList<String> list){
        int randomOption= r.nextInt(list.size());
        return list.get(randomOption);
    }
    public Integer randomChoiseInteger(ArrayList<Integer> list){
        int randomOption= r.nextInt(list.size());
        return list.get(randomOption);
    }

    public String resourceWanted(){
        int random = r.nextInt(ResourceCard.array.length);
        for(int i=0;i<ResourceCard.array.length;i++) {
            for (int j = 0; j < ResourceCard.array.length - 1; j++) {
                if (canPayPrice(ResourceCard.array[(random + i + j) % ResourceCard.array.length], ResourceCard.array[(random + i) % ResourceCard.array.length])) {
                    return ResourceCard.array[(random + i) % ResourceCard.array.length];
                }
            }
        }
        return null;
    }



    public String resourceExchanged(String resourceWanted){
        ArrayList<String> posibilities= new ArrayList<>();
        for (String key: price.keySet()){
            if (canPayPrice(key,resourceWanted)){
                posibilities.add(key);
            }
        }
        return (String) randomChoiseString(posibilities);
    }

    public void optionsDevCard(DevCard card, Board board){
        useDevCard(card,board);
    }

    public String randomRessource(){
        return ResourceCard.array[r.nextInt(ResourceCard.ore)];
    }


    void useYearOfPlenty() {
        for (int i=0; i<2;i++){
            winResource(ResourceCard.array[r.nextInt(ResourceCard.ore)],1);
        }
    }

    @Override
    void actionDevCard(Board board) {
        ArrayList<DevCard> options= new ArrayList();
        for( DevCard card:hand){
            if (card.getCanUSe()){
                options.add(card);
            }
        }
        if (options!=null){
            int random= r.nextInt(options.size());
            useDevCard(options.get(random),board);
        }
    }

    @Override
    public void discartCards(int i) {
        while(i>0){
            String random= randomRessource();
            if (resourceDeck.get(random)>0){
                looseResource(random,1);
                i--;
            }
        }
    }
    public Player choosePlayerToStolen(List<Player> players){
        if (players==null){
            return null;
        }
        if (players.isEmpty()){
            return null;
        }
        Player choosed= players.get(r.nextInt(players.size()));
        return choosed;
    }

    @Override
    public int[] askCoordinatesTile() {
        return new int[]{r.nextInt(Board.getSizeT() ), r.nextInt(Board.getSizeT() )};

    }



    @Override
    public void placeFirstSettlement(Board b, boolean b1) {
        ArrayList<Location> loc = suggestedLocationStructures(b);
        int randLocPos = r.nextInt(loc.size());
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
