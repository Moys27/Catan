package Catan.gui.view.object;

import Catan.Board.Tile;
import Catan.Card.ResourceCard;

import javax.swing.*;
import java.awt.*;

public class TilePanel extends JPanel {

    private Color color ;

    final Color FOREST = new Color (38, 131, 38);
    final Color HILLS = new Color (166, 110, 74);
    final Color MOUNTAINS = new Color(144, 146, 152);
    final Color FIELDS = new Color(231, 231, 154);
    final Color PASTURE = new Color(135, 204, 107);
    final Color DESERT = new Color(229, 182, 45);

    public TilePanel(Tile tile) {
        setSize(400,400);
        determineType(tile.getResource());
        setLayout(new BorderLayout());
        setBackground(color);
        JLabel label = new JLabel(tile.getResource()+tile.getId());
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }

    void determineType(String resource){
        switch (resource){
            case ResourceCard.Brick: color=HILLS; break;
            case ResourceCard.Lumber: color =FOREST;break;
            case ResourceCard.Grain: color = FIELDS;break;
            case ResourceCard.Ore: color = MOUNTAINS;break;
            case ResourceCard.Wool:color = PASTURE;break;
            default: color=DESERT;break;
        }
    }


}
