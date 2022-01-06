package Catan.Run;
import Catan.Board.Location;
import Catan.Card.ResourceCard;

import java.util.Scanner;

/**
 * This class models the interaction between the user interface and the game
 * */

public class Settings {
    private static Scanner scanReponse = new Scanner(System.in);
    //todo traiter les cas où l'utilisateur insère un mauvais format comme réponse (débug)
    private static  int numberPlayersDefault=4;
    private static boolean humanPlayersDefault=false;
    public static int countPlayer=-1;


    /**
     * Ask the location for the structure or the road to build
     * @return the given location
     * */

    public static Location askLocation() { //todo : trouver un moyen de pouvoir donner les positions valables
        System.out.println("Insert the location. [Valid format : 'posX/posY/Orientation'] " +
                "(NB: Orientation : 0 for horizontal roads, 1 for Vertical Roads, 2 for Structure)");
        String input = scanReponse.next();
        String[] inputs = input.split("/");
        if(inputs.length ==3){
            int posX=  Integer.parseInt(inputs[0]);
            int posY = Integer.parseInt(inputs[1]);
            int orientation = Integer.parseInt(inputs[2]);
            return new Location(posX,posY,orientation);
        }
        System.out.println("Wrong location, please retry.");
        return askLocation();
    }

    /**
     * Determines the number of game players
     */
    public static int setNumberPlayers(){
        System.out.println("Choose the number of players:");
        System.out.println("[3]");
        System.out.println("[4] (default)");
        int answer;
        try {
            answer= Integer.valueOf(scanReponse.nextLine());
            if ((answer!=3)&&(answer!=4)){
                System.out.println("Choose 3 or 4, please");
                return setNumberPlayers();
            }
            return answer;
        }
        catch (NumberFormatException e){
            return numberPlayersDefault;
        }
    }

    /**
     *  Determines the number of human player that will play except the main user.
     */

    public static boolean askHumanPlayers(){
        System.out.println("Will you play with another humans?");
        System.out.println("[1] Yes");
        System.out.println("[2] No (default)");
        try {
            int answer= scanReponse.nextInt();
            if ((answer!=1)&&(answer!=2)){
                System.out.println("Choose between option [1] or [2], please");
                return askHumanPlayers();
            }
            return (answer==1);
        }
        catch (NumberFormatException e){
            scanReponse = new Scanner(System.in);
            return humanPlayersDefault;
        }
    }

    /**
     *
     * @param maximumPlayers
     * @return
     */
    public static int setUpHumanPlayers(int maximumPlayers){
        System.out.println("Who many humans will play with you?");
        System.out.println("[0] (default)");
        System.out.println("[1] ");
        System.out.println("[2]");
        if (maximumPlayers==4){
            System.out.println("[3] ");
        }
        int answer;
        try {
            answer= Integer.valueOf(scanReponse.nextLine());
            if ((answer!=0)&&(answer!=1)&&(answer!=2)&&(answer!=3)){
                System.out.println("Choose betweean 0 and "+(maximumPlayers-1)+", please");
                return setNumberPlayers();
            }
            return answer;
        }
        catch (NumberFormatException e){
            return 0; //default value
        }
    }

    /**
     *
     * @return
     */
    public static String setName(){
        System.out.println("What is your name?");
        String answer;
        try {
            answer= scanReponse.nextLine();
            return answer;
        } catch (Exception e){
            scanReponse = new Scanner(System.in);
            return setIAName();
        }
    }

    /**
     *Attributes name for IA players
     */
    public static String setIAName(){
        String [] nameList= {"Ada","Linux","Pixel"};
        countPlayer++;
        return nameList[countPlayer];
    }

    /**
     * @return The type of resource that the player want to commerce
     */

    public static String chooseResource() {
        int answer;
        try {
            System.out.println("Choose the resource :");
            for (int i=0;i<ResourceCard.array.length;i++){
                System.out.println("["+(i+1)+"] "+ResourceCard.array[i]);
            }
            answer = chooseBetweenCards(ResourceCard.array.length);
            switch (answer) {
                case 1:
                    return ResourceCard.Brick;
                case 2:
                    return ResourceCard.Grain;
                case 3:
                    return ResourceCard.Wool;
                case 4:
                    return ResourceCard.Lumber;
                case 5:
                    return ResourceCard.Ore;
            }
        } catch (NumberFormatException e) {
            System.out.println("Choose a resource (between the numbers)");
            return chooseResource();
        }
        return chooseResource();
    }

    public static int chooseBetweenCards(int i) {
        int answer;
            try {
                answer = scanReponse.nextInt();
                if ((answer >= 1) && (answer <= i)) {
                    return answer;
                } else {
                    System.out.println("Not valid number. Try again");
                    return chooseBetweenCards(i);
                }
            } catch (Exception e) {
                System.out.println("Not a number. Try again");
                scanReponse = new Scanner(System.in);
                return chooseBetweenCards(i);
            }
        }

    public static int chooseActionDevCard(){
        System.out.println("[1] Use");
        System.out.println("[2] Description");
        int answer;
        try {
            answer = chooseBetweenCards(2);
            if ((answer == 1) || (answer == 2)) {
                return answer;
            }
        } catch (Exception e){
            return chooseActionDevCard();
        }
        return chooseActionDevCard();
    }

    /*
    number include, for array
     */
    public static int chooseANumber(int number){
        for (int i=0;i<number;i++){
            System.out.println("["+(i+1)+"]");
        }
        int answer;
        try {
            answer = scanReponse.nextInt();
            if ((answer >= 1) && (answer <= number)){
                return answer;
            }else {
                System.out.println("Invalid number. Try again");
                return chooseANumber(number);
            }
        } catch (Exception e){
            System.out.println("Not a number. Try again");
            scanReponse = new Scanner(System.in);
            return chooseANumber(number);
        }

    }
}
