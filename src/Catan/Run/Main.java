package Catan.Run;

import Catan.Board.Board;
import Catan.Card.Deck;
import Catan.Card.ResourceCard;
import Catan.Players.HumanPlayer;
import Catan.Players.Player;

public class Main {

    public static void main(String[] args) {
        GameRunner g = new GameRunner();
        g.run();
        /**
        Board board= new Board();
        HumanPlayer player= new HumanPlayer();

        TestAddResources(player);
        player.askAction(board,new Deck());

        player.showHand();
        player.askAction(board,new Deck());

        System.out.println(player.getResourceDeck().toString());

*/
    }

    public static void TestAddResources(Player player){
        for (int i=0;i<5;i++){
            player.winResource(ResourceCard.array[i],5);
        }
    }
}
