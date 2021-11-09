package com.company;

import java.util.HashMap;
import java.util.Map;

public class Tile{

    public final int resource;
    public final int id;
    private boolean hasRobber;
    public Map<Location, Road> roadMap = new HashMap<Location, Road>() ;
    public Map<Location, Structure> structureMap = new HashMap<Location, Structure>();


    public Tile(int id, int resource){
        this.id=id;
        this.resource = resource;
        hasRobber=false;
    }


}