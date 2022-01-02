package Catan.Run;

import java.util.Random;

public class TestElementaire {
    public static void main(String[] args) {
        Random r = new Random();
        int random=0;

        for (int i = 0 ; i< 100 ; i++){
            random = r.nextInt(8) ;
            System.out.print(random+"\t");
        }
    }

}

