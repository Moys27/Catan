package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** This class models the board
 * We chose to fix its dimension  with 16 tiles (instead of the 19s of the standard game board).
 */

public class Board {

    public final Tile[][] tiles;
    private Structure [][] structures = new Structure[5][5];
    private Road [][]  verticalRoads = new Road[5][4];
    private Road [][] horizontalRoads = new Road[4][5];



    public Board (){
        tiles = new Tile [4][4];
        initializeTiles();
    }

    public Road[][] getHorizontalRoads() {
        return horizontalRoads;
    }

    public Road[][] getVerticalRoads() {
        return verticalRoads;
    }

    public Structure[][] getStructures() {
        return structures;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * We distribute the resources and generate Id for each tiles
     */
    private void initializeTiles(){
        List<Integer> resourcesList=new ArrayList<>();
        List<Integer> id = this.generateId();

        /**
         * Repartition of the land type as below:
         * 1 desert unproductive (id = 0)
         * 3 hills produce brick (id = 1)
         * 3 mountains produce ore (id = 5)
         * 3 fields produce grain (id = 2)
         * 3 pastures produce wool (id = 3 )
         * 3 forests produce lumber  (id = 4)
         */
        for (int i =0 ; i <16; i++){
            if(i == 0) resourcesList.add(0);
            else if(i<4) resourcesList.add(ResourceCard.brick);
            else if(i<7) resourcesList.add(ResourceCard.ore);
            else if(i<10) resourcesList.add(ResourceCard.grain);
            else if(i<13) resourcesList.add(ResourceCard.wool);
            else resourcesList.add(ResourceCard.lumber);
        }

        Collections.shuffle(resourcesList);
        /*
         * Tiles initialization
         */
        int k = 0;
        for (int i = 0; i<tiles.length; i++){
            for(int j=0; j< tiles[i].length;j++){
                int resource = resourcesList.get(k);
                int idTiles= (resource == 0 ? 0 : id.get(k));
                k++;
                tiles[i][j]=new Tile(idTiles, resource);
            }
        }

    }

    /**
     * This method allowed to generate the Tile's id according to the rule of the game.
     */
    private List<Integer> generateId(){
        List<Integer> id = new ArrayList<>();
        for (int i =0 ; i <15; i++){
            if(i==0) id.add(2);
            else if(i==1) id.add(3);
            else if(i==2) id.add(4);
            else if(i<=4) id.add(5);
            else if(i<=6) id.add(6);
            else if(i<=8) id.add(8);
            else if(i<=10) id.add(9);
            else if(i<=12) id.add(10);
            else if(i==13) id.add(11);
            else id.add(12);
        }
        id.add(0);
        Collections.shuffle(id);
        return id;
    }

    /**
     * Proceeds to the placement of structures
     * */
    public void placeStructures(Location loc, Structure s){
        if(s.getOwner().canBuild(s,loc)){
            this.structures[loc.getX()][loc.getY()] = s;
        }else{
        }
    }



    /**
     * Distributes resources to all Players with a Structure bordering Tiles with number roll
     * @param roll the value of the Tiles that have produced
     */
    public void distributeResources(int roll) {

        ArrayList<Tile> rollTiles = getTilesWithId(roll);

        for (Tile t : rollTiles) {
            if (t.hasRobber() || t.getId()==0) {
                continue;
            }

            ArrayList<Structure> rollStructures = new ArrayList<Structure>();

            for (Map.Entry structure : t.structureMap.entrySet()){
                rollStructures.add((Structure) structure);
            }

            for (Structure s : rollStructures) {
                if (null != s.getOwner())
                    s.winResources(t.getResource());
            }
        }
    }
    /**
     * Searches the Board for any Tiles with the value of the param and returns an ArrayList of them
     * @param id the roll number to be found on the Tile
     * @return an ArrayList of found Tiles
     */
    private ArrayList<Tile> getTilesWithId(int id) {

        ArrayList<Tile> rollTiles = new ArrayList<Tile>();

        for (int i = 1; i < tiles.length; i++) {
            for (int j = 1; j < tiles[i].length; j++) {
                if (tiles[i][j].getId() == id)
                    rollTiles.add(tiles[i][j]);
            }
        }
        return rollTiles;
    }

    /**
     * Gives the tiles adjacent to the given StructureLocation
     * @param loc location being checked
     * @return ArrayList<Tile> list of adjacent tiles
     */
    public ArrayList<Tile> getAdjacentTilesStructure(Location loc) {
        ArrayList<Tile> output = new ArrayList<Tile>();
        if (loc.isaNode()){
            if(isAValidLocation(loc.getX()-1, loc.getY()-1,4,4))
                output.add(tiles[loc.getX()-1][loc.getY()-1]);

            if(isAValidLocation(loc.getX()-1, loc.getY(),4,4))
                output.add(tiles[loc.getX()-1][loc.getY()]);

            if(isAValidLocation(loc.getX(), loc.getY()-1,4,4))
                output.add(tiles[loc.getX()][loc.getY()-1]);

            if(isAValidLocation(loc.getX(), loc.getY(),4,4))
                output.add(tiles[loc.getX()][loc.getY()]);
        }
        return output;
    } //todo first resources distribution at the beginning of the game

    private boolean isAValidLocation(int x, int y,int xBounds, int yBounds){
        return (x<xBounds && x>=0 && y<yBounds && y>=0);
    }

    /**
     * Gives the roads adjacent to the given location
     * @param loc location being checked
     * @return ArrayList<Road> list of adjacent roads
     */

    public ArrayList<Road> getAdjacentRoads(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        ArrayList<Road> output = new ArrayList<Road>();
        if (loc.isaNode()) {
            if(isAValidLocation(x-1, y,4,5))
                output.add(verticalRoads[x-1][y]);

            if(isAValidLocation(x, y,4,5))
                output.add(verticalRoads[x][y]);

            if(isAValidLocation(x, y-1,5,4))
                output.add(horizontalRoads[x][y-1]);

            if(isAValidLocation(x, y,5,4))
                output.add(horizontalRoads[x][y]);
        }else{

            if (loc.getOrientation() == 0){
                if(isAValidLocation(x-1, y,4,5))
                    output.add(verticalRoads[x-1][y]);

                if(isAValidLocation(x-1, y+1,4,5))
                    output.add(verticalRoads[x-1][y+1]);

                if(isAValidLocation(x, y,4,5))
                    output.add(verticalRoads[x][y]);

                if(isAValidLocation(x, y+1,4,5))
                    output.add(verticalRoads[x][y+1]);

                if(isAValidLocation(x, y-1,5,4))
                    output.add(horizontalRoads[x][y-1]);

                if(isAValidLocation(x, y+1,5,4))
                    output.add(horizontalRoads[x][y+1]);
            }
            else {
                if(isAValidLocation(x-1, y,4,5))
                    output.add(verticalRoads[x-1][y]);

                if(isAValidLocation(x+1, y,4,5))
                    output.add(verticalRoads[x+1][y]);

                if(isAValidLocation(x, y-1,5,4))
                    output.add(horizontalRoads[x][y-1]);

                if(isAValidLocation(x, y,5,4))
                    output.add(horizontalRoads[x][y]);

                if(isAValidLocation(x+1, y-1,5,4))
                    output.add(horizontalRoads[x+1][y-1]);

                if(isAValidLocation(x+1, y,5,4))
                    output.add(horizontalRoads[x+1][y]);

            }
        }
        return output;
    }

    /**
     * Counts the number of roads built from a given @param loc
     */
    public int countRoadsFromLocation(Location loc, Player player){

        ArrayList<Road> adjacentRoads = this.getAdjacentRoads(loc);
        List<Integer> roads = new ArrayList<>();
        for (Road r : adjacentRoads){
            if(r.getOwner() == player)  roads.add(1 + countRoadsFromLocation(r.getLocation(),player));
        }
        int max=0;
        if(!roads.isEmpty()){
            for (Integer i : roads) {
                if(max<i) max = i;
            }
        }
        return max;
    }


}
