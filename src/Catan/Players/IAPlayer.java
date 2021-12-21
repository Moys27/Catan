package Catan.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import Catan.Card.*;
import Catan.Board.*;
import Catan.Run.*;

public class IAPlayer extends Player{
    private Random r;
    public IAPlayer() {
        super(Settings.giveName());
        r = new Random();
    }


    //Todo Creer quelque chose empechant les numeros magiques et les strings magiques
    @Override
    public void  askAction(Board board, Deck d){
        HashMap<Integer,Integer> option= new HashMap<>();
        ArrayList<Integer> possibilities= new ArrayList<>();
        if(canBuildRoad()){
                possibilities.add(1);
        }
        if (canBuildSettlement()){
            possibilities.add(2);
        }
        if(canBuildCity()){
            possibilities.add(3);
        }
        if (canBuyDevCard()){
            possibilities.add(4);
        }
        if (resourceWanted()!=null){
            possibilities.add(5); //Trade
        }
        //Todo conUseDevCard
        Random r= new Random();
        if(possibilities.size()==0){
            executeAction( 6, board, d);
            return;
        }
        int randomOption= r.nextInt(possibilities.size());
        executeAction( possibilities.get(randomOption), board, d);

    }

    /**
     * Helps to the IAPlayer to choise between differents options
     */
    public String randomChoiseString(ArrayList<String> list){
        Random r= new Random();
        int randomOption= r.nextInt(list.size());
        return list.get(randomOption);
    }
    public Integer randomChoiseInteger(ArrayList<Integer> list){
        Random r= new Random();
        int randomOption= r.nextInt(list.size());
        return list.get(randomOption);
    }

    public String resourceWanted(){
        int random = r.nextInt(ResourceCard.array.length);
        for(int i=0;i<ResourceCard.array.length;i++) {
            for (int j = 0; j < ResourceCard.array.length - 1; j++) {
                if (canPayPrice(ResourceCard.array[(random + i + j) % ResourceCard.array.length], ResourceCard.array[(random + i) % ResourceCard.array.length])) {
                    return ResourceCard.array[(random + i) % ResourceCard.array.length];
                }
            }
        }
        return null;
    }

    public String resourceExchanged(String resourceWanted){
        ArrayList<String> posibilities= new ArrayList<>();
        for (String key: price.keySet()){
            if (canPayPrice(key,resourceWanted)){
                posibilities.add(key);
            }
        }
        return (String) randomChoiseString(posibilities);
    }




        @Override
    public void placeFirstSettlement(Board b, boolean b1) {
    //todo
    }

    @Override
    public void placeFirstRoad(Board b) {
    //todo
    }
}
