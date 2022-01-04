package Catan.gui.view.object;


import Catan.Board.Board;
import Catan.Board.Tile;

import javax.swing.*;
import java.awt.*;

public class CatanBoard extends JPanel {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int sW = screenSize.width;
    public static int sH = screenSize.height;

    private Board board;
    final Color FOREST = new Color (38, 131, 38);
    final Color HILLS = new Color (115, 53, 3);
    final Color MOUNTAINS = new Color(144, 146, 152);
    final Color FIELDS = new Color(231, 231, 154);
    final Color PASTURE = new Color(135, 204, 107);
    final Color DESERT = new Color(229, 182, 45);
    public static int TILES_DIM = 100;


    /**
     * Constructor
     */
    public CatanBoard(Board board){
        this.board= board;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    private void draw (Graphics2D g, Color c, int x , int y){
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(5));
        //g.drawRect(x*TILES_DIM+(sW/3), y*TILES_DIM+(sH/5), TILES_DIM, TILES_DIM);
        g.drawRect(x*TILES_DIM, y*TILES_DIM, TILES_DIM, TILES_DIM);
        g.setColor(c);
        //g.fillRect(x*TILES_DIM+(sW/3), y*TILES_DIM+(sH/5), TILES_DIM, TILES_DIM);
        g.fillRect(x*TILES_DIM, y*TILES_DIM, TILES_DIM, TILES_DIM);
    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Tile [][] tiles = board.getTiles();

        for (int x = 0 ; x < tiles.length; x++){
            for (int y = 0; y < tiles.length; y++){
                String res = tiles[x][y].getResource();
                switch (res){
                    case "brick" :draw(g2d, HILLS,x,y);break;
                    case "ore" :draw(g2d,MOUNTAINS,x,y);break;
                    case "grain":draw(g2d, FIELDS,x,y);break;
                    case "wool":draw(g2d, PASTURE,x,y);break;
                    case "lumber" :draw(g2d, FOREST,x,y);break;
                    case "desert" :draw(g2d, DESERT,x,y);break;
                }
            }
        }

    }

}
