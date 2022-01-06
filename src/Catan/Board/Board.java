package Catan.Board;

import java.util.*;

import Catan.Card.ResourceCard;
import Catan.Players.*;

/** This class models the board
 * We chose to fix its dimension  with 16 tiles (instead of the 19s of the standard game board).
 */

public class Board {

    public final Tile[][] tiles;
    private Tile actualRobber;
    private static final int sizeT= 4;
    public static final int sizeS= 5;
    public static final int numberPort= 8;

    private  Structure [][] structures = new Structure[sizeS][sizeS];
    private  Road [][]  verticalRoads = new Road[sizeT][sizeS];
    private  Road [][] horizontalRoads = new Road[sizeS][sizeT];
    public static HashMap<Location, Integer> Ports= new HashMap<>();

    public Board (){
        tiles = new Tile [sizeT][sizeT];
        initializeTiles();
        initialiseCoast();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Structure[][] getStructures() {
        return structures;
    }

    public Road[][] getHorizontalRoads() {
        return horizontalRoads;
    }

    public Road[][] getVerticalRoads() {
        return verticalRoads;
    }

    public static int getSizeT() {
        return sizeT;
    }

    public static int getSpecification(Location l){
        Location coastLoc= null;
        for(Location location : Board.Ports.keySet()){
            if(Location.compareLocation(location,l))
                coastLoc = location;
        }
        return Ports.get(coastLoc);

    } //FIXME ports.get() peut être null pourquoi?


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
            if(i<3) resourcesList.add(ResourceCard.Brick);
            else if(i<6) resourcesList.add(ResourceCard.Ore);
            else if(i<9) resourcesList.add(ResourceCard.Grain);
            else if(i<12) resourcesList.add(ResourceCard.Wool);
            else resourcesList.add(ResourceCard.Lumber);
        }
        resourcesList.add("DESERT");
        Collections.shuffle(resourcesList);

