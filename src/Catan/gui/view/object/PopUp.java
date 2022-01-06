package Catan.gui.view.object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PopUp extends JDialog {
    private JButton buttonOK;
    private JLabel message;
    private JPanel contentPane;

    public PopUp(String msg){
        contentPane= new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());


        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        message = new JLabel(msg);
        message.setHorizontalAlignment(JLabel.CENTER);
        buttonOK = new JButton("OK");
        buttonOK.addActionListener(e -> onOK());

        contentPane.add(message, BorderLayout.CENTER);
        contentPane.add(buttonOK, BorderLayout.SOUTH);


        // call onOK() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onOK() on ESCAPE
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        dispose();
    }

}
