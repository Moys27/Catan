package com.company;

public class DevCard extends Card {
    static final int knights = 1; /* lets the player move the robber */
    static final int victoryPoint = 2; /* 1 additional Victory Point is added to the owners total and doesn't need to be played to win  */
    static final int monopoly = 3; /* player can claim all resource cards of a specific declared type  */
    static final int yearOfPlenty = 4; /* the player can draw 2 resource cards of their choice from the bank */
    static final int roadBuilding = 5; /* player can place 2 roads as if they just built them*/

    private int id;
    private String description;
    private String title;
    private Player owner;

    public DevCard(int id){
        this.id=id;
        initialization(this.id);
        this.owner=null;

    }
    private void initialization(int id){
        switch(id){
            case knights:
                title = "Knights";
                description="Lets the player move the robber"; break;
            case victoryPoint:
                title = "Victory Point";
                description="1 additional Victory Point is added to the owners total and doesn't need to be played to wins"; break;
            case monopoly:
                title = "Monopoly";
                description = "If you play this card, you must name 1 type of resource." +
                        "All of the other players must give you all of the resource cards of this type that they have in their hands." +
                        "If an opponent does not have resource card of the specified type, they do not have to give you anything.";
                break;
            case yearOfPlenty:
                title="Year Of Plenty";
                description= "If you play this card you may immediately take any 2 resource card from the supply stacks. " +
                        "You may use these cards to build in the same turn.";
                break;
            case roadBuilding:
                title="Road Building";
                description="If you play this card, you may immediately place 2 free roads on the board (according to normal building rules).";break;
            default: break;
        }
    }

    public void setOwner(Player p){
        this.owner = p;
    }

}