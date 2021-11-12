package com.company;

public class Title extends Card{

    private String nomination;
    protected Player owner;
    public Title(int type) {
        super(type);
        owner = null;
        setNomination();
    }

    private void setNomination(){
        switch (this.type) {
            case 1 -> nomination = "Longest Road";
            case 2 -> nomination = "Largest Army";
            default -> {
            }
        }
    }

    public void setOwner(Player owner) {
        if(this.owner == owner) return;

        if (this.owner == null){
            this.owner = owner;
            this.owner.winVictoryPoint(2);
        }else{
            this.owner.looseVictoryPoint(2);
            this.owner = owner;
            this.owner.winVictoryPoint(2);
        }
    }

    @Override
    public String toString() {
        return this.nomination;
    }
}