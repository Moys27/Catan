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
        return this.location;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public boolean isBuildable() {//todo
        return false;
    }

    @Override
    public boolean hasNecessaryResources(Player p) {
        return false; //todo
    }

    @Override
    public void winResources(String resource) {
        this.getOwner().winResource(resource,1);
    }
}