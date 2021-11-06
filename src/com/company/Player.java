package com.company;

public abstract class Player{
    private static int nbPlayers;
    private final String name;
    private int score;
    private int[] deckResource;

    public Player(String n){
        name=n;
        score=0;
        deckResource= new int[5];
        nbPlayers++;
    }
}