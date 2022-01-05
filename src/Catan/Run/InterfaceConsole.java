package Catan.Run;

import Catan.Card.ResourceCard;
import Catan.Players.Player;

import java.util.Scanner;

public class InterfaceConsole {

    public static void main(String[] args) {
        welcoming();
        GameRunner g = new GameRunner();
        g.run();

    }

    public static void welcoming(){
        System.out.println("\tWelcome to CATAN 2.0 !" +
                "\n\tYou are about to play a revisited version of THE SETTLERS OF CATAN." +
                "\n\tTo configure the game, please press ENTER. " +
                "\n\tTo end the program press CTRL+C.");
        Scanner s = new Scanner(System.in);
        String readString = s.nextLine();
        while(readString!=null) {
            if (readString.isEmpty()) {
                break;
            }
        }
    }

    public static void TestAddResources(Player player){
        for (int i=0;i<5;i++){
            player.winResource(ResourceCard.array[i],5);
        }
    }
}
