package com.company;

public class Location {
    /**
     * @x and @y are the structure's coordinates in the board
     * @node stands for either it's located in the vertex (for structures)
     * or in the edge (for roads)
     * */
    private int x, y ;
    private boolean node ;
    public Location(int x, int y, boolean node){
        this.x = x;
        this.y=y;
        this.node=node;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isaNode() {
        return node;
    }
}