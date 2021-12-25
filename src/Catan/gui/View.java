package Catan.gui;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{
    private JPanel panneau;
    private JButton pressMeButton;

    private void createUIComponents() {
        panneau = new JPanel();
        panneau.setLayout(new BorderLayout());

        pressMeButton = new JButton("Press me");
        pressMeButton.addActionListener(event -> System.out.println("Aouchhh!"));

        panneau.add(pressMeButton,BorderLayout.SOUTH);
    }

    View(){
        setTitle("Exemple");
        setSize(600,600);
        createUIComponents();
        this.getContentPane().add(panneau);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
