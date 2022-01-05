package Catan.Board;
import Catan.Players.*;

public class Settlement implements Structure{
    private Player owner;
    private Location location;

    public Settlement(Player p, Location l){
        this.owner=p;
        this.location=l;
        /*if (hasAPort()){
            owner.priceReduction(Board.getSpecification(l));
        }*/
    }
    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void winResources(String resource) {
        this.getOwner().winResource(resource,1);
    }

    public boolean hasAPort(){
        return location.isCoast();
    }

}