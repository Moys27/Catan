package com.company;
import java.util.Random;
import java.util.Scanner;

public class GameRunner {
    private Player current;

    //TODO Au debut de chaque jeux initialiser Player.nbPlayers
    GameRunner(){
        //ask number of players
        //ask names
        String s="test";
        current= new HumanPlayer(s);

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