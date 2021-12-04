package Catan.Board;
import Catan.Players.*;
public interface Structure {
    Location getLocation();
    Player getOwner();
    void winResources(String resource);
}