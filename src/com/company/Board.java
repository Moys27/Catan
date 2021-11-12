package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** This class models the board
 * We chose to fix its dimension  with 20 tiles (instead of the 19s of the standard game board).
 */

public class Board {

    private final Tile[][] tiles;
    private Map<Integer,Tile> tilesMap;


    public Board (){
        tiles = new Tile [5][4];
        initializeTiles();
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
         * 4 fields produce grain (id = 2)
         * 4 pastures produce wool (id = 3 )
         * 5 forests produce lumber  (id = 4)
         */
        for (int i =0 ; i <20; i++){
            if(i == 0) resourcesList.add(0);
            else if(i<3) resourcesList.add(ResourceCard.brick);
            else if(i<6) resourcesList.add(ResourceCard.ore);
            else if(i<10) resourcesList.add(ResourceCard.grain);
            else if(i<14) resourcesList.add(ResourceCard.wool);
            else resourcesList.add(ResourceCard.lumber);
        }

        Collections.shuffle(resourcesList);
        /*
         * Tiles initialization
         */
        for (int i = 0; i<tiles.length; i++){
            for(int j=0; j< tiles[i].length;j++){
                int resource = resourcesList.get(i+j);
                int idTiles= id.get(i+j);
                tiles[i][j]=new Tile(idTiles, resource);
            }
        }

    }

    /**
     * This method allowed to generate the Tile's id according to the rule of the game.
     */
    private List<Integer> generateId(){
        List<Integer> id = new ArrayList<>();
        for (int i =0 ; i <20; i++){
            if(i == 0) id.add(0);
            else if(i<2) id.add(2);
            else if(i<4) id.add(3);
            else if(i<6) id.add(4);
            else if(i<8) id.add(5);
            else if(i<10) id.add(6);
            else if(i<12) id.add(8);
            else if(i<14) id.add(9);
            else if(i<16) id.add(10);
            else if(i<18) id.add(11);
            else id.add(12);
        }
        Collections.shuffle(id);
        return id;
    }
}
