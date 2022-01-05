package Catan.gui.view.object;


import Catan.Players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PlayerPanel extends JPanel {
    private Player p;
    JPanel resourcePanel;
    JPanel dataPanel;

    public PlayerPanel(Player p){
        this.p=p;
        this.setBorder(BorderFactory.createTitledBorder(p.name));

        resourcePanel = new JPanel(new GridLayout(1,2));
        setResourcePanel();
        this.add(resourcePanel);

        dataPanel = new JPanel();
        setData();
        this.add(dataPanel);
    }

    private void setData(){
        dataPanel.setBorder(BorderFactory.createTitledBorder("DATA"));
        dataPanel.setLayout(new GridLayout(6,1));
        dataPanel.add(createDataPanel("VP",p.getVictoryPoints()));
        dataPanel.add(createDataPanel("Nb roads allowed",p.getNbRoadsAllowed()));
        dataPanel.add(createDataPanel("Nb settlements allowed",p.getNbSettlementsAllowed()));
        dataPanel.add(createDataPanel("Nb cities allowed",p.getNbCitiesAllowed()));
        dataPanel.add(createDataPanel("Nb Roads built",p.getNbRoads()));
        dataPanel.add(createDataPanel("Nb Knights",p.getNbKnights()));
    }

    private JPanel createDataPanel(String key, int value){
        JPanel pane = new JPanel(new GridLayout(1,2));
        JLabel k = new JLabel(key);
        k.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        JLabel v = new JLabel(String.valueOf(value));
        v.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 10));
        pane.add(k);
        pane.add(v);
        return pane;
    }

    private void setResourcePanel(){
        resourcePanel.setBorder(BorderFactory.createTitledBorder("RESOURCES"));
        resourcePanel.setLayout(new GridLayout(5,1));
        for(Map.Entry r : p.getResourceDeck().entrySet()){
            resourcePanel.add(createDataPanel((String) r.getKey(), (Integer) r.getValue()));
        }
    }


}
