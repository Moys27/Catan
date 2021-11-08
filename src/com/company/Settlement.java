package com.company;

import java.util.Set;

public class Settlement implements Structure{
    private Player owner;
    private Location location;

    public Settlement(Player p, Location l){
        this.owner=p;
        this.location=l;
    }
    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Player getOwner() {
        return null;
    }
}