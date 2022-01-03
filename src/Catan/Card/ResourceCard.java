package Catan.Card;

public class ResourceCard extends Card{

    public static final int brick = 1;
    public static final int grain = 2;
    public static final int wool = 3;
    public static final int lumber = 4;
    public static final int ore = 5;
    public static final String Brick = "BRICK";
    public static final String Grain = "GRAIN";
    public static final String Wool = "WOOL";
    public static final String Lumber = "LUMBER";
    public static final String Ore = "ORE";
    public static final String [] array = {ResourceCard.Brick,ResourceCard.Grain,ResourceCard.Wool,ResourceCard.Lumber,ResourceCard.Ore};



    public ResourceCard(int n){
        super(n);
    }

    @Override
    public String toString() {
        switch (this.type){
            case brick : return Brick;
            case grain : return Grain;
            case wool : return Wool;
            case lumber: return Lumber;
            case ore: return Ore;
            default: return "None";
        }
    }

}