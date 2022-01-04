package Catan.Run;

import Catan.Board.Board;
import Catan.Board.Location;

import java.util.Random;

public class TestElementaire {
    public static void main(String[] args) {
        Board b = new Board();
        for (int i = 0 ; i < 4; i++){
                Location loc = new Location(i,0,2);
                if (loc.isCoast()) System.out.println(b.getSpecification(loc));


        }
    }

}

