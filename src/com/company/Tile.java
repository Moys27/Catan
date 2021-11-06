package com.company;

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

    public  Tile upRight;
    public  Tile downRight;
    public  Tile right;
    public Tile upLeft;
    public  Tile downLeft;
    public  Tile left;
    public  Tile [] adjacents;
    private Road[] roads = new Road[6];
    private Structure[] structures = new Structure[6];


    public Tile(int id, int resource, Tile upRight, Tile downRight, Tile right, Tile upLeft, Tile downLeft, Tile left){
        this.id=id;
        this.resource = resource;
        hasRobber=false;
        this.upRight=upRight;
        this.downRight=downRight;
        this.right=right;
        this.upLeft=upLeft;
        this.downLeft=downLeft;
        this.left=left;

        this.adjacents = new Tile []{this.upLeft,this.upRight,this.left,this.right,this.downLeft,this.downRight};
    }

    public Tile(int id, int resource){
        this(id, resource, null, null, null, null, null, null);
    }

    public boolean hasRobber() {
        return hasRobber;
    }
}