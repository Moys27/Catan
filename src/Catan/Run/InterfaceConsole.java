package Catan.Run;

import Catan.Board.Board;
import Catan.Card.Deck;
import Catan.Card.ResourceCard;
import Catan.Players.HumanPlayer;
import Catan.Players.Player;

import java.util.Scanner;

public class InterfaceConsole {

    public static void main(String[] args) {
        //letsPlay();


        HumanPlayer humanPlayer= new HumanPlayer();
        TestAddResources(humanPlayer);
        //humanPlayer.askAction(new Board(),new Deck());
        System.out.println(humanPlayer.getPrice());
        //humanPlayer.askAction(new Board(),new Deck());

    }

    public static void welcoming(){
        System.out.println("\tWelcome to CATAN 2.0 !" +
                "\n\tYou are about to play a revisited version of THE SETTLERS OF CATAN." +
                "\n\tTo configure the game, please press ENTER. " +
                "\n\tTo end the program press CTRL+C.");
        Scanner s = new Scanner(System.in);
        String readString = s.nextLine();
            if (readString.isEmpty()) {
                System.out.println("Starting game...");
                System.out.println("");
            } else{
               endGame();
            }

    }

    public static void endGame(){
        System.out.println("Game Over. Thanks for play to Catan 2.0 by Tania Mahandry and Ana Parres Cerezo");
        System.exit(0);
    }

    public static void letsPlay(){
        welcoming();
        GameRunner game= new GameRunner();
        game.run();
        endGame();
    }

    public static void TestAddResources(Player player){
        for (int i=0;i<5;i++){
            player.winResource(ResourceCard.array[i],5);
        }
    }
}
