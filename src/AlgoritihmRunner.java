// UNUSED FILE
// This file is not used in the final version of the project. It was used to test the Algorithims class and its methods.

import java.util.Scanner;
import java.util.*;
public class AlgoritihmRunner {
    /**
     * Main method to test the Algorithims class
     * Creates a new Algorithims object and tests the methods
     * @param args
     * @author Ethan
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Algorithims game = new Algorithims();

        System.out.println("Input your guess # # # #");
        System.out.println(Arrays.toString(game.getKey()));
        for (int y = 0; y < game.getCol(); y++){
            for (int x = 0; x < game.getRows(); x++){
                game.setColor(x,y, s.nextInt());
            }
            System.out.println(game.getBoard());
            
        }
        
        
    }
}
