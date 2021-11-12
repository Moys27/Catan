package com.company;

public class City extends Settlement implements Structure{

    public City(Player p, Location l) {
        super(p, l);
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
    public void winResources(int resource) {
        this.getOwner().winResource(new ResourceCard(resource),2);
    }
}