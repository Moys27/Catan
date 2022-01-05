package Catan.gui.view.object;

import Catan.Players.Player;
import Catan.gui.model.GameModel;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    private Player p;
    private ButtonGroup btnG;
    private JRadioButton buildRoadBox;
    private JRadioButton buildSettlementBox;
    private JRadioButton buildCityBox;
    private JRadioButton buyDevCardBox;
    private JRadioButton useDevCardBox;
    private JRadioButton tradeBox;
    private JRadioButton continueBox;


    public ActionPanel(GameModel model){
        this.p = model.getCurrentPlayer();
        this.setLayout(new GridLayout(7,1));
        btnG = new ButtonGroup();
        setCheckBoxes();
    }

    public void setCheckBoxes(){
        if(p.canBuildRoad()){
            buildRoadBox = new JRadioButton("Build A Road");
            btnG.add(buildRoadBox);
            this.add(buildRoadBox);
        }
        if(p.canBuildSettlement()){
            buildSettlementBox = new JRadioButton("Build A Settlement");
            btnG.add(buildSettlementBox);
            this.add(buildSettlementBox);
        }
        if(p.canBuildCity()){
             buildCityBox= new JRadioButton("Build A City");
            btnG.add(buildCityBox);
            this.add(buildCityBox);
        }
        if(p.canBuyDevCard()){
            buyDevCardBox = new JRadioButton("Buy A Development Card");
            btnG.add(buyDevCardBox);
            this.add(buyDevCardBox);
        }
        if(p.getNbCardsInHand()!=0){
            useDevCardBox = new JRadioButton("Use Development Card");
            btnG.add(useDevCardBox);
            this.add(useDevCardBox);
        }
        tradeBox = new JRadioButton("Trade");
        btnG.add(tradeBox);
        this.add(tradeBox);
        continueBox = new JRadioButton("Continue");
        btnG.add(continueBox);
        this.add(continueBox);
    }

    public void setPlayer(Player current){
        p=current;
        setCheckBoxes();
    }

}
