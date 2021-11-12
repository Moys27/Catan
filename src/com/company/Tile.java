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
        hasRobber=(id==0)? true : false;
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

    @Override
    public String toString() {
        return "Tile{" +
                "resource=" + resource +
                ", id=" + id +
                ", hasRobber=" + hasRobber +
                ", roadMap=" + roadMap +
                ", structureMap=" + structureMap +
                '}';
    }
}