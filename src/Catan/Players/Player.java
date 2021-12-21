package Catan.Players;

import java.util.HashMap;
import java.util.Map;
import Catan.Board.*;
import Catan.Card.*;
import Catan.Run.*;

public abstract class Player{
    public final String name;
    private int victoryPoints;
    private int nbRoadsAllowed = 15;
    private int nbSettlementsAllowed = 5;
    private int nbCitiesAllowed = 4;

    private Map<Location,Road> roadsMap = new HashMap<>();
    private Map<Location,Structure> structureMap=new HashMap<>();
    private Map<String, Integer> resourceDeck = new HashMap<String, Integer>();
    private Map<String, DevCard> hand = new HashMap<>();
    Map<String,Integer> price= new HashMap<>();

    public Player(String n){
        name =n;
        this.victoryPoints=0;
        initializeResourceDeck();
        initializePrice();
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     *
     */
    private void initializeResourceDeck(){
            resourceDeck.put(ResourceCard.Brick,0);
            resourceDeck.put(ResourceCard.Grain,0);
            resourceDeck.put(ResourceCard.Wool,0);
            resourceDeck.put(ResourceCard.Lumber,0);
            resourceDeck.put(ResourceCard.Ore,0);
    }


    private void initializePrice(){
        price.put(ResourceCard.Brick,4);
        price.put(ResourceCard.Grain,4);
        price.put(ResourceCard.Wool,4);
        price.put(ResourceCard.Lumber,4);
        price.put(ResourceCard.Ore,4);
    }

    /**
     *
     * @param resourceCard
     * @param number
     */

    public void winResource(String resourceCard, int number){
        int actualValue = resourceDeck.get(resourceCard);
        resourceDeck.replace(resourceCard,actualValue+number);
    }

    public void looseResource(String resourceCard, int number){
        int actualValue = resourceDeck.get(resourceCard);
        if (actualValue >= number) resourceDeck.replace(resourceCard,actualValue - number);
        else System.out.println("ERROR: Insufficient resources.");
    }


   /**
     * Checks if this Player has the specified resources
     * @param res the resources to check
     * @return whether the Player has those resources
     */
    boolean hasResources(HashMap<String, Integer> res) {
        int wool = 0,
                ore = 0,
                lumber = 0,
                brick = 0,
                grain = 0;

        for (Map.Entry resType : res.entrySet()) {
            if (resType.getKey().equals("wool")) {
                wool = (int) resType.getValue();
            } else if (resType.getKey().equals("ore"))
                ore= (int) resType.getValue();
            else if (resType.getKey().equals("lumber"))
                lumber= (int) resType.getValue();
            else if (resType.getKey().equals("brick"))
                brick= (int) resType.getValue();
            else if (resType.getKey().equals("grain"))
                grain= (int) resType.getValue();
        }

        if (wool > resourceDeck.get("wool") || ore > resourceDeck.get("ore") || lumber > resourceDeck.get("lumber") || brick > resourceDeck.get("brick") || grain > resourceDeck.get("grain"))
            return false;
        else
            return true;
    }
    /**
     * Check if the location is valid :
     *       if there are roads which reach the location
     *       no structure from other player is already settled
     *       if (it's a city) there's already a settlement there
     * if resources are sufficient
     * if the number of structures allowed hasn't been reached yet
     * */


    /**
     * Check if the player has enough resources to build a road and if they're allowed to do it
     * It's the same thing for canBuildSettlements and canBuildCity
     * */
    public boolean canBuildRoad(){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put("brick",1);
        resNeeded.put("lumber",1);
        return (nbRoadsAllowed!=0 && hasResources(resNeeded));
    }


    /**
     * @return true if the player can build a road at the given location
     */
    public boolean checkLocationForRoad(Board board, Location location){
        return board.isValidLocation(location)
                && board.checkRoadAt(location)==null &&
                (board.haveAdjacentRoads(location,this) || board.haveAdjacentStructures(location,this));
    }

    /**
     * @return true if all the conditions to build a road are fulfilled.
     */
    public boolean canBuildRoadAt(Board board, Location location){
        return (canBuildRoad() && checkLocationForRoad(board,location));
    }

    /**
     * Proceeds to the construction of the road
     * @param b the board related to the game
     * @param location to place the newly built road
     * @return the new road
     */

    public Road buildRoad(Board b, Location location){
        looseResource("brick",1);
        looseResource("lumber",1);
        Road road = new Road(location,this);
        roadsMap.put(location,road);
        nbRoadsAllowed--;
        return road;
    }

    /**
     * @return if all the conditions to build a settlement at a given location are fullfilled.
     */
    public boolean canBuildSettlementAt(Board board, Location location){
        return (canBuildSettlement() && checkLocationForSettlement(board,location));
    }

    /**
     * Check if the player have the resources needed to build a settlement
     * @return if all resources needed are in the player's deck
     */
    public boolean canBuildSettlement(){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put("brick", 1);
        resNeeded.put("lumber", 1);
        resNeeded.put("grain", 1);
        resNeeded.put("wool", 1);
        return (nbSettlementsAllowed!=0 && hasResources(resNeeded));
    }

    /**
     * Check if the player can place a settlement at the given location
     * @param board
     * @param location
     * @return if conditions related the location of an eventual settlement construction are fullfilled
     */
    public boolean checkLocationForSettlement(Board board, Location location){
        return  board.isValidLocation(location)
                && board.haveAdjacentRoads(location,this)
                && !board.haveAdjacentStructures(location, this);
    }

    /**
     * Creates the Settlement
     * @param b
     * @param location
     * @return the built settlement
     */
    public Structure buildSettlement(Board b, Location location){
        if(canBuildSettlementAt(b,location)){
               looseResource(ResourceCard.Brick, 1);
               looseResource(ResourceCard.Lumber, 1);
               looseResource(ResourceCard.Grain, 1);
               looseResource(ResourceCard.Wool, 1);
               Structure settlement = new Settlement(this, location);
               nbSettlementsAllowed--;
               structureMap.put(location,settlement);
               winVictoryPoint(1);
               return settlement;
       }
        return null;
    }

    /**
     * @param b the board
     * @param location
     * @return if there's a settlement in a given location
     */
    private boolean haveAntecedentSettlement(Board b , Location location){
        if (b.getStructureAt(location) != null ){
            Structure s = b.getStructureAt(location);
            return (s instanceof Settlement && s.getOwner()==this);
        }
        return false;
    }

    /**
     * @return true if all the conditions to build a city are fulfilled.
     */
    public boolean canBuildCityAt(Board board, Location location){
        return canBuildRoad() && checkLocationForCity(board,location);
    }

    /**
     * Check if the player have the resources needed to build a city
     * @return if all resources needed are in the player's deck
     */
    public boolean canBuildCity(){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put(ResourceCard.Grain,2);
        resNeeded.put(ResourceCard.Ore,3);
        return (nbCitiesAllowed!=0 && hasResources(resNeeded)
        );
    }

    /**
     * Check if the player can place a settlement at the given locatio
     * @return if conditions related the location of an eventual city construction are fullfilled
     */
    public boolean checkLocationForCity(Board b, Location location){
        return b.haveAdjacentRoads(location,this)
                && haveAntecedentSettlement(b,location);
    }

    /**
     * Creates the city
     * @return the built city
     */
    public Structure buildCity(Board b, Location location){
        if(canBuildCityAt(b,location)){
            looseResource(ResourceCard.Grain,2);
            looseResource(ResourceCard.Ore,3);
            Structure city= new City(this,location);
            structureMap.replace(location,city); //au lieu de put
            winVictoryPoint(2);
            nbCitiesAllowed--;
            return city;
        }
        return null;
    }

    /**
     * @return if the player can build a development card
     */
    public boolean canBuyDevCard(){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put(ResourceCard.Brick,1);
        resNeeded.put(ResourceCard.Ore,1);
        resNeeded.put(ResourceCard.Wool,1);
        return hasResources((HashMap<String, Integer>) resNeeded);
    }


    public void buyDevCard(Deck d){
        if(canBuyDevCard()){
            DevCard card = d.dealACard();
            if (card != null) {
                looseResource(ResourceCard.Grain,1);
                looseResource(ResourceCard.Ore,1);
                looseResource(ResourceCard.Wool,1);
                if(card.type==DevCard.victoryPoint) winVictoryPoint(1);
                hand.put(card.toString(), card);
            }
        }
    }

    /**
     * Adds i victory points to the player's score
     */
    public void winVictoryPoint(int i){
        this.victoryPoints+=i;
    }

    /**
     * Removes i victory points to the player's score
     */
    public  void looseVictoryPoint(int i){
        this.victoryPoints-=i;
    }

    /**
     * Places first roads at the beginning of the game
     */
    public abstract void placeFirstRoad(Board b);

    /**
     * Places first settlements at the beginning of the game
     * @param b1 flag when the player win his firt resources
     */
    public abstract  void placeFirstSettlement(Board b, boolean b1);


    public abstract  void askAction(Board board, Deck d);

    /**
     *
     * @param option
     * @param board
     * @param d
     */
    public void executeAction(int option, Board board, Deck d){
        Location location;
        switch (option){
            case 1 :
                location = Settings.askLocation();
                if(canBuildRoadAt(board,location)){
                    board.placeRoad(buildRoad(board,location));
                }
                askAction(board,d);

            case 2:
                location = Settings.askLocation();
                if(canBuildSettlementAt(board,location)){
                    board.placeStructure(buildSettlement(board,location));
                }
                askAction(board,d);

            case 3:
                location=Settings.askLocation();
                if(canBuildCityAt(board,location)){
                    buildCity(board,location);
                }
                askAction(board,d);

            case 4:
                if(canBuyDevCard()){
                    buyDevCard(d);
                }
                askAction(board,d);

            case 5: commerce();
                    askAction(board,d);
            case 6:
                break;
        }
    }

    public abstract String resourceWanted();
    public abstract String resourceExchanged(String wanted);

    /**
     *
     * @param rW is the resourceExchanged, rW resourceWarned
     * @return
     */
    public boolean canPayPrice(String rE, String rW){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put(rE, price.get(rW));
        return (hasResources(resNeeded));
    }


    private void doCommerce(String resourceWanted, String resourceExchanged){
        looseResource(resourceExchanged,price.get(resourceWanted));
        winResource(resourceWanted,1);

    }

    private void commerce(){
        String resourceWanted= resourceWanted();
        if (resourceWanted==null){
            return;
        }
        String resourceExchaged = resourceExchanged(resourceWanted);
        if (resourceExchaged!=null) {
            doCommerce(resourceWanted,resourceExchaged);
        } else{
            System.out.println("Error: Can not do the trade");
        }
    }

        //todo réfléchir sur suggestedLocationRoads(Road); suggestedLocationStructures()
}