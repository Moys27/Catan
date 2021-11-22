package com.company;

import java.util.Scanner;

public class HumanPlayer extends Player{
    private static Scanner scanReply = new Scanner(System.in);
    public HumanPlayer() {
        super(Settings.askName());
    }



    public void  askAction(Board board, Deck d){
        System.out.println("Choose an action to make:" +
                "\n[1]Build Roads" +
                "\n[2]Build Settlements" +
                "\n[3]Build City" +
                "\n[4]Buy Developpement Card" +
                "\n[5]Trade" +
                "\n[6]Continue");
        int option = scanReply.nextInt();
        Location location;
        switch (option){
            case 1 :
                location = askLocation();
                if(canBuildRoadAt(board,location)){
                    buildRoad(board,location);
                }
                break;
            case 2:
                location = askLocation();
                if(canBuildSettlementAt(board,location)){
                    buildSettlement(board,location);
                }
                break;
            case 3:
                location=askLocation();
                if(canBuildCityAt(board,location)){
                    buildCity(board,location);
                }
                break;
            case 4:
                if(canBuyDevCard()){
                    buyDevCard(d);
                }
                break;
            case 5: //todo trade avec le port tout simplement
                break;
            case 6://todo faire passer le tour: peut etre qu'un breaak tout simplement suffit
                break;

        }
    }
    public Location askLocation(){ //todo : trouver un moyen de pouvoir donner les positions valables
        System.out.println("Insert the location. [Valid format : 'posX-posY-Orientation'] " +
                "(NB: Orientation : 0 for horizontal roads, 1 for Vertical Roads, -1 for Structure)");
        String input = scanReply.next();
        String[] inputs = input.split("-");
        if(inputs.length ==3){
            int posX= Integer.parseInt(inputs[0]);
            int posY = Integer.parseInt(inputs[1]);
            int orientation = Integer.parseInt(inputs[2]);
            return new Location(posX,posY,orientation);
        }
        System.out.println("Wrong location, please retry.");
        return askLocation();
    }
    @Override
    public void placeFirstSettlement(Board b, boolean b1) {

    //todo if b1 true -> winresource

    }

    @Override
    public void placeFirstRoad(Board b) {
    //todo place les premières routes à côté des premiers settlements
    }
}