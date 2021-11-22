package com.company;


public interface Structure {
    Location getLocation();
    Player getOwner();
    void winResources(String resource);
}