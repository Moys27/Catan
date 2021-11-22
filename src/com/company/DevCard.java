package com.company;

/** This class models the development cards seen in the game, composed by :
* Knight Cards
* Victory Point Cards
* Progress Cards
* */

public class DevCard extends Card {
    static final int knights = 1; /* lets the player move the robber */
    static final int victoryPoint = 2; /* 1 additional Victory Point is added to the owners total and doesn't need to be played to win  */
    static final int monopoly = 3; /* player can claim all resource cards of a specific declared type  */
    static final int yearOfPlenty = 4; /* the player can draw 2 resource cards of their choice from the bank */
    static final int roadBuilding = 5; /* player can place 2 roads as if they just built them*/

    private String description;
    private String title;

    public DevCard(int type){
        super(type);
        initialization();

    }
    private void initialization(){
        switch(this.type){
            case knights:
                title = "Knights";
                description= "Move the robber. Steal 1 resource from the owner of a settlement or city adjacent to the robber's new hex.";
                break;
            case victoryPoint:
                title = "Victory Point";
                description= "Reveal this card on your turn if, with it, you reach the number of points requires for victory.";
                break;
            case monopoly:
                title = "Monopoly";
                description = "When you play this card, announce 1 type of resource." +
                        "All of the other players must give you all of the resource cards of this type that they have in their hands." ;
                break;
            case yearOfPlenty:
                title="Year Of Plenty";
                description= "If you play this card you may immediately take any 2 resource card from the supply stacks. " +
                        "You may use these cards to build in the same turn.";
                break;
            case roadBuilding:
                title="Road Building";
                description= "If you play this card, you may immediately place 2 free roads on the board (according to normal building rules).";break;
            default: break;
        }
    }


    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public void useCard(DevCard card){
        //todo
        switch(card.type){
            case knights -> {
            }
            case victoryPoint -> {
            }
            case monopoly -> {
            }
            case yearOfPlenty -> {
            }
            case roadBuilding -> {
            }
            default -> {
            }

        }
    }

}