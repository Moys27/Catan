package com.company;

public class Test {
    public static void main(String[] args) {
        Board b = new Board();
        for(Tile [] tab : b.getTiles()){
            for(Tile t : tab){
                System.out.println(t);
            }
        }
    }
}
