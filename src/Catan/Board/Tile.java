package Catan.Board;

import java.util.HashMap;
import java.util.Map;

public class Tile{

    public final String resource;
    public final int id;
    private boolean hasRobber;
    public Map<Location, Road> roadMap = new HashMap<Location, Road>() ;
    public Map<Location, Structure> structureMap = new HashMap<Location, Structure>();
    //todo pour chaque construction de routes et Structures, remplir les hashMap ci-dessus si les constructions sont reliées aux tuiles.


    public Tile(int id, String resource){
        this.id=id;
        this.resource = resource;
        hasRobber=(id==0)? true : false;
    }

    public int getId() {
        return id;
    }

    public String getResource() {
        return resource;
    }

    public boolean hasRobber() {
        return hasRobber;
    }

    public Map<Location, Structure> getStructureMap() {
        return structureMap;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "resource=" + resource +
                ", id=" + id +
                ", hasRobber=" + hasRobber +
                ", roadMap=" + roadMap +
                ", structureMap=" + structureMap +
                '}';
    }

    public void moveRobber(){
        hasRobber=true;
    }
    public void removeRobber() {
            hasRobber = false;
    }
}