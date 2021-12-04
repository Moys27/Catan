package Catan.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private static int nbCard = 25 ;
    private ArrayList<DevCard> deck;
    public Deck(){
        deck = new ArrayList<>();
        this.initialization();
    }
    private void initialization(){
        for (int i = 0 ; i < 25; i++){
            if (i<14) deck.add(new DevCard(DevCard.knights));
            else if(i<19) deck.add(new DevCard(DevCard.victoryPoint));
            else if(i<21) deck.add(new DevCard(DevCard.monopoly));
            else if(i<23) deck.add(new DevCard(DevCard.yearOfPlenty));
            else deck.add(new DevCard(DevCard.roadBuilding));
        }
        Collections.shuffle(deck);
    }

    public DevCard dealACard(){
        return (nbCard>0 ? deck.remove((nbCard--)-1) : null);
    }
}