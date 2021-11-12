package com.company;

import java.util.HashMap;

public abstract class Player{
    public final String name;
    private int victoryPoints;
    private int nbRoadsAllowed = 15;
    private int nbSettlementsAllowed = 5;
    private int nbCitiesAllowed = 4;

    private HashMap<Location,Road> roadsMap = new HashMap<>();
    private HashMap<Location,Structure> structureMap=new HashMap<>();
    private HashMap<String, Integer> resourceDeck = new HashMap<String, Integer>();

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

    public void buildRoad(Location location){ //conditions a verifier, assez de ressources, assez de nbAllowed, validlocalisation
        looseResource("brick",1);
        looseResource("lumber",1);
        Road road = new Road(location,this);

    }
    public void buildSettlement(Location location){
        looseResource("brick",1);
        looseResource("lumber",1);
        looseResource("grain",1);
        looseResource("wool",1);
        Settlement settlement= new Settlement(this,location);
        winVictoryPoint(1);
    }

    public void buildCity(Location location){
        looseResource("grain",2);
        looseResource("ore",3);
        City city= new City(this,location);
        winVictoryPoint(2);
    }

    /* TODO
    public void buyDevCard(){
        looseResource("grain",1);
        looseResource("ore",1);
        looseResource("wool",1);
    }
    */



    public void winVictoryPoint(int i){
        this.victoryPoints+=i;
    }

    public  void looseVictoryPoint(int i){
        this.victoryPoints-=i;
    }

    public boolean canBuild(Structure s, Location loc) {
        return true; //todo
        /*
        * Check if the location is valid :
        *       if there are roads which reach the location
        *       no structure from other player is already settled
        *       if (it's a city) there's already a settlement there
         * if resources are sufficient
        * if the number of structures allowed hasn't been reached yet
        * */
    }
}