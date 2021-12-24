package Catan.Board;

import java.util.*;
import Catan.Players.*;

/** This class models the board
 * We chose to fix its dimension  with 16 tiles (instead of the 19s of the standard game board).
 */

public class Board {

    public final Tile[][] tiles;
    private static final int sizeT= 4;

    private static final int sizeS= 5;
    private static final int numberPort= 8;

    private Structure [][] structures = new Structure[sizeS][sizeS];
    private Road [][]  verticalRoads = new Road[sizeS][sizeT];
    private Road [][] horizontalRoads = new Road[sizeT][sizeS];
    private static HashMap<Location, Integer> Ports= new HashMap<>();
    //todo: ajout de l'attribut des ports
    //todo créer un classe pour les ports


    public Board (){
        tiles = new Tile [sizeT][sizeT];
        initializeTiles();
        initailiseCoast();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public static int getSizeS() {
        return sizeS;
    }

    public static int getSizeT() {
        return sizeT;
    }

    public static int getSpecialisation(Location l){
        return Ports.get(l);
    }

    /**
     * We distribute the resources and generate Id for each tiles
     */
    private void initializeTiles(){
        List<String> resourcesList=new ArrayList<>();
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
        for (int i =0 ; i <15; i++){
            if(i<3) resourcesList.add("brick");
            else if(i<6) resourcesList.add("ore");
            else if(i<9) resourcesList.add("grain");
            else if(i<12) resourcesList.add("wool");
            else resourcesList.add("lumber");
        }
        resourcesList.add("desert");
        Collections.shuffle(resourcesList);

        /*
         * Tiles initialization
         */
        int k = 0; //to browse the ResourcesList
        int K=0; //to browse the IdList
        for (int i = 0; i<tiles.length; i++){
            for(int j=0; j< tiles[i].length;j++){
                String resource = resourcesList.get(k++);
                int idTiles= (resource.equals("desert") ? id.get(id.size()-1) : id.get(K++));
                tiles[i][j]=new Tile(idTiles, resource);
            }
        }

    }

    public int[] initailisePorts(){
        Random r= new Random();
        int random= r.nextInt(numberPort) +1;
        int [] ports= new int[numberPort];
        for (int i=0;i<numberPort;i++){
            ports[i]= (random+i)%numberPort;
            if((random+i)%numberPort==0){
                ports[i]= numberPort;
            }
        }
        return ports;
    }

    public void initailiseCoast() {
        int[] ports= initailisePorts();
        int a=0;
        for (int i=0; i<sizeS; i++){
            if(i==2) a++;
            Ports.put(new Location(i,0,-1),ports[a]);
            Ports.put(new Location(sizeT,i,-1),ports[a+2]);
            Ports.put(new Location(sizeT-i,sizeT,-1),ports[a+4]);
            Ports.put(new Location(0,sizeT-i,-1),ports[a+6]);
        }
        /*
            Ports.put(new Location(0,0,-1),ports[0]);
            Ports.put(new Location(1,0,-1),ports[0]);
            Ports.put(new Location(2,0,-1),ports[1]);
            Ports.put(new Location(3,0,-1),ports[1]);
            Ports.put(new Location(4,0,-1),ports[2]);
            Ports.put(new Location(4,1,-1),ports[2]);
            Ports.put(new Location(4,2,-1),ports[3]);
            Ports.put(new Location(4,3,-1),ports[3]);
            Ports.put(new Location(4,4,-1),ports[4]);
            Ports.put(new Location(3,4,-1),ports[4]);
            Ports.put(new Location(2,4,-1),ports[5]);
            Ports.put(new Location(1,4,-1),ports[5]);
            Ports.put(new Location(0,4,-1),ports[6]);
            Ports.put(new Location(0,3,-1),ports[6]);
            Ports.put(new Location(0,2,-1),ports[7]);
            Ports.put(new Location(0,1,-1),ports[7]);
         */
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
        Collections.shuffle(id);
        id.add(0);
        return id;
    }


    /**
     * Proceeds to the placement of structures
     * @returns if the structure was placed successfully
     * */
    public boolean placeStructure(Structure structure){
        //ajouter la structure dans tiles
        if(structure != null) {
            Location loc = structure.getLocation();
            if (structure instanceof Settlement){
                structures[loc.getX()][loc.getY()]=structure;

                    return true;
            }
            if (structure instanceof City){
                structures[loc.getX()][loc.getY()]=structure;
                    return true;
            }
        }
        return false;
    }

    /**
     * Proceeds to the placement of roads
     * @returns if the road was placed successfully
     */
    public boolean placeRoad(Road road){
        if (road != null){
            Location loc = road.getLocation();
            if(loc.getOrientation() == 0 ){
                horizontalRoads[loc.getX()][loc.getY()]=road;
                return true;
            }
            if (loc.getOrientation() == 1) {
                verticalRoads[loc.getX()][loc.getY()]=road;
                return true;
            }
        }
        return false;
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
        return output; //pour les routes
    } //todo first resources distribution at the beginning of the game

    private boolean isAValidLocation(int x, int y,int xBounds, int yBounds){
        return (x<xBounds && x>=0 && y<yBounds && y>=0);
    }


    /**
     * Gives the structures adjacent to the given location
     * @param loc location being checked
     * @return ArrayList<Structure> list of adjacent structures
     */
    public ArrayList<Structure> getAdjacentStructure(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        int o = loc.getOrientation();
        ArrayList<Structure> output = new ArrayList<>();
        if(loc.isaNode()){
            if(isAValidLocation(x-1, y,5,5))
                output.add(structures[x-1][y]);

            if(isAValidLocation(x+1, y,4,5))
                output.add(structures[x+1][y]);

            if(isAValidLocation(x, y-1,5,4))
                output.add(structures[x][y-1]);

            if(isAValidLocation(x, y+1,5,4))
                output.add(structures[x][y+1]);
        }else if (o == 0){
            if(isAValidLocation(x, y,5,5))
                output.add(structures[x][y]);

            if(isAValidLocation(x, y+1,5,5))
                output.add(structures[x][y+1]);
        }else if (o == 1){
            if(isAValidLocation(x, y,5,5))
                output.add(structures[x][y]);

            if(isAValidLocation(x+1, y,5,5))
                output.add(structures[x+1][y]);
        }
        return output;
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
     * @return  the number of roads built from a given @param loc
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

    /**
     * @return if the given location is valid according to either it's a road or a structure
     */
    public boolean isValidLocation(Location loc){
        if (loc.getOrientation() == -1){
            return (loc.getX()>=0 && loc.getX()<5 && loc.getX()>=0 && loc.getY()<5);
        }
        else if (loc.getOrientation() == 0){
            return (loc.getX()>=0 && loc.getX()<4 && loc.getX()>=0 && loc.getY()<5);
        }else{
             return (loc.getX()>=0 && loc.getX()<5 && loc.getX()>=0 && loc.getY()<4);
        }
    }

    /**
     * @param location of a structure
     * @return the structure at a given location, null if there isn't any
     */
    public Structure getStructureAt(Location location) {
        if (isValidLocation(location)) {
            return structures[location.getX()][location.getY()];
        }
        return null;
    }

    /**
     * @param loc of a road
     * @return the road ar a given location , null if there is'nt any
     */
    public Road checkRoadAt(Location loc){
        if(loc.getOrientation()==-1){
            return null;
        }
        if(loc.getOrientation()==0){
            return horizontalRoads[loc.getX()][loc.getY()];
        }
        if(loc.getOrientation()==1){
            return verticalRoads[loc.getX()][loc.getY()];
        }
        return null;
    }

    /**
     * Checks if a player has roads near a given location
     * @return true if there's at least one road adjacent to the location
     */
    public boolean haveAdjacentRoads(Location loc,Player player){
        ArrayList<Road> ajdRoads=getAdjacentRoads(loc);
        for(Road r : ajdRoads){
            if (r!=null && r.getOwner() == player) return true;
        }
        return false;
    }

    /**
     * Checks if a player has structures near a given lcoation
     * @return true if there's at least one road adjacent to the location
     */
    public boolean haveAdjacentStructures(Location loc, Player player){
        ArrayList<Structure> adjStructures = getAdjacentStructure(loc);
        for (Structure s : adjStructures){
            if(s!= null && s.getOwner() == player ) return  true;
        }
        return false;
    }


    public void affiche() {
        for(Tile[] tt : tiles){
            for(Tile t : tt){
                System.out.println(t);
            }
        }
    }//fixme : ameliorer l'affichage du tableau
    /*
    * exemple affichage par tiles avec les roads et strutures presents*/
}
