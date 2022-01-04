package Catan.gui.view.run;

import Catan.Run.GameRunner;
import Catan.gui.controller.GameController;
import Catan.gui.model.GameModel;
import Catan.gui.view.object.BoardPanel;
import Catan.gui.view.object.CatanBoard;
import Catan.gui.view.object.WarningDialog;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    GameModel model;
    GameController controller;

    private JPanel mainPanel;
    private JPanel locationPanel;
    private JTextField getX;
    private JTextField getY;
    private JTextField getO;
    private JButton validateButton;

    private BoardPanel board;

    GameView(GameRunner gameRunner){
        model = new GameModel(gameRunner);
        controller=new GameController(model,this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width, screenSize.height);


        setUpFrame();

    }

    private void setUpFrame() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setUpLocationPanel();
        mainPanel.add(locationPanel, BorderLayout.SOUTH);

        board = new BoardPanel(model.getBoard());
        mainPanel.add(board, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private void setUpLocationPanel(){
        locationPanel=new JPanel();
        locationPanel.setLayout(new GridLayout(1,4));
        getX = new JTextField();
        getX.setBorder(BorderFactory.createTitledBorder("x"));
        getY = new JTextField();
        getY.setBorder(BorderFactory.createTitledBorder("y"));
        getO = new JTextField();
        getO.setBorder(BorderFactory.createTitledBorder("orientation"));

        validateButton = new JButton("Validate");
        locationPanel.add(getX);
        locationPanel.add(getY);
        locationPanel.add(getO);
        locationPanel.add(validateButton);

        locationPanel.setBorder(BorderFactory.createTitledBorder("Insert a location"));

        validateButton.addActionListener(e -> controller.insertLocation());

    }

    public JTextField [] getLocationTextField(){
        return new JTextField[]{getX,getY,getO};
    }

    public static void main(String[] args) {
        GameView view = new GameView(new GameRunner());
        view.setVisible(true);
    }


    public void showWarning(String s) {
        WarningDialog warn = new WarningDialog(s);
    }
}
