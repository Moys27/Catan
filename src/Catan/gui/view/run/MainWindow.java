package Catan.gui.view.run;

import Catan.Board.Board;
import Catan.gui.view.object.CatanBoard;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {


    /**
     * Constructor
     */
    MainWindow(){
        setTitle("Catan 2.0");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width, screenSize.height);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Board b = new Board(); //à mettre en paramètre
        CatanBoard board = new CatanBoard(b);
        add(board);

    }

    /**
     * Application running
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
