package Catan.Board;

/**
 * This class encloses all elementary methods needed in the project
 */

public class Methods {
    /**
     *
     * @param x
     * @param y
     * @param xBounds
     * @param yBounds
     * @return
     */
    public static boolean isAValidLocation(int x, int y,int xBounds, int yBounds){
        return (x<xBounds && x>=0 && y<yBounds && y>=0);
    }
}
