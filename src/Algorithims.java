// This is only to be used as framework code for the game
// Java class will prob and should not be utilized : )
import java.util.*;

public class Algorithims {
    private int[] key;
    private int[][] guesses;
    private int[][] hints;
    public final static int rows = 10;
    public final static int cols = 4;
    public int currentRow = 1;
    
    public Algorithims(){
        key = new int[cols];
        guesses = new int[rows+1][cols];
        hints = new int[rows+1][cols];
        for (int i = 0; i < cols; i++){
            key[i] = (int)(Math.random()* 6 + 1);
        }
        // key = new int[]{4, 1, 4, 3};
        for (int y = 0; y < rows; y++){
            for (int x = 0; x < cols; x++){
                guesses[y][x] = 0;
                hints[y][x] = 0;
            }
        }
    }
    public int getCol(){
        return cols;
    }

    public int getRows(){
        return rows;
    }

    public void setColor(int row, int col, int color){
        if (row == currentRow && col >= 0 && col < cols) {
            guesses[row][col] = color;
        }
        // guesses[y][x] = num;
    }

    public int[] getKey(){
        return key;
    }

    public int[][] getGuesses(){
        return guesses;
    }

    public String getBoard(){
        String board = "";
        for (int y = 0; y < rows; y++){
            board += Arrays.toString(guesses[y]) + " -> " + Arrays.toString(hints[y]) + "\n";
            // board += Arrays.toString(giveHints(y)) + " | ";
            // board += Arrays.toString(guesses[y]) +"\n";
        }

        return board;
    }
    public boolean blackCheck(int row, int col){
        return guesses[row][col] == key[col];
    }

    public void advanceRow(){
        if (currentRow < rows) currentRow++;
    }

    // public int whiteCheck(int row, int col){
    //     for (int i = 0; i < cols; i++) {
    //         if (i != col && guesses[row][i] == key[col]) {
    //             return i;
    //         }
    //     }
    //     return -1;
    // }

    public int[] check(int row){
        int[] hintPegs = {0,0};
        boolean[] keyVisited = new boolean[cols];  // tracks if key peg has been matched for black or white
        boolean[] guessVisited = new boolean[cols]; // tracks if guess peg has been matched for black or white

        for (int col = 0; col < cols; col++) {
            if (blackCheck(row, col)) {
                hintPegs[0]++;
                keyVisited[col] = true;
                guessVisited[col] = true;
            }
        }
        for (int col = 0; col < cols; col++) {
            if (!guessVisited[col]) { 
                for (int k = 0; k < cols; k++) {
                    if (!keyVisited[k] && guesses[row][col] == key[k]) {
                        hintPegs[1]++;
                        keyVisited[k] = true;
                        break;
                    }
                }
            }
        }
        // System.out.println(hintPegs[0] + " " + hintPegs[1]);
        System.out.println("Blacks: " + hintPegs[0] + ", Whites: " + hintPegs[1]);
        return hintPegs;
    }
    public int[] giveHints(int row){
        int[] feedback = check(row);
        Arrays.fill(hints[row], 0);

        for (int i = 0, b = 0, w = 0; i < cols; i++) {
            if (b < feedback[0]) {
                hints[row][i] = 1;  // black pegs first
                b++;
            } else if (w < feedback[1]) {
                hints[row][i] = 2;  // then white pegs
                w++;
            }
        }
        //blacks will go first just cus
        // int i = 0;

    //     while (blacks > 0){
    //         hints[row][i] = 1;
    //         blacks--;
    //         i++;
    //     }

    //    while (whites > 0){
    //         hints[row][i] = 2;
    //         whites--;
    //         i++;
    //    }
        
        return hints[row];
    }

    // reset the entire game
    public void resetGame() {
        for (int row = 0; row < rows; row++) {
            Arrays.fill(guesses[row], 0);
            Arrays.fill(hints[row], 0);
        }
        currentRow = 1;
        // optionally reshuffle the key
        for (int i = 0; i < cols; i++) {
            key[i] = (int)(Math.random()*6 + 1);
        }
    }

    // // Clear the current row of guesses
    // public void clearCurrentRow() {
    //     if (currentRow < rows) {
    //         Arrays.fill(guesses[currentRow], 0);
    //         // Arrays.fill(hints[currentRow], 0);
    //     }
    // }

}

