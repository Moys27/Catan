package com.company;
import java.util.Random;
import java.util.Scanner;

public class GameRunner {
    private Player current;
    private Player [] allPlayers;

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