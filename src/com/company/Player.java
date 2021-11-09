package com.company;

import java.util.HashMap;

public abstract class Player{
    private final String name;
    private int score;
    private int nbRoadsAllowed = 15;
    private int nbSettlementsAllowed = 5;
    private int nbCitiesAllowed = 4;

    private HashMap<Location,Road> roadsMap = new HashMap<>();
    private HashMap<Location,Structure> structureMap=new HashMap<>();
    private HashMap<ResourceCard, Integer> resourceDeck = new HashMap<ResourceCard, Integer>();

    public Player(String n){
        name=n;
        score=0;
        initializeResourceDeck();
    }

    private void initializeResourceDeck(){
            resourceDeck.put(new ResourceCard (ResourceCard.brick),0);
            resourceDeck.put(new ResourceCard(ResourceCard.grain),0);
            resourceDeck.put(new ResourceCard(ResourceCard.wool),0);
            resourceDeck.put(new ResourceCard(ResourceCard.lumber),0);
            resourceDeck.put(new ResourceCard(ResourceCard.ore),0);
    }

    public void winResource(ResourceCard resourceCard){
        int actualValue = resourceDeck.get(resourceCard);
        resourceDeck.replace(resourceCard,++actualValue);
    }

    public void looseResource(ResourceCard resourceCard){
        int actualValue = resourceDeck.get(resourceCard);
        if (actualValue > 0) resourceDeck.replace(resourceCard,--actualValue);
        else System.out.println("ERROR: Insufficient resources.");
    }

    public abstract void buildRoad();
    public abstract void buildStructure();



}