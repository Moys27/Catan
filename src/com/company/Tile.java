package com.company;

import java.util.HashMap;
import java.util.Map;

public class Tile{
    /**
     * Description */
    static final int brick = 1;
    static final int grain = 2;
    static final int wool = 3;
    static final int lumber = 4;
    static final int ore = 5;


    public final int resource;
    public final int id;

    private boolean hasRobber;
    private Map<String, Tile> adjacents;

    public Tile(int id, int resource){
        this.id=id;
        this.resource = resource;
        hasRobber=false;
        adjacents= new HashMap<String,Tile>();
    }
    public boolean hasRobber() {
        return hasRobber;
    }
    public void setAdjacents(Tile topLeft,Tile topRight,Tile left, Tile right, Tile downLeft, Tile downRight){
        adjacents.put("topLeft",topLeft);
        adjacents.put("topRight",topRight);
        adjacents.put("left",left);
        adjacents.put("right",right);
        adjacents.put("downLeft",downLeft);
        adjacents.put("downRight",downRight);
    }
    public Map<String,Tile> getAdjacents(){ return adjacents;}

    public Tile getAdjacent(String side){
        return adjacents.get(side);
    }
}