        /*
         * Tiles initialization
         */
        int k = 0; //to browse the ResourcesList
        int K=0; //to browse the IdList
        for (int i = 0; i<tiles.length; i++){
            for(int j=0; j< tiles[i].length;j++){
                String resource = resourcesList.get(k++);
                int idTiles= (resource.equals("DESERT") ? id.get(id.size()-1) : id.get(K++));
                tiles[i][j]=new Tile(idTiles, resource);
            }
        }

    }

    /**
     * @return an array of ports with their specialisations
     * (specification between 0 and 7)
     * Creer et jetter au hazar une liste de Port avec leur respectives resources.
     * random+i%numberPort et le specification du por, de 1 a 5 pour les ressource et de 6 a 8 pour les generales
     * Je devais avois jusqu'a oui, pour ça j'ai ecris que si le modulo, alors au leiu de donner un specification=0, sera une specification = 8= nbPorts
     */
    public int[] initialisePorts(){
        Random r= new Random();
        int random= r.nextInt(numberPort);
        int [] ports= new int[numberPort];
        for (int i=0;i<numberPort;i++){
            ports[i]= (random+i)%numberPort;
            if((random+i)%numberPort==0){
                ports[i]= numberPort;
            }
        }
        return ports;
    }

    /**
     * Initialise the ports localisation
     *
     * Une fois que j'avais creer le type de ports au hazard, il fallairt que j'attribu c'est 8 port à la cote de notre jeu
     * C'est pour ça je j'utilise un HashMap avec des localisations.
     * En vrai, comme notre jeu est carré toutes les settlement on acces a un port, mais ce n'est pas toujours le meme
     * Cette fonction elle serve a distinguer les ports deux à deux, alors pour reflechir a la distribution donc j'ai lister toutes
     * les localisations et les differents ports qu'elles devaient contenir (code commenter)
     * et finalement je l'ai optimisé avec le code qui est en bas, mais c'est exactement la meme chose
     */

    public void initialiseCoast() {
        int[] ports= initialisePorts();
        int a=0;
        for (int i=0; i<sizeS; i++){
            if(i==2) a++;
            Ports.put(new Location(i,0,2),ports[a]);
            Ports.put(new Location(sizeT,i,2),ports[a+2]);
            Ports.put(new Location(sizeT-i,sizeT,2),ports[a+4]);
            Ports.put(new Location(0,sizeT-i,2),ports[a+6]);
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
     * Fill the structureMap of every Tile that has
     * @param structure in its vertex.
     */
    private void fillTileMapForStructure(Location location,Structure structure){
        int x = location.getX();
        int y = location.getY();
        for(int i = -1 ; i < 1 ; i++){
            for (int j = -1; j <1; j++){
                if(isAValidLocation(x+i,y+j,sizeT,sizeT)){
                    Tile t = tiles[x+i][y+j];
                    t.structureMap.put(location,structure);
                }
            }
        }
    }

    /**
     * Proceeds to the placement of structures
     * @returns if the structure was placed successfully
     * */
    public boolean placeStructure(Structure structure){
        if(structure != null) {
            Location loc = structure.getLocation();
            /*if (structure instanceof Settlement){
                structures[loc.getX()][loc.getY()]=(Settlement) structure;
                    return true;
            }
            if (structure instanceof City){
                structures[loc.getX()][loc.getY()]= (City)structure;
                    return true;
            }*/
            structures[loc.getX()][loc.getY()]=structure;
            fillTileMapForStructure(loc,structure);
            return true;
        }
        return false;
    }

    /**
     * Fill the roadMap of every Tile that has
     * @param road in its edge.
     */
    public void fillTilesMapForRoad(Location location, Road road){
        int x = location.getX();
        int y = location.getY();
        int o= location.getOrientation();
        if(o==0){
            for(int i = -1; i<1;i++){
                if(isAValidLocation(x+i,y,sizeT,sizeT)){
                    Tile t = tiles[x+i][y];
                    t.roadMap.put(location, road);
                }
            }
        }else{
            for(int i = -1; i<2;i++){
                if(isAValidLocation(x,y+i,sizeT,sizeT)){
                    Tile t = tiles[x][y+i];
                    t.roadMap.put(location, road);
                }
            }
        }
    }

    public ArrayList<Location> suggestedLocationFirstSettlements(){
        ArrayList <Location> locations = new ArrayList<>();
        for(int i = 0 ; i < sizeS ; i++){
            for(int j = 0 ; j < sizeS ; j++){
                if(structures[i][j] == null) locations.add(new Location(i,j,2));
            }
        }
        return locations;
    }

    public void showSuggestedLocationFirstSettlements(){
        System.out.println("\tWhere you may place your first settlement");
        for (Location l : suggestedLocationFirstSettlements()){
            System.out.println("\t"+l);
        }
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
                fillTilesMapForRoad(loc,road);
                return true;
            }
            if (loc.getOrientation() == 1) {
                verticalRoads[loc.getX()][loc.getY()]=road;
                fillTilesMapForRoad(loc,road);
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
                rollStructures.add((Structure) structure.getValue());
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
            /*if(isAValidLocation(loc.getX()-1, loc.getY()-1,4,4))
                output.add(tiles[loc.getX()-1][loc.getY()-1]);

            if(isAValidLocation(loc.getX()-1, loc.getY(),4,4))
                output.add(tiles[loc.getX()-1][loc.getY()]);

            if(isAValidLocation(loc.getX(), loc.getY()-1,4,4))
                output.add(tiles[loc.getX()][loc.getY()-1]);

            if(isAValidLocation(loc.getX(), loc.getY(),4,4))
                output.add(tiles[loc.getX()][loc.getY()]);*/

            for (int i = -1 ; i < 1 ; i++){
                for (int j = -1 ; j< 1; j++){
                    if (isAValidLocation(loc.getX()+i, loc.getY()+j,4,4))
                        output.add(tiles[loc.getX()+i][loc.getY()+j]);
                }
            }

        }
        return output;
    }

    public boolean isAValidLocation(int x, int y,int xBounds, int yBounds){
        return (x<xBounds && x>=0 && y<yBounds && y>=0);
    }

    /**
     * Gives the structures adjacent to the given location
     * @param loc location being checked
     * @return ArrayList<Structure> list of adjacent structures
     */
    public HashMap<Location, Structure> getAdjacentStructure(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        int o = loc.getOrientation();
        HashMap<Location,Structure> output = new HashMap<>();
        if(loc.isaNode()){
            /*if(isAValidLocation(x-1, y,4,5))
                output.add(structures[x-1][y]);

            if(isAValidLocation(x+1, y,4,5))
                output.add(structures[x+1][y]);

            if(isAValidLocation(x, y-1,5,4))
                output.add(structures[x][y-1]);

            if(isAValidLocation(x, y+1,5,4))
                output.add(structures[x][y+1]);*/
            getAdjacentStructureForNode(output,x,y);


        }else if (o == 0){
            /*if(isAValidLocation(x, y,5,5))
                output.add(structures[x][y]);

            if(isAValidLocation(x, y+1,5,5))
                output.add(structures[x][y+1]);*/
            getAdjacentStructureForHorizontalRoads(output, x, y);


        }else if (o == 1){
            /*if(isAValidLocation(x, y,5,5))
                output.add(structures[x][y]);

            if(isAValidLocation(x+1, y,5,5))
                output.add(structures[x+1][y]);*/
            getAdjacentStructureForVerticalRoads(output,x,y);
        }
        return output;
    }

    private void getAdjacentStructureForNode(HashMap<Location, Structure> output, int x, int y){
        for (int i = -1 ; i < 2 ; i+=2){
            if(isAValidLocation(x+i, y,4,5)){
                output.put(new Location(x+i,y,2),structures[x+i][y]);
            }
        }
        for(int i = -1 ; i < 2 ; i+=2){
            if(isAValidLocation(x, y+i,5,4))
                output.put(new Location(x,y+i,2),structures[x][y+i]);
        }
    }

    private void getAdjacentStructureForHorizontalRoads(HashMap<Location, Structure> output, int x, int y){
        for (int i = 0; i < 2 ; i++){
            if(isAValidLocation(x, y+i,5,5))
                output.put(new Location(x,y+i,0),structures[x][y+i]);
        }
    }

    private void getAdjacentStructureForVerticalRoads(HashMap<Location, Structure> output, int x, int y){
        for (int i = 0; i < 2 ; i++){
            if(isAValidLocation(x+i, y,5,5))
                output.put(new Location(x+i,y,1),structures[x+i][y]);
        }
    }



    /**
     * Gives the roads adjacent to the given location
     * @param loc location being checked
     * @return ArrayList<Road> list of adjacent roads
     */

    public HashMap<Location, Road> getAdjacentRoads(Location loc){
        int x = loc.getX();
        int y = loc.getY();
        HashMap<Location, Road> output = new HashMap<>();
        if (loc.isaNode()) {
            /*if(isAValidLocation(x-1, y,4,5))
                output.add(verticalRoads[x-1][y]);

            if(isAValidLocation(x, y,4,5))
                output.add(verticalRoads[x][y]);


            if(isAValidLocation(x, y-1,5,4))
                output.add(horizontalRoads[x][y-1]);

            if(isAValidLocation(x, y,5,4))
                output.add(horizontalRoads[x][y]);*/
            getAdjacentRoadsForNode(output,x,y);

        }else{

            if (loc.getOrientation() == 0){
                /*if(isAValidLocation(x-1, y,4,5))
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
                    output.add(horizontalRoads[x][y+1]);*/
                getAdjacentRoadsForHorizontalRoads(output,x,y);

            }
            else {
                /*if(isAValidLocation(x-1, y,4,5))
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
                    output.add(horizontalRoads[x+1][y]);*/
                getAdjacentRoadsForVerticalRoads(output,x,y);

            }
        }
        return output;
    }

    private void getAdjacentRoadsForNode(HashMap<Location, Road> output, int x, int y){
        for (int i = -1 ; i <1 ; i++){
            if(isAValidLocation(x+i, y,4,5))
                output.put(new Location(x+i,y,1),verticalRoads[x+i][y]);
            if(isAValidLocation(x, y+i,5,4))
                output.put(new Location(x,y+i,0),horizontalRoads[x][y+i]);
        }
    }
    private void getAdjacentRoadsForHorizontalRoads(HashMap<Location, Road> output, int x, int y){
        for (int i = -1 ; i < 1 ; i++){
            for (int j = 0 ; j<2 ; j++){
                if(isAValidLocation(x+i, y+j,4,5))
                    output.put(new Location(x+i,y+j,1),verticalRoads[x+i][y+j]);
            }
        }
        for (int j = -1 ; j <2 ; j+=2){
            if(isAValidLocation(x, y+j,5,4))
                output.put(new Location(x,y+j,0),horizontalRoads[x][y+j]);
        }
    }
    private void getAdjacentRoadsForVerticalRoads(HashMap<Location, Road> output, int x, int y){
        for (int i = -1 ; i <2 ; i+=2){
            if(isAValidLocation(x+i, y,4,5))
                output.put(new Location(x+i,y,1),verticalRoads[x+i][y]);
        }
        for (int i = 0; i <2; i++){
            for (int j = -1 ; j <1;  j++){
                if(isAValidLocation(x+i, y+j,5,4))
                    output.put(new Location(x+i,y+j,0),horizontalRoads[x+i][y+j]);
            }
        }
    }

    /**
     * @return  the number of roads built from a given @param loc
     */
    public int countRoadsFromLocation(Location loc, Player player){
        HashMap<Location, Road> adjacentRoads = this.getAdjacentRoads(loc);
        List<Integer> roads = new ArrayList<>();
        int max=0;
        if (player.getNbRoads()==0){
            return max;
        }
        for (Map.Entry road : adjacentRoads.entrySet()){
            if(((Road)road.getValue()).getOwner() == player) {
                roads.add(1 + countRoadsFromLocation((Location) road.getKey(), player));
            }
        }
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
        if (loc.getOrientation() == 2){
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
     * @return the road in a given location , null if there is'nt any
     */
    public Road checkRoadAt(Location loc){
        if(loc.getOrientation()==2){
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
        HashMap<Location, Road> adjacentRoads = this.getAdjacentRoads(loc);
        for (Map.Entry r : adjacentRoads.entrySet()){
            Road road = (Road)r.getValue();
            if( road != null && road.getOwner() == player) return true;
        }
        return false;
    }

    /**
     * Checks if a player has structures near a given location
     * @return true if there's at least one structure adjacent to the location
     */
    public boolean haveAdjacentStructures(Location loc, Player player){
        HashMap<Location, Structure> adjacentStructure = this.getAdjacentStructure(loc);
        for (Map.Entry s : adjacentStructure.entrySet()){
            Structure structure = (Structure) s.getValue();
            if( structure!= null && structure.getOwner() == player) return true;
        }
        return false;
    }

    public void moveRobber(int x, int y) {
        tiles[x][y].moveRobber();
        if (actualRobber != null) {
            actualRobber.removeRobber();
        }
        actualRobber = tiles[x][y];
    }

    public List<Player> peopleStolen(){
        List<Player> owners= new ArrayList<>();
        if (!actualRobber.getStructureMap().isEmpty()){
            for (Location l: actualRobber.getStructureMap().keySet()){
                owners.add(actualRobber.getStructureMap().get(l).getOwner());
            }
        }
        return owners;
    }

}
