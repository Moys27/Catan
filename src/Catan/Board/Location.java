package Catan.Board;

import java.util.ArrayList;

public class Location {
    /**
     * @param x
     * @param y are the structure's coordinates in the board
     * @param node stands for either it's located in the vertex (for structures)
     * or in the edge (for roads)
     * @param orientation defines either the item is on the vertical axis or the horizontal one
     *                    it is used to distinguish roads, 0 for horizontal ones, 1 for vertical and 2 for structure
     * */
    private int x, y ;
    private boolean node ;
    private int orientation;

    public Location(int x, int y, int orientation){
        this.x = x;
        this.y=y;
        this.node= (orientation==2);
        this.orientation= orientation;
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


    public int getOrientation() {
        return orientation;
    }

    public boolean isCoast() {
        return (
                (x%Board.getSizeT()==0)||(y%Board.getSizeT()==0));
    }

    @Override
    public int hashCode(){
        return x+y+10*orientation;
    }

    @Override
    public boolean equals(Object loc){
        return (this.hashCode()==loc.hashCode());
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                '}';
    }
    public static boolean compareLocation(Location loc1, Location loc2){
        return (loc1.equals(loc2));
    }

    public static boolean init(ArrayList<Location> list, Location loc){
        for (Location item : list){
            if (loc.equals(item)) return true;
        }
        return false;
    }
}