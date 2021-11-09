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
    }

    private void initializeResourceDeck(){

    }

}