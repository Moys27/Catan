package com.company;

public class ResourceCard extends Card{
    /**
     * brick (id=1)
     * grain (id=2)
     * wool (id=3)
     * lumber (id=4)
     * ore (id=5) */

    int typeRessource;

    public ResourceCard(int n){
        typeRessource=n;
    }
}