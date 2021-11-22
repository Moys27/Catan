package com.company;
import java.util.Random;
import java.util.Scanner;

public class GameRunner {
    public final Board board;
    private Player current;
    private Settings settings;
    private Player [] allPlayers;
    private Deck deckCard;

    GameRunner() {
        board = new Board();
        settings = new Settings();
        createPlayers();
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
                p.placeFirstSettlement(b,i==1);
                p.placeFirstRoad(b);
            }
            i--;
        }
    }

    public void askActions(){
        for (Player p : allPlayers){
            p.askAction(board,deckCard);
        }
    }
    public int rollDice(){
       Random r= new Random();
       return r.nextInt(9)+2;
    }

    public void run(){
        placeFirstSettlementsAndRoads(board);
        askActions();
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