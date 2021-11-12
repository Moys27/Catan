package com.company;

public abstract class Card {
    protected final int type;
    protected Player owner;
    public Card(int type){
        this.type=type;
        this.owner=null;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}