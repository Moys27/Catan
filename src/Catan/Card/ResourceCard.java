package Catan.Card;

public class ResourceCard extends Card{

    static final int brick = 1;
    static final int grain = 2;
    static final int wool = 3;
    static final int lumber = 4;
    static final int ore = 5;


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