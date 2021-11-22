package com.company;

public class City extends Settlement implements Structure{

    public City(Player p, Location l) {
        super(p, l);
    }

    @Override
    public void winResources(String resource) {
        this.getOwner().winResource(resource,2);
    }
}