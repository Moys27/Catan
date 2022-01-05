package Catan.gui;

import Catan.Run.GameRunner;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.*;

public class View extends JFrame{
    private GameRunner game;
    private JPanel panneau;
    private JButton pressMeButton;
    private JPanel players;
    private JPanel control;

    private void createUIComponents() {
        panneau = new JPanel();
        panneau.setLayout(new BorderLayout());

        pressMeButton = new JButton("Press me");
        pressMeButton.addActionListener(event -> System.out.println("1"));

        panneau.add(pressMeButton,BorderLayout.SOUTH);
    }

    private void initialiseControl(){
        control= new JPanel();
        control.setLayout(new GridLayout(3,3));
       /* JButton uno= new JButton("1");
        uno.addActionListener(event -> Settings.setIAnswer(1));
        JButton dos= new JButton("2");
        dos.addActionListener(event -> Settings.setIAnswer(2));
        JButton tres= new JButton("3");
        tres.addActionListener(event -> Settings.setIAnswer(3));
        JButton cuatro= new JButton("4");
        cuatro.addActionListener(event -> Settings.setIAnswer(4));
        JButton cinco= new JButton("5");
        cinco.addActionListener(event -> Settings.setIAnswer(5));
        JButton seis= new JButton("6");
        seis.addActionListener(event -> Settings.setIAnswer(6));
        JButton siete= new JButton("7");
        siete.addActionListener(event -> Settings.setIAnswer(7));
        JButton ocho= new JButton("8");
        uno.addActionListener(event -> Settings.setIAnswer(8));
        JButton nueve= new JButton("9");
        nueve.addActionListener(event -> Settings.setIAnswer(9));
        control.add(uno);
        control.add(dos);
        control.add(tres);
        control.add(cuatro);
        control.add(cinco);
        control.add(seis);
        control.add(siete);
        control.add(ocho);
        control.add(nueve);
        */

        this.getContentPane().add(control,SOUTH);
    }
    private void initialiseJPanelPlayers(){
        players= new JPanel();
        for (int i= 0; i<game.getAllPlayers().length;i++){
            players.add(initialiseJPanelPlayer(i));
        }
        this.getContentPane().add(players,NORTH);

    }


    private JPanel initialiseJPanelPlayer(int id){
        JPanel player=new JPanel();
        player.setLayout(new GridLayout(3,1));
        JPanel name= newPanel(game.getAllPlayers()[id].name);
        JPanel victoryPoints= newPanel("Victory Points: "+game.getAllPlayers()[id].getVictoryPoints());
        JPanel nbCards = newPanel("Resources Cards: "+game.getAllPlayers()[id].getNbCardsInHand());
        JPanel nbRoad= newPanel("Roads: "+game.getAllPlayers()[id].getNbRoads());
        JPanel nbKnights= newPanel("Knights: " +game.getAllPlayers()[id].getNbKnights());
        player.add(name);
        player.add(victoryPoints);
        player.add(nbKnights);
        player.add(nbRoad);
        player.add(nbCards);
        return player;
    }

    private JPanel newPanel(Object var){
        JPanel varPanel= new JPanel();
        JLabel varLabel= new JLabel();
        BorderLayout borderLayout= new BorderLayout();
        varPanel.setLayout(borderLayout);
        varLabel.setHorizontalAlignment(JLabel.CENTER);
        varLabel.setText(String.valueOf(var));
        varPanel.add(varLabel, CENTER);
        return varPanel;
    }


    View(GameRunner game){
        this.game=game;
        setTitle("Catan");
        setSize(1800,800);
        this.getContentPane().setLayout(new BorderLayout());
        initialiseJPanelPlayers();
        initialiseControl();
        createUIComponents();
        this.getContentPane().add(panneau,CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
