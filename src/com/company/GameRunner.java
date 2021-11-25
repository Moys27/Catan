package com.company;
import java.util.Random;

public class GameRunner {
    public final Board board;
    public Player current;
    private Settings settings;
    private Player [] allPlayers; //fixme : une linkedList ne serait-elle pas mieux ?
    private Deck deckCard;


    GameRunner() {
        board = new Board();
        settings = new Settings();
        deckCard = new Deck();
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

    public void playFirts(){

    }
    public void nextPlayer(){
        //todo considérer le cas où allPlayers serait une linkedList
        if(current==allPlayers[allPlayers.length]){
            current=allPlayers[0];
            System.out.println("Hey, "+current.name+" your turn!");
            return;
        }
        if(current==allPlayers[0]){
            current=allPlayers[1];
            System.out.println("Hey, "+current.name+" your turn!");
            return;
        }
        if(current==allPlayers[1]){
            current=allPlayers[2];
            System.out.println("Hey, "+current.name+" your turn!");
            return;
        }
        if(current==allPlayers[2]){
            current=allPlayers[3];
            System.out.println("Hey, "+current.name+" your turn!");
            return;
        }
        current= allPlayers[4];
        System.out.println("Hey, "+current.name+" your turn!");
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

    public void askActions(Player p){
        if( p instanceof HumanPlayer) {
            System.out.println("Hey, " + p.name + " your turn!");
        }
        p.askAction(board,deckCard);
    }

    public void rollDice(){
       Random r= new Random();
       int num = r.nextInt(9)+2;
       if(num==7){
           //TODO Rober
       } else {
           for (int i = 0; i < board.getTiles().length; i++) {
               for (int j = 0; j < board.getTiles()[i].length; j++) {
                   if ((board.getTiles()[i][j].getId() == num) &&
                           (!board.getTiles()[i][j].hasRobber())) {
                       //board.getTiles()[i][j].getStructureMap();
                       //TODO acces to owner of settlement in the tile
                   }
               }
           }
       }
    }

    public void run(){
        //placeFirstSettlementsAndRoads(board);
        while (true){
            for (Player p : allPlayers) {
                rollDice();
                askActions(p);
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