package Catan.gui.view.run;


import Catan.Run.GameRunner;
import Catan.Run.InterfaceConsole;


import java.awt.*;

public class GUI{
    /**
     * Application running
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(
                () -> {
                    InterfaceConsole.welcoming();
                    GameView view = new GameView(new GameRunner());
                    view.setVisible(true);
                });
    }
}
