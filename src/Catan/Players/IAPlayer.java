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
        if (!hand.isEmpty()) {
            possibilities.add(5);
        }
        if (resourceWanted()!=null){
            possibilities.add(6);
        }
        Random r= new Random();
        if(possibilities.size()==0){
            executeAction( 7, board, d);
            return;
        }
        int randomOption= r.nextInt(possibilities.size());
        executeAction(possibilities.get(randomOption), board, d);
    }

    @Override
    public void executeAction(int option, Board board, Deck d){
        Location location;
        switch (option){
            case 1 :
                location = chooseRandomLocation(suggestedLocationRoads(board));
                if(canBuildRoadAt(board,location)){
                    board.placeRoad(buildRoad(board,location));
                }
                askAction(board,d);
                break;
            case 2:
                location = chooseRandomLocation(suggestedLocationSettlements(board));
                if(canBuildSettlementAt(board,location)){
                    board.placeStructure(buildSettlement(board,location));
                }
                askAction(board,d);
                break;
            case 3:
                location = chooseRandomLocation(suggestedLocationCities(board));
                if(canBuildCityAt(board,location)){
                    buildCity(board,location);
                }
                askAction(board,d);
                break;
            case 4:
                if(canBuyDevCard()){
                    buyDevCard(d);
                }
                askAction(board,d);
                break;
            case 5:
                if (!hand.isEmpty()) {
                    actionDevCard(board);
                }
                askAction(board,d);
                break;
            case 6:
                commerce();
                askAction(board,d);
                break;
            case 7:
                next();
                break;
        }
    }


    private Location chooseRandomLocation(ArrayList<Location> suggestedLocation) {
        return suggestedLocation.get(r.nextInt(suggestedLocation.size()));
    }

    /**
     * Helps to the IAPlayer to choose between different options
     */
    public String randomChoiceString(ArrayList<String> list){
        int randomOption= r.nextInt(list.size());
        return list.get(randomOption);
    }
    public Integer randomChoiceInteger(ArrayList<Integer> list){
        int randomOption= r.nextInt(list.size());
        return list.get(randomOption);
    }

    /**
     * Choissi une ressource que l'IA puisse acheter
     * @return
     */
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
        ArrayList<String> possibilities= new ArrayList<>();
        for (String key: price.keySet()){
            if (canPayPrice(key,resourceWanted)){
                possibilities.add(key);
            }
        }
        return (String) randomChoiceString(possibilities);
    }

    public void optionsDevCard(DevCard card, Board board){
        useDevCard(card,board);
    }

    public String randomResource(){
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
    public void discardCards(int i) {
        while(i>0){
            String random= randomResource();
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


    /**
     * Demande la Tile o`tu veux placer le voleur
     * @return
     */
    @Override
    public int[] askCoordinatesTile() {
        return new int[]{r.nextInt(Board.getSizeT() ), r.nextInt(Board.getSizeT() )};

    }

    @Override
    public void placeFirstSettlement(Board b, boolean b1) {
        Location randomLoc = chooseRandomLocation( b.suggestedLocationFirstSettlements());
        Settlement settlement = new Settlement(this, randomLoc);
        structureMap.put(randomLoc,settlement);
        b.placeStructure(settlement);
        if (b1){
            ArrayList<Tile> tiles = b.getAdjacentTilesStructure(randomLoc);
            for (Tile t : tiles){
                if(t.getResource()!= "DESERT")
                    this.winResource(t.getResource(),1);
            }
        }
    }

    @Override
    public void placeFirstRoad(Board b) {
        Location randomLoc = chooseRandomLocation(suggestedLocationRoads(b));
        Road road = new Road(randomLoc,this);
        roadsMap.put(randomLoc,road);
        b.placeRoad(road);
    }

}
