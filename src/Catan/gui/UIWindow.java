package Catan.gui;

public class UIWindow {
    private View wind;

    UIWindow(){
        wind = new View();
        wind.setVisible(true);
    }

    public static void main(String[] args) {
        UIWindow ui = new UIWindow();
    }
}
