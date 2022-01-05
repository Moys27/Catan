package Catan.Players;

import java.util.ArrayList;
import Catan.Board.*;
import Catan.Card.*;
import Catan.Run.*;

import java.util.List;

public class HumanPlayer extends Player{
    public HumanPlayer() {
        super(Settings.askName());
        //super("Tania");
    }



    public void  askAction(Board board, Deck d){
        showRessource();
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
        if (!hand.isEmpty()){
            System.out.println("[5]Use Develop Card");
        }
        System.out.println("[6]Trade");
        System.out.println("[7]Continue");
        int option=Settings.chooseBetweenCards(7);
        executeAction(option, board, d);
    }

    public void executeAction(int option, Board board, Deck d){
        Location location;
        switch (option){
            case 1 :
                showSuggestedLocationRoads(board);
                location = Settings.askLocation();
                if(canBuildRoadAt(board,location)){
                    board.placeRoad(buildRoad(board,location));
                }
                askAction(board,d);
                break;
            case 2:
                showSuggestedLocationSettlements(board);
                location = Settings.askLocation();
                if(canBuildSettlementAt(board,location)){
                    board.placeStructure(buildSettlement(board,location));
                }
                askAction(board,d);
                break;
            case 3:
                showSuggestedLocationCities(board);
                location=Settings.askLocation();
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
                System.out.println("Price: "+price);
                commerce();
                askAction(board,d);
                break;
            case 7:
                next();
                break;
        }
    }

    public String resourceWanted() {
        System.out.println("What do you want?");
        return Settings.choiseRessource();
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



    @Override
    public void placeFirstSettlement(Board b, boolean b1) {
        System.out.println("Choose the location for the settlement.");
        b.showSuggestedLocationFirstSettlements();
        Location loc = Settings.askLocation();
        Settlement settlement = new Settlement(this, loc);
        structureMap.put(loc,settlement);
        b.placeStructure(settlement);
        if (b1){ //if true -> the player win resources from the adjacent tiles for the first time
            ArrayList<Tile> tiles = b.getAdjacentTilesStructure(loc);
            for (Tile t : tiles){
                if(t.getResource()!= "DESERT")
                    this.winResource(t.getResource(),1);
            }
        }

    }

    @Override
    public void placeFirstRoad(Board b) {
        System.out.println("Choose the location for the road.");
        showSuggestedLocationRoads(b);
        Location loc = Settings.askLocation();
        Road road = new Road(loc,this);
        roadsMap.put(loc,road);
        b.placeRoad(road);
    }

    public void showHand(){
        int i=1;
        for (DevCard card: hand){
            System.out.print("["+i+"]"+card.getTitle()+ "  ");
            i++;
        }
        System.out.println();
    }

    public void showRessource(){
        System.out.print("Resources: ");
        System.out.println(resourceDeck);
        System.out.println();

    }

    public DevCard selectDevCard(){
        int i= Settings.chooseBetweenCards(hand.size() );
        return hand.get(i-1); //-1 car s.chooseBetweenCards(hand.size()) return a number between 1 and hand.size

    }



    public void optionsDevCard(DevCard card, Board board){
        int i= Settings.chooseActionDevCard();
        if (i==1){
            if (card.getCanUSe())
                useDevCard(card,board);
            else {
                System.out.println("You can not use this card in this turn");
            }
        } else {
            System.out.println(card.getDescription());
            System.out.println();
        }
    }
    @Override
    void useYearOfPlenty() {
        System.out.println("You can choose 2 resources for free.");
        System.out.println("First:");
        String First= Settings.choiseRessource();
        winResource(First,1);
        System.out.println("Second:");
        winResource(Settings.choiseRessource(),1);
    }

    @Override
    void actionDevCard(Board board) {
        showHand();
        optionsDevCard(selectDevCard(),board);
    }

    @Override
    public void discardCards(int i) {
        System.out.println("You need to discard "+i+" card(s)");
        for (int j=0;j<i;j++){
            looseResource(Settings.choiseRessource(),1);
        }
    }
    public Player choosePlayerToStolen(List<Player> players){
        System.out.println("Choose the player wich you will stole");
        for (int i=0;i<players.size();i++){
            System.out.println("["+(i+1)+"] "+players.get(i).name);
        }
        Player choosed= players.get(Settings.chooseBetweenCards(players.size()));
        return choosed;
    }

    @Override
    public int[] askCoordinatesTile() {
        System.out.println("Move the robber");
        System.out.println("Choose coordinates x of the tile");
        int x= Settings.chooseANumber(Board.getSizeT())-1;
        System.out.println("Choose coordinates y of the tile");
        int y= Settings.chooseANumber(Board.getSizeT())-1;
        return new int[]{x, y};
    }
}