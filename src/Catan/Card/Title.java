package Catan.Card;
import Catan.Players.Player;

public class Title extends Card{

    private String nomination;
    protected Player owner;
    public static final int LONGEST_ROAD=1;
    public static final int LARGEST_ARMY=2;

    public Title(int type) {
        super(type);
        owner = null;
        setNomination();
    }

    private void setNomination(){
        switch (this.type) {
            case LONGEST_ROAD -> nomination = "Longest Road";
            case LARGEST_ARMY -> nomination = "Largest Army";
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