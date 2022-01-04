package Catan.gui.view.object;

import Catan.Board.Board;
import Catan.Board.Tile;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Affichage du plateau de jeu sur l'interface
 */
public class BoardPanel extends JPanel {

    public BoardPanel(Board board) {

        Tile[][] tiles = board.getTiles();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.BOTH;
        initBoard(tiles, constraints);

    }

    /**
     * Initialise l'affichage en fonction du plateau
     *
     * @param tiles       represente le plateau de jeu
     * @param constraints organise l'affichage
     */
    private void initBoard(Tile[][] tiles, GridBagConstraints constraints) {
        this.putOceansPanel(tiles.length, constraints);
        for (int i = 0; i < tiles.length; i++) {
            constraints.gridy = i;
            constraints.gridx = 0;
            this.add(new OceanPanel(), constraints);
            constraints.gridx++;
            for (int j = 0; j < tiles[i].length; j++) {
                JPanel panel =new TilePanel(tiles[i][j]);
                constraints.gridx = j;
                this.add(panel, constraints);
            }
            constraints.gridx++;
            this.add(new OceanPanel(), constraints);
        }
        constraints.gridy++;
        this.putOceansPanel(tiles.length, constraints);
    }


    private void putOceansPanel(int l, GridBagConstraints constraints) {
        for (int i = 0; i <= l; i++) {
            constraints.gridx = i;
            this.add(new OceanPanel(), constraints);
        }
        constraints.gridy++;
        constraints.gridx = 0;
    }

    public static void main(String[] args) {
        JFrame j = new JFrame();
        JPanel board = new BoardPanel(new Board());
        j.add(board);
        j.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        j.setBounds(0,0,screenSize.width, screenSize.height);

        j.setVisible(true);

    }
}
