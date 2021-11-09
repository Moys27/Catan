package com.company;

public class Road {
    private final Location location;
    private final Player owner;

    public Road(Location location, Player owner){
        this.location=location;
        this.owner=owner;
    }

    public Player getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }
}