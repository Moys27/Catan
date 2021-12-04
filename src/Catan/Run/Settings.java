package Catan.Run;
import Catan.Board.Location;

import java.util.Scanner;

public class Settings {
    private static Scanner scanReponse = new Scanner(System.in);
    //todo traiter les cas où l'utilisateur insère un mauvais format comme réponse (débug)
    private static  int numberPlayersDefault=4;
    private static boolean humanPlayersDefault=false;
    public static int countPlayer=-1;



    public static Location askLocation() { //todo : trouver un moyen de pouvoir donner les positions valables
        System.out.println("Insert the location. [Valid format : 'posX/posY/Orientation'] " +
                "(NB: Orientation : 0 for horizontal roads, 1 for Vertical Roads, -1 for Structure)");
        String input = scanReponse.next();
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

    public static int numberPlayers(){
        System.out.println("Choose the number of players:");
        System.out.println("[3]");
        System.out.println("[4] (default)");
        int answer;
        try {
            answer= Integer.valueOf(scanReponse.nextLine());
            if ((answer!=3)&&(answer!=4)){
                System.out.println("Choose 3 or 4, please");
                return numberPlayers();
            }
            return answer;
        }
        catch (NumberFormatException e){
            return numberPlayersDefault;
        }
    }

    public static boolean askHumanPlayers(){
        System.out.println("Will you play with anothers humans?");
        System.out.println("[1] Yes");
        System.out.println("[2] No (default)");
        try {
            int answer= Integer.valueOf(scanReponse.nextLine());
            if ((answer!=1)&&(answer!=2)){
                System.out.println("Choose between option [1] or [2], please");
                return askHumanPlayers();
            }
            return (answer==1);
        }
        catch (NumberFormatException e){
            return humanPlayersDefault;
        }
    }
    public static int HumanPlayers( int maximumPlayers){
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
                return numberPlayers();
            }
            return answer;
        }
        catch (NumberFormatException e){
            return 0; //default value
        }
    }

    public static String askName(){
        System.out.println("What is your name?");
        String answer;
        Scanner scanReponse=new Scanner(System.in);
        try {
            answer= scanReponse.nextLine();
            return answer;
        } catch (Exception e){
            return giveName();
        }
    }
    public static String giveName(){
        String [] nameList= {"Ada","Linux","Pixel"};
        countPlayer++;
        return nameList[countPlayer];

    }

}
