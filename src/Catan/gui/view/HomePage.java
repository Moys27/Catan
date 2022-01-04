package Catan.gui.view;

import Catan.gui.controller.ButtonController;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private JPanel mainPane;
    private JButton optionsButton;
    private JButton newGameButton;
    private JButton resumeButton;
    private JButton aboutButton;

    private ButtonController c;
    private JTextField textField1;
    private JTextField textField2;
    private JButton validateButton;
    private JTextField textField3;

    HomePage(){
        setTitle("Catan 2.0");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width, screenSize.height);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        c = new ButtonController();

        mainPane= new JPanel();
        mainPane.setLayout(new GridLayout(2,1));

        JPanel topPane = new JPanel();

        mainPane.add(topPane);

        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new GridLayout(4,1));

        newGameButton = new JButton("New Game");

        newGameButton.addActionListener(event -> c.startGame());
        resumeButton= new JButton("Resume");
        resumeButton.addActionListener(event->c.resumeGame());
        optionsButton = new JButton("Options");
        optionsButton.addActionListener(event->c.goToOptions());
        aboutButton = new JButton("About");
        aboutButton.addActionListener(event -> c.goToAbouts());

        bottomPane.add(newGameButton);
        bottomPane.add(resumeButton);
        bottomPane.add(optionsButton);
        bottomPane.add(aboutButton);

        mainPane.add(bottomPane);
        this.add(mainPane);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

}

