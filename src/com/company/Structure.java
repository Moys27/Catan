package com.company;


public interface Structure {
    Location getLocation();
    Player getOwner();
    boolean isBuildable();
    boolean hasNecessaryResources(Player p);
    void winResources(int resource);
}