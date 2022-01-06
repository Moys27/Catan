package Catan.gui.view.object;

import Catan.Board.Board;
import Catan.Board.Tile;

import javax.swing.*;
import java.awt.*;


/**
 * This class models the board display
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
     * Initializes the board display
     * @param tiles       represents the game board
     * @param constraints organizes the display
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

}
