package com.company;

import java.util.HashMap;
import java.util.Map;

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

    public Player(String n){
        name =n;
        this.victoryPoints=0;
        initializeResourceDeck();
    }

    private void initializeResourceDeck(){
            resourceDeck.put("brick",0);
            resourceDeck.put("grain",0);
            resourceDeck.put("wool",0);
            resourceDeck.put("lumber",0);
            resourceDeck.put("ore",0);
    }

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
    private boolean hasResources(HashMap<String, Integer> res) {
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
     * @return true if all the conditions for building a road are fulfilled.
     */
    public boolean canBuildRoadAt(Board board, Location location){
        return (canBuildRoad() && checkLocationForRoad(board,location));
    }

    /**
     * Proceeds to the construction of the road
     */

    public Road buildRoad(Board b, Location location){
        looseResource("brick",1);
        looseResource("lumber",1);
        Road road = new Road(location,this);
        roadsMap.put(location,road);
        nbRoadsAllowed--;
        return road;
    }
    public boolean canBuildSettlementAt(Board board, Location location){
        return (canBuildSettlement() && checkLocationForSettlement(board,location));
    }

    public boolean canBuildSettlement(){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put("brick", 1);
        resNeeded.put("lumber", 1);
        resNeeded.put("grain", 1);
        resNeeded.put("wool", 1);
        return (nbSettlementsAllowed!=0 && hasResources(resNeeded));
    }

    public boolean checkLocationForSettlement(Board board, Location location){
        return  board.isValidLocation(location)
                && board.haveAdjacentRoads(location,this)
                && !board.haveAdjacentStructures(location, this);
    }

    public Structure buildSettlement(Board b, Location location){
        if(canBuildSettlementAt(b,location)){
               looseResource("brick", 1);
               looseResource("lumber", 1);
               looseResource("grain", 1);
               looseResource("wool", 1);
               Structure settlement = new Settlement(this, location);
               nbSettlementsAllowed--;
               structureMap.put(location,settlement);
               winVictoryPoint(1);
               return settlement;
       }
        return null;
    }

    private boolean haveAntecedentSettlement(Board b , Location location){
        if (b.getStructureAt(location) != null ){
            Structure s = b.getStructureAt(location);
            return (s instanceof Settlement && s.getOwner()==this);
        }
        return false;
    }

    public boolean canBuildCityAt(Board board, Location location){
        return canBuildRoad() && checkLocationForCity(board,location);
    }
    public boolean canBuildCity(){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put("grain",2);
        resNeeded.put("ore",3);
        return (nbCitiesAllowed!=0 && hasResources(resNeeded)
        );
    }
    public boolean checkLocationForCity(Board b, Location location){
        return b.haveAdjacentRoads(location,this)
                && haveAntecedentSettlement(b,location);
    }
    public Structure buildCity(Board b, Location location){
        if(canBuildCityAt(b,location)){
            looseResource("grain",2);
            looseResource("ore",3);
            Structure city= new City(this,location);
            structureMap.replace(location,city); //au lieu de put
            winVictoryPoint(2);
            nbCitiesAllowed--;
            return city;
        }
        return null;
    }

    public boolean canBuyDevCard(){
        HashMap<String, Integer> resNeeded = new HashMap<>();
        resNeeded.put("grain",1);
        resNeeded.put("ore",1);
        resNeeded.put("wool",1);
        return hasResources((HashMap<String, Integer>) resNeeded);
    }

    public void buyDevCard(Deck d){
        if(canBuyDevCard()){
            DevCard card = d.dealACard();
            if (card != null) {
                looseResource("grain",1);
                looseResource("ore",1);
                looseResource("wool",1);
                if(card.type==DevCard.victoryPoint) winVictoryPoint(1);
                hand.put(card.toString(), card);
            }
        }
    }
    public void winVictoryPoint(int i){
        this.victoryPoints+=i;
    }

    public  void looseVictoryPoint(int i){
        this.victoryPoints-=i;
    }


    public abstract void placeFirstRoad(Board b);
    public abstract  void placeFirstSettlement(Board b, boolean b1);
    public abstract  void askAction(Board board, Deck d);

    public void executeAction(int option, Board board, Deck d){
        Location location;
        switch (option){
            case 1 :
                location = Settings.askLocation();
                if(canBuildRoadAt(board,location)){
                    board.placeRoad(buildRoad(board,location));
                }
                break;
            case 2:
                location = Settings.askLocation();
                if(canBuildSettlementAt(board,location)){
                    board.placeStructure(buildSettlement(board,location));
                }
                break;
            case 3:
                location=Settings.askLocation();
                if(canBuildCityAt(board,location)){
                    buildCity(board,location);
                }
                break;
            case 4:
                if(canBuyDevCard()){
                    buyDevCard(d);
                }
                break;
            case 5: //todo trade avec le port tout simplement
                break;
            case 6:
                break;

        }
    }
}