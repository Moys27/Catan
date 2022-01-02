package Catan.Players.test;

import Catan.Board.Board;
import Catan.Players.IAPlayer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test pour les fonctions de players
 *
 * @see Catan.Players.Player
 * @see Catan.Players.IAPlayer
 * @see Catan.Players.HumanPlayer
 * */
public class PlayerTest {

    @Test
    public void testSuggestedLocationRoads(){
        var sugRoads = new IAPlayer().suggestedLocationRoads(new Board());
        Assert.assertTrue(sugRoads.isEmpty());

    }


}
