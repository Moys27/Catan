package Catan.gui.view.object;

import Catan.Players.HumanPlayer;
import Catan.Players.Player;
import Catan.gui.controller.GameController;
import Catan.gui.model.GameModel;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    private Player p;
    private GameController c;
    private ButtonGroup btnG;
    private JRadioButton buildRoadBox;
    private JRadioButton buildSettlementBox;
    private JRadioButton buildCityBox;
    private JRadioButton buyDevCardBox;
    private JRadioButton useDevCardBox;
    private JRadioButton tradeBox;
    private JRadioButton continueBox;


    public ActionPanel(GameModel model, GameController c){
        this.c=c;
        this.setLayout(new GridLayout(7,1));
        btnG = new ButtonGroup();
        setPlayer(model.getCurrentPlayer());
    }

    public void setCheckBoxes(){
        if(!p.canBuildRoad()){
            buildRoadBox = new JRadioButton("Build A Road");
            buildRoadBox.addActionListener(e -> c.executeAction(1));
            btnG.add(buildRoadBox);
            this.add(buildRoadBox);
        }
        if(p.canBuildSettlement()){
            buildSettlementBox = new JRadioButton("Build A Settlement");
            buildSettlementBox.addActionListener(e -> c.executeAction(2));
            btnG.add(buildSettlementBox);
            this.add(buildSettlementBox);
        }
        if(p.canBuildCity()){
             buildCityBox= new JRadioButton("Build A City");
            buildCityBox.addActionListener(e -> c.executeAction(3));
            btnG.add(buildCityBox);
            this.add(buildCityBox);
        }
        if(p.canBuyDevCard()){
            buyDevCardBox = new JRadioButton("Buy A Development Card");
            buyDevCardBox.addActionListener(e -> c.executeAction(4));
            btnG.add(buyDevCardBox);
            this.add(buyDevCardBox);
        }
        if(p.getNbCardsInHand()!=0){
            useDevCardBox = new JRadioButton("Use Development Card");
            useDevCardBox.addActionListener(e -> c.executeAction(5));
            btnG.add(useDevCardBox);
            this.add(useDevCardBox);
        }
        tradeBox = new JRadioButton("Trade");
        tradeBox.addActionListener(e -> c.executeAction(6));
        btnG.add(tradeBox);
        this.add(tradeBox);
        continueBox = new JRadioButton("Continue");
        continueBox.addActionListener(e -> c.executeAction(7));
        btnG.add(continueBox);
        this.add(continueBox);
    }

    public void setPlayer(Player current){
        p=current;
        if(p instanceof HumanPlayer){
            setCheckBoxes();
        } else{
            c.generateIAAction();
        }

    }

}
