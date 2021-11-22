package com.company;
import java.util.Random;

public class GameRunner {
    private Player current;
    private Player [] allPlayers;
    private Deck deckDevelop;

    GameRunner() {
        createPlayers();
    }
    public void createPlayers(){
        Settings initialise= new Settings();
        current= new HumanPlayer();
        allPlayers= new Player[initialise.numberPlayers()];
        allPlayers[0]= current;
        int numHP= initialise.HumanPlayers(allPlayers.length);
        for (int i = 1; i<=numHP; i++ ){
            System.out.println("Player "+(i+1)+":"); //Player 1 = allPla
            allPlayers[i]= new HumanPlayer();
        }
        for (int i = numHP+1; i< allPlayers.length; i++ ) {
            allPlayers[i] = new IAPlayer();
        }
        deckDevelop= new Deck();
    }

    public void play(){
        int nbressource= rollDice(); //fonction repartir resource
       // current.choix();
    }

    public void playFirts(){

    }
    public void nextPlayer(){
        if(current==allPlayers[allPlayers.length]){
            current=allPlayers[0];
            return;
        }
        if(current==allPlayers[0]){
            current=allPlayers[1];
            return;
        }
        if(current==allPlayers[1]){
            current=allPlayers[2];
            return;
        }
        if(current==allPlayers[2]){
            current=allPlayers[3];
            return;
        }
        current= allPlayers[4];
    }

    public int rollDice(){
       Random r= new Random();
       return r.nextInt(9)+2;
    }

    /*
    Etapes du jeux:
        Tirage des des
        Obtenir des ressources
        Utiliser carte Develop
        Comerce
        Achat carte Develop (Attribut lastDevelopCard, interdiction de l utiliser)
        Utiliser carte develop
        Construction

     */
}