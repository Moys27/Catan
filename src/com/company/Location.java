package com.company;

public class Location {
    /**
     * @param x
     * @param y are the structure's coordinates in the board
     * @param node stands for either it's located in the vertex (for structures)
     * or in the edge (for roads)
     * @param orientation defines either the item is on the vertical axis or the horizontal one
     *                    it is used to distinguish roads, 0 for horizontal ones, 1 for vertical and -1 for structure
     * */
    private int x, y ;
    private boolean node ;
    private int orientation;

    public Location(int x, int y, boolean node){
        this.x = x;
        this.y=y;
        this.node=node;
        this.orientation= -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @return if the item is on the edge or on the vertex of the tile
     * */
    public boolean isaNode() {
        return node;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }
}