package Catan.gui.view;

import Catan.Board.Board;
import Catan.gui.view.object.CatanBoard;

import javax.swing.*;

import java.awt.*;

public class test{

    public static void main(String[] args){
        JFrame f = new JFrame("Dessiner un rectangle");

        CatanBoard board = new CatanBoard(new Board());
        f.add(board);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setBounds(0,0,screenSize.width, screenSize.height);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
