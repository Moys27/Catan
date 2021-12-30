package Catan.Card;

public class ResourceCard extends Card{

    public static final int brick = 1;
    public static final int grain = 2;
    public static final int wool = 3;
    public static final int lumber = 4;
    public static final int ore = 5;
    public static final String Brick = "Brick";
    public static final String Grain = "Grain";
    public static final String Wool = "Wool";
    public static final String Lumber = "Lumber";
    public static final String Ore = "Ore";
    public static final String [] array = {ResourceCard.Brick,ResourceCard.Grain,ResourceCard.Wool,ResourceCard.Lumber,ResourceCard.Ore};



    public ResourceCard(int n){
        super(n);
    }

    @Override
    public String toString() {
        switch (this.type){
            case brick : return "brick";
            case grain : return "grain";
            case wool : return "wool";
            case lumber: return "lumber";
            case ore: return "ore";
            default: return "none";
        }
    }

}