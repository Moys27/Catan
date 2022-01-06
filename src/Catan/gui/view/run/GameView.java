package Catan.gui.view.run;

import Catan.Board.Location;
import Catan.Players.Player;
import Catan.Run.GameRunner;
import Catan.gui.controller.GameController;
import Catan.gui.model.GameModel;
import Catan.gui.view.object.ActionPanel;
import Catan.gui.view.object.BoardPanel;
import Catan.gui.view.object.PlayerPanel;
import Catan.gui.view.object.PopUp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {
    GameModel model;
    GameController controller;

    private JPanel mainPanel;
    private PlayerPanel [] players;
    private BoardPanel board;

    GameView(GameRunner gameRunner){
        model = new GameModel(gameRunner);
        controller=new GameController(model,this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width, screenSize.height);

        players = new PlayerPanel[model.getSettlers().length];

        setUpFrame();

    }

    private void setUpFrame() {
        mainPanel = new JPanel();
        //mainPanel.setLayout(new BorderLayout());
        //mainPanel.add(locationPanel(), BorderLayout.SOUTH);

        JPanel middle = new JPanel(new GridLayout(1,3));
        middle.add(setPlayersPanel());
        board = new BoardPanel(model.getBoard());
        middle.add(board);

        JPanel middleEast = new JPanel(new GridLayout(2,1));
        middleEast.add(new ActionPanel(model,controller));

        JButton diceRolling = new JButton("Roll Dice");
        diceRolling.setPreferredSize(new Dimension(40, 40));

        middleEast.add(diceRolling);

        middle.add(middleEast);

        mainPanel.add(middle);
        this.add(mainPanel);
    }



    private JPanel setPlayersPanel(){
        JPanel panel = new JPanel(new GridLayout(players.length,1));
        int i = 0 ;
        for (Player p : model.getSettlers()){
            players[i] = new PlayerPanel(p);
            panel.add(players[i++]);
        }
        return panel;
    }

    public void showWarning(String s) {
        PopUp warn = new PopUp(s);
    }

    public void popUp(ArrayList<Location> suggestion) {

    }
}
