package Catan.Run;

import Catan.Board.*;
import Catan.Players.HumanPlayer;
import Catan.Players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        /*Board b = new Board();

        int [] t = b.initialisePorts();
        for(int i : t) System.out.print(i+" ");
        System.out.println();


        for (Map.Entry a : Board.Ports.entrySet()){
            System.out.print(a.getKey()+"  : " + a.getValue()+"\n");
            //System.out.println(Board.getSpecialisation(new Location (4,2,2)));
        }

        System.out.println(Board.getSpecialisation(new Location(0,0,0)));*/

        testSuggestions();

        //testAdjacent();
    }

    public static void testSuggestions(){

        /*for (Player p : g.allPlayers){
            for(Map.Entry roads : p.roadsMap.entrySet()){
                System.out.println(roads.getKey()+"  :  "+roads.getValue());
            }
            for(Map.Entry struc : p.structureMap.entrySet()){
                System.out.println(struc.getKey()+"  :  "+struc.getValue());
            }
        }*/

        Board b = new Board();
        Player p = new HumanPlayer();

        Location location = new Location(0,0,2);
        Structure settlement = new Settlement(p, location);
        p.structureMap.put(location,settlement);
        b.placeStructure(settlement);

        Location location2 = new Location(0,1,2);
        Structure settlement2 = new Settlement(p, location2);
        p.structureMap.put(location2,settlement2);
        b.placeStructure(settlement2);

        Location location3 = new Location(0,0,0);
        Road road = new Road(location3, p);
        System.out.println(location3 + "  : "+road);
        p.roadsMap.put(location3,road);
        System.out.println(b.placeRoad(road));

        Location location4 = new Location(0,0,1);
        Road road2 = new Road(location4, p);
        System.out.println(location4+"  : "  +road2);
        p.roadsMap.put(location4,road2);
        System.out.println(b.placeRoad(road2));

        p.showSuggestedLocationStructure(b);
        //p.showSuggestedLocationRoads(b);


    }

    public static void testAdjacent(){
        Board b = new Board();
        Player p = new HumanPlayer();

        Location location = new Location(0,0,2);
        Structure settlement = new Settlement(p, location);
        p.structureMap.put(location,settlement);
        b.placeStructure(settlement);

        Location location2 = new Location(0,1,2);
        Structure settlement2 = new Settlement(p, location2);
        p.structureMap.put(location2,settlement2);
        b.placeStructure(settlement2);

        Location location3 = new Location(0,0,0);
        Road road = new Road(location3, p);
        System.out.println(location3 + "  : "+road);
        p.roadsMap.put(location3,road);
        System.out.println(b.placeRoad(road));

        Location location4 = new Location(0,0,1);
        Road road2 = new Road(location4, p);
        System.out.println(location4+"  : "  +road2);
        p.roadsMap.put(location4,road2);
        System.out.println(b.placeRoad(road2));



        /*for (var str : b.getStructures()){
            for(var s : str) System.out.println(s);
        }*/

        System.out.println("HORIZONTAL ROADS:");
        for(var rd : b.getHorizontalRoads()){
            for(var r: rd) System.out.print(r+"\t");
            System.out.println();
        }
        System.out.println("VERTICAL ROADS:");
        for(var rd : b.getVerticalRoads()){
            for(var r: rd) System.out.print(r+"\t");
            System.out.println();
        }



        for(Map.Entry struc : p.structureMap.entrySet()){
            System.out.println("THE STRUCTURE : "+struc.getKey()+"  :  "+struc.getValue());

            //adjacent structures : OK
           /* HashMap<Location,Structure> adjStructure= b.getAdjacentStructure((Location) struc.getKey());
            for(Map.Entry var : adjStructure.entrySet()) System.out.println(var.getKey()+ " : "+var.getValue());
            */

            //adjacent tiles : OK
            /*ArrayList<Tile> tiles = b.getAdjacentTilesStructure((Location) struc.getKey());
            for(Tile var : tiles) System.out.println(var);*/


            System.out.println("FIN.\n");

        }
    }


}
