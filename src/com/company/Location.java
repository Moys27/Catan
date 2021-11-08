package com.company;

public class Location {
    private int x, y ;
    private int orientation ;
    public Location(int x, int y, int orientation){
        this.x = x;
        this.y=y;
        this.orientation=orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrientation() {
        return orientation;
    }
}