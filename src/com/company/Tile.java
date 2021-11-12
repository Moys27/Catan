package com.company;

import java.util.HashMap;
import java.util.Map;

public class Tile{

    public final String resource;
    public final int id;
    private boolean hasRobber;
    public Map<Location, Road> roadMap = new HashMap<Location, Road>() ;
    public Map<Location, Structure> structureMap = new HashMap<Location, Structure>();


    public Tile(int id, String resource){
        this.id=id;
        this.resource = resource;
        hasRobber=false;
    }

    public int getId() {
        return id;
    }

    public String getResource() {
        return resource;
    }

    public boolean hasRobber() {
        return hasRobber;
    }
}