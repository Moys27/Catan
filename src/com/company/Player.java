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
    private HashMap<ResourceCard, Integer> resourceDeck = new HashMap<ResourceCard, Integer>();

    public Player(String n){
        name =n;
        this.victoryPoints=0;
        initializeResourceDeck();
    }

    private void initializeResourceDeck(){
            resourceDeck.put(new ResourceCard (ResourceCard.brick),0);
            resourceDeck.put(new ResourceCard(ResourceCard.grain),0);
            resourceDeck.put(new ResourceCard(ResourceCard.wool),0);
            resourceDeck.put(new ResourceCard(ResourceCard.lumber),0);
            resourceDeck.put(new ResourceCard(ResourceCard.ore),0);
    }

    public void winResource(ResourceCard resourceCard, int number){
        int actualValue = resourceDeck.get(resourceCard);
        resourceDeck.replace(resourceCard,actualValue+number);
    }

    public void looseResource(ResourceCard resourceCard, int number){
        int actualValue = resourceDeck.get(resourceCard);
        if (actualValue >= number) resourceDeck.replace(resourceCard,actualValue - number);
        else System.out.println("ERROR: Insufficient resources.");
    }

    public abstract void buildRoad();
    public abstract void buildStructure();


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