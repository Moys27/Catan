package Catan.Run;

import java.util.Random;
import Catan.Board.*;
import Catan.Players.*;
import Catan.Card.*;

public class GameRunner {
    public final Board board;
    public Player current;
    private Settings settings;
    private Player [] allPlayers; //fixme : une linkedList ne serait-elle pas mieux ?
    private Deck deckCard;
    private int maxVictoryPoint;


    GameRunner() {
        board = new Board();
        settings = new Settings();
        deckCard = new Deck();
        createPlayers();
        maxVictoryPoint =0;
    }

    public void createPlayers(){
        current= new HumanPlayer();
        allPlayers= new Player[settings.numberPlayers()];
        allPlayers[0]= current;
        int numHP= settings.HumanPlayers(allPlayers.length);
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
        while (i < 0){
            for (Player p : allPlayers){
                p.placeFirstSettlement(b,i==1); //todo#1 à implémenter
                p.placeFirstRoad(b);//todo#2
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

    public void rollDice(){
       Random r= new Random();
       int num = r.nextInt(6)+1 +r.nextInt(6)+1;
        if(num==7){
           //TODO Rober
       } else {
           board.distributeResources(num);
       }
    }

    public void run(){
        placeFirstSettlementsAndRoads(board);
        while (maxVictoryPoint < 10){ //todo find the condition
            for (Player p : allPlayers) {
                current = p;
                rollDice();
                askActions(p);
                if(p.getVictoryPoints() > maxVictoryPoint) maxVictoryPoint=p.getVictoryPoints();
                if (maxVictoryPoint >= 10) break;
            }
        }
    }

    /*
    Etapes du jeux:
        Tirage des des
        Obtenir des ressources
        Utiliser carte Develop
        Commerce
        Achat carte Develop (Attribut lastDevelopCard, interdiction de l utiliser)
        Utiliser carte develop
        Construction

     */
}