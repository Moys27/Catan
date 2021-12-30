package Catan.Players;

import java.lang.reflect.Array;
import java.util.*;

import java.util.ArrayList;
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
    private int nbRoads= 15-nbRoadsAllowed;
    private int nbKnigths=0;


    private Map<Location,Road> roadsMap = new HashMap<>();
    private Map<Location,Structure> structureMap=new HashMap<>();
    Map<String, Integer> resourceDeck = new HashMap<String, Integer>();
    ArrayList<DevCard> hand= new ArrayList<>();
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

    public int getNbKnigths() { return nbKnigths;    }

    public int getNbRoads() { return nbRoads;  }

    public Map<String, Integer> getResourceDeck() {
        return resourceDeck;
    }

    public Map<String, Integer> getPrice() {
        return price;
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
            if (resType.getKey().equals(ResourceCard.Wool)) {
                wool = (int) resType.getValue();
            } else if (resType.getKey().equals(ResourceCard.Ore))
                ore= (int) resType.getValue();
            else if (resType.getKey().equals(ResourceCard.Lumber))
                lumber= (int) resType.getValue();
            else if (resType.getKey().equals(ResourceCard.Brick))
                brick= (int) resType.getValue();
            else if (resType.getKey().equals(ResourceCard.Grain))
                grain= (int) resType.getValue();
        }

        if (wool > resourceDeck.get(ResourceCard.Wool) || ore > resourceDeck.get(ResourceCard.Ore)
                || lumber > resourceDeck.get(ResourceCard.Lumber) || brick > resourceDeck.get(ResourceCard.Brick) || grain > resourceDeck.get(ResourceCard.Grain))
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
        resNeeded.put(ResourceCard.Brick,1);
        resNeeded.put(ResourceCard.Lumber,1);
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
        looseResource(ResourceCard.Brick,1);
        looseResource(ResourceCard.Brick,1);
        Road road = new Road(location,this);
        roadsMap.put(location,road);
        nbRoadsAllowed--;
        return road;
    }

    public Road buildRoadFree(Board b, Location location){
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
        resNeeded.put(ResourceCard.Brick, 1);
        resNeeded.put(ResourceCard.Lumber, 1);
        resNeeded.put(ResourceCard.Grain, 1);
        resNeeded.put(ResourceCard.Wool, 1);
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
                if(card.type==DevCard.getVictoryPoint()) {
                    System.out.println(name+ " gain a "+ card.getTitle() +" card");
                    winVictoryPoint(1); //the win of points of victory are automatically
                    return;
                }
                hand.add(card);

                if (this instanceof HumanPlayer){
                    System.out.println(name+ " gain a "+ card.getTitle() +" card");
                }
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
                break;
            case 2:
                location = Settings.askLocation();
                if(canBuildSettlementAt(board,location)){
                    board.placeStructure(buildSettlement(board,location));
                }
                askAction(board,d);
                break;
            case 3:
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
                if(this instanceof HumanPlayer) System.out.println("Price: "+price);
                    commerce();
                    askAction(board,d);
                    break;
            case 7:
                next();
                break;
        }
    }

    public abstract String resourceWanted();
    public abstract String resourceExchanged(String wanted);

    /**
     *
     * @param rE is the resourceExchanged,
     * @param rW resourceWarned
     * @return
     */
    public boolean canPayPrice(String rE, String rW){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put(rE, price.get(rW));
        return (hasResources(resNeeded));
    }

    /**
     * @return a location where the player could place a road
     */
    public ArrayList<Location> suggestedLocationRoads(Board b){
        ArrayList<Location> output = new ArrayList<>();

        HashMap<Location, Road> allAdjRoads = new HashMap<>();

        /*We take all roads adjacent to all structures and roads that belong to the player*/
        for (Map.Entry structureOwned : structureMap.entrySet()){
            HashMap<Location,Road> adjRoads= b.getAdjacentRoads((Location) structureOwned.getKey());
            for (Map.Entry road : adjRoads.entrySet()){
                allAdjRoads.put((Location)road.getKey(),(Road)road.getValue());
            }
        }
        for (Map.Entry roadOwned : roadsMap.entrySet()){
            HashMap<Location,Road> adjRoads= b.getAdjacentRoads((Location) roadOwned.getKey());
            for (Map.Entry road : adjRoads.entrySet()){
                allAdjRoads.put((Location)road.getKey(),(Road)road.getValue());
            }
        }
        /*we return only locations that haven't any road in it.*/
        for (Map.Entry road : allAdjRoads.entrySet()){
            if((Road)road.getValue() == null) output.add((Location)road.getKey() );
        }
        return output;
    }

    /**
     * @return location where the player could place a structure
     */
   public ArrayList<Location> suggestedLocationStructures(Board b){
       ArrayList<Location> output = new ArrayList<>();

       HashMap<Location, Structure> allAdjStructures = new HashMap<>();

       /*We take all structures adjacent to all structures and roads that belong to the player*/
       for (Map.Entry structureOwned : structureMap.entrySet()){
           HashMap<Location,Structure> adjacentStructure = b.getAdjacentStructure((Location) structureOwned.getKey());
           for (Map.Entry structure : adjacentStructure.entrySet()){
               adjacentStructure.put((Location)structure.getKey(),(Structure) structure.getValue());
           }
       }
       for (Map.Entry roadOwned : roadsMap.entrySet()){
           HashMap<Location,Structure> adjacentStructure = b.getAdjacentStructure((Location) roadOwned.getKey());
           for (Map.Entry structure : adjacentStructure.entrySet()){
               adjacentStructure.put((Location)structure.getKey(),(Structure) structure.getValue());
           }
       }
       /*we return only locations that haven't any structures in it.*/
       for (Map.Entry structure : allAdjStructures.entrySet()){
           if((Structure)structure.getValue() == null) output.add((Location)structure.getKey() );
       }
       return output;
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

    /**
     * Fonction charged to reduce the price of the ressource when the player wants to commerce with the bank
     * @param specialisation
     */
    public void priceReduction(int specialisation){
        if (specialisation>=ResourceCard.ore){
            for (String s: price.keySet()){
                if (price.get(s)==4){
                    price.replace(s,3);
                }
            }
        } else {
            price.replace(ResourceCard.array[specialisation],2);
        }
    }


        //todo réfléchir sur suggestedLocationRoads(Road); suggestedLocationStructures()

    /**
     *
     */
    public void useDevCard(DevCard card,Board board){
        switch(card.getTitle()){
            case "Knights":
                GameRunner.useRobber(this, board);
                nbKnigths++;
                break;

            case "Monopoly":

                break;
            case "Year Of Plenty":
                useYearOfPlenty();
                break;
            case "Road Building":
                useRoadBuilding(board);
            break;
            default: break;
        }
    }

    /**
     * Use the develop card Road Building
     * @param board
     */
    private void useRoadBuilding(Board board) {
        for (int i = 0; i < 2; i++) {
            Location location = Settings.askLocation();
            if (checkLocationForRoad(board, location) && (nbRoadsAllowed != 0)) {
                board.placeRoad(buildRoadFree(board, location));
            }
        }
    }

    abstract void useYearOfPlenty();
    abstract void actionDevCard(Board board);


    /**
     * You can't use the DevCard (but victory point) in the same turn that you have buy it,
     * so this fonction change the state of DevCard and they can be used, this fonction is only call in the end of the turn of the player
     */

    public void next(){
        if(hand==null) return;
        for(DevCard card: hand){
            if (!card.getCanUSe()){
                card.canUse();
            }
        }
    }

    public int howManyCards(){
        int i= 0;
        for(String s: resourceDeck.keySet()){
            i= i+ resourceDeck.get(s);
        }
        return i;
    }

    public abstract void discartCards(int i);

    public void discartExtraCards(){
        int nbCards= howManyCards();
        if (nbCards>7){
            discartCards(nbCards/2);
        }
    }
    public List<Player> cleanStolenListPlayer(List<Player> players){
        if(players==null){
            return null;
        }
        List<Player> playerList= new ArrayList<>();
        for(Player p:players){
            if (!this.equals(p)){
                playerList.add(p);
            }
        }
        return playerList;
    }

    public abstract Player choosePlayerToStolen(List<Player> players);

    public void playerMoveRobber(){

    }

    public void stoleACardto(Player player) {
        Random r= new Random();
        if(player.howManyCards()==0){
            return;
        }
        String random= ResourceCard.array[r.nextInt(ResourceCard.ore)];
            if (resourceDeck.get(random)>0){
                player.looseResource(random,1);
                this.winResource(random,1);
            } else {
                stoleACardto(player);
        }
    }

    public abstract int[] askCoordinatesTile();

}