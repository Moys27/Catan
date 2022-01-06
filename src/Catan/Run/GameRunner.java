package Catan.Run;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Catan.Board.*;
import Catan.Players.*;
import Catan.Card.*;

public class GameRunner implements Serializable {
    public final Board board;
    private Player current;
    private Settings settings;
    public Player [] allPlayers;
    public Deck deckCard;
    private int maxVictoryPoint;
    private int minNbRoadForTitle;
    private int minNbKnightsForTitle;
    private Title longestRoad;
    private Title largestArmy;

    public Player[] getAllPlayers(){
         return allPlayers;
    }

    public Player getCurrent() {
        return current;
    }

    public Deck getDeckCard() {
        return deckCard;
    }


    public GameRunner(){
        board = new Board();
        settings = new Settings();
        deckCard = new Deck();
        maxVictoryPoint =0;
        minNbRoadForTitle=3;
        minNbKnightsForTitle =3;
        longestRoad= new Title(1);
        largestArmy= new Title(2);
        createPlayers();
    }


    /**
     * Initialize players
     */
    public void createPlayers(){
        current= new HumanPlayer();
        allPlayers= new Player[settings.setNumberPlayers()];
        allPlayers[0]= current;
        int numHP= settings.setUpHumanPlayers(allPlayers.length);
        for (int i = 1; i<=numHP; i++ ){
            System.out.println("Player "+(i+1)+":"); //Player 1 = allPla
            allPlayers[i]= new HumanPlayer();
        }
        for (int i = numHP+1; i< allPlayers.length; i++ ) {
            allPlayers[i] = new IAPlayer();
        }
    }

    public void placeFirstSettlementsAndRoads(Board b){
        int i = 2;
        while (i > 0){
            for (Player p : allPlayers){
                if (p instanceof HumanPlayer)System.out.println("Hey, " + p.name + " your turn!");
                p.placeFirstSettlement(b,i==1);
                p.placeFirstRoad(b);
            }
            i--;
        }
    }

    public void askActions(Player p){
        if( p instanceof HumanPlayer) {
            System.out.println("Hey, " + p.name + " your turn!");
        }
        p.askAction(board,deckCard);
    }

    public void rollDice(Player p, Board b){
       Random r= new Random();
       int num = r.nextInt(6)+1 +r.nextInt(6)+1;
        if(num==7){
            discardCards();
            useRobber(p,b);
       } else {
           board.distributeResources(num);
       }
    }

    public void discardCards(){
        for (int i=0;i<allPlayers.length;i++){
            allPlayers[i].discardExtraCards();
        }
    }

    public static void moveRobber(Player player, Board b){
        int [] coord= player.askCoordinatesTile();
        b.moveRobber(coord[0],coord[1]);
    }


    public static void stolePlayer(Player player,Board b){
        List<Player> players= cleanStolenListPlayer(b.peopleStolen(),player);
        if (players.isEmpty()){
            return;
        }
        Player playerStolen= player.choosePlayerToStolen(players);
        player.stoleACardto(playerStolen);
    }


    /**
     * @param player instead of using the current player, we can modularize the parameter
     *               and use in the future for the card Develop Knights
     */
    public static void useRobber(Player player, Board b){ //FIXME PAS CLAIR LA DESCRIPTION
        moveRobber(player,b);
        stolePlayer(player,b);
    }

    /*
    Discard the player how takes de action, from the list of players how summit the action
     */
    public static List<Player> cleanStolenListPlayer(List<Player> players, Player player){
        if(players==null){
            return null;
        }
        List<Player> playerList= new ArrayList<>();
        for(Player p:players){
            if (!player.equals(p)){
                playerList.add(p);
            }
        }
        return playerList;
    }

    public void verifyTitle(Player owner){
        if(owner.getNbKnights()>=minNbRoadForTitle){
            largestArmy.setOwner(owner);
            minNbKnightsForTitle =owner.getNbKnights()+1;
            System.out.println(owner.name +" have the Largest Army");
        }
        if(owner.getNbRoads()>=minNbRoadForTitle){
            longestRoad.setOwner(owner);
            minNbRoadForTitle=owner.getNbRoads()+1;
            System.out.println(owner.name +" have the Longest Road");
        }
    }

    public void run(){
        placeFirstSettlementsAndRoads(board);
        boolean endGame= true;
        int i=0;
        while (endGame){
            current = allPlayers[i%allPlayers.length];
            rollDice(current, board);
            askActions(current);
            verifyTitle(current);
            if (current.getVictoryPoints() > maxVictoryPoint) maxVictoryPoint = current.getVictoryPoints();
            if(maxVictoryPoint>=10) endGame=false;
            i++;
        }
        System.out.println(current.name +" win the game! Congratulations!");
        System.out.println("You have play " +i+ " turns");
    }
}