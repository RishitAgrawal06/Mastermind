// This is only to be used as framework code for the game
// Java class will prob and should not be utilized : )
import java.util.*;

public class Algorithims {
    private int[] key; // 
    private int[][] guesses;
    private int[][] hints;
    public final static int rows = 10;
    public final static int cols = 4;
    public int currentRow = 1;
    
    /**
     * Constructor for the Algorithims class
     * Initializes the key, guesses, and hints arrays
     * Generates a random key for the game and sets the guesses and hints arrays to 0
     * @param void
     * @return void
     * @author Ethan
     */
    public Algorithims(){
        key = new int[cols];
        guesses = new int[rows+1][cols];
        hints = new int[rows+1][cols];
        for (int i = 0; i < cols; i++){
            key[i] = (int)(Math.random()* 6 + 1);
        }
        // key = new int[]{1, 5, 1, 2}; // for testing
        clearFromRow(0);
    }

    /**
     * Clear the guesses and hints from a given row to the end of the board using recursion
     * @param row the row to start clearing from
     * @return void
     * @author Rishit
     */
    public void clearFromRow(int row) {
        if (row >= rows) return;
        Arrays.fill(guesses[row], 0);
        Arrays.fill(hints[row], 0);
        clearFromRow(row + 1);  // recursively clear the next row
    }
    
    /**
     * Get the current row of the game
     * @param void
     * @return int the current row of the game
     * @author Ethan
     */
    public int getCol(){
        return cols;
    }

    /**
     * Get the number of rows in the game
     * @param void
     * @return int the number of rows in the game
     * @author Ethan
     */
    public int getRows(){
        return rows;
    }

    /**
     * Set the color of a peg in the guesses array
     * @param row the row of the peg
     * @param col the column of the peg
     * @param color the color to set the peg to
     * @return void
     * @author Ethan + Rishit
     */
    public void setColor(int row, int col, int color){
        if (row == currentRow && col >= 0 && col < cols) {
            guesses[row][col] = color;
        }
    }

    /**
     * Get the key of the game
     * @param void
     * @return int[] the key of the game
     * @author Ethan
     */
    public int[] getKey(){
        return key;
    }

    /**
     * Get the guesses of the game
     * @param void
     * @return int[][] the guesses of the game
     * @author Ethan
     */
    public int[][] getGuesses(){
        return guesses;
    }

    /**
     * Get the hints of the game... this isn't used in the game but can be used for debugging
     * @param void
     * @return String containing the board of the game in the CLI
     * @author Ethan + Rishit
     */
    public String getBoard(){
        String board = "";
        for (int y = 0; y < rows; y++){
            board += Arrays.toString(guesses[y]) + " -> " + Arrays.toString(hints[y]) + "\n";
        }

        return board;
    }

    /**
     * Check if the guess is correct
     * @param row the row of the guess
     * @param col the column of the guess
     * @return boolean true if the guess is correct, false otherwise
     * @author Ethan + Rishit
     */
    public boolean blackCheck(int row, int col){
        return guesses[row][col] == key[col];
    }

    /**
     * Advance the row of the game if the current row is less than the number of rows
     * @param void
     * @return void
     * @author Rishit
     */
    public void advanceRow(){
        if (currentRow < rows) currentRow++;
    }

    /**
     * Check the guess and return the number of black and white pegs
     * First checks for black pegs, then white pegs while iterating through the columns
     * @param row the row of the guess
     * @return int[] an array containing the number of black and white pegs
     * @author Rishit + Ethan
     */
    public int[] check(int row){
        int[] hintPegs = {0,0};
        boolean[] keyVisited = new boolean[cols];  // tracks if key peg has been matched for black or white
        boolean[] guessVisited = new boolean[cols]; // tracks if guess peg has been matched for black or white
        // check for black pegs first
        for (int col = 0; col < cols; col++) {
            if (blackCheck(row, col)) {
                hintPegs[0]++;
                keyVisited[col] = true;
                guessVisited[col] = true;
            }
        }
        // check for white pegs
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
        // System.out.println("Blacks: " + hintPegs[0] + ", Whites: " + hintPegs[1]); // for testing
        return hintPegs;
    }

    /**
     * Returns an array of feedback hints for a provided row where 2 = black and 1 = white
     * @param row the current row needed to be checked 
     * @return int[] an array of feedback hints containing black and white pegs
     * @author Rishit + Ethan
     */
    public int[] giveHints(int row){
        int[] feedback = check(row);
        Arrays.fill(hints[row], 0);

        for (int i = 0, b = 0, w = 0; i < cols; i++) {
            if (b < feedback[0]) {
                hints[row][i] = 2;  // black pegs first
                b++;
            } else if (w < feedback[1]) {
                hints[row][i] = 1;  // then white pegs
                w++;
            }
        }
        
        return hints[row];
    }

    /**
     * Resets the game to its starting position with guesses and hints defaulted to 0
     * @param void
     * @return void
     * @author Rishit
     */
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

    /**
     * Sort the guesses by feedback and return a list of the sorted guesses
     * The guesses are sorted in descending order from best to worst guess
     * The method also features a text-based header for the sorted guesses
     * @param void
     * @return List<String> a list of the sorted guesses in descending order from best to worst guess
     * @author Rishit
     */
    public List<String> sortGuessesByFeedback() {
        List<String> sortedGuesses = new ArrayList<>();
        // this will sort based on greatest sum as best guess and least sum as worst guess
        int[][] feedbackSums = new int[rows][2];
        for (int i = 1; i <= rows; i++) {
            int[] feedback = check(i);
            feedbackSums[i-1][0] = i;
            feedbackSums[i-1][1] = feedback[0]*2 + feedback[1];
        }
        // sort the feedback sums
        selectionSort(feedbackSums);
        sortedGuesses.add("Sorted Guesses: \n(Descending Order - Best to Worst)\n--------------------------------------");
        sortedGuesses.add("Format: Row #: <guess> →→ <feedback>");
        sortedGuesses.add("Guess: 1-Green, 2-Red, 3-Blue,\n             4-Yellow, 5-White, 6-Black\nFeedback: 2-Black, 1-White. 0-Incorrect\n--------------------------------------");
        // add the sorted guesses to the list
        for (int i = 0; i < rows; i++) {
            String guess = Arrays.toString(guesses[feedbackSums[i][0]]);
            String hint = Arrays.toString(hints[feedbackSums[i][0]]);
            guess = guess.substring(1, guess.length()-1);
            hint = hint.substring(1, hint.length()-1);
            sortedGuesses.add("Row #" + feedbackSums[i][0] + ": " + guess + " →→ " + hint);
        }
        return sortedGuesses;

    }

    /**
     * Sort the guesses by feedback using selection sort
     * The method sorts the guesses in descending order from best to worst guess
     * @param feedbackSums the feedback sums of the guesses
     * @return void
     * @author Rishit
     */
    public void selectionSort(int[][] feedbackSums){
        for (int i = 0; i < rows; i++) {
            int maxIndex = i;
            for (int j = i+1; j < rows; j++) {
                if (feedbackSums[j][1] > feedbackSums[maxIndex][1]) {
                    maxIndex = j;
                }
            }
            // swap the row number and the sum
            int tempRow = feedbackSums[i][0];
            int tempSum = feedbackSums[i][1];
            feedbackSums[i][0] = feedbackSums[maxIndex][0];
            feedbackSums[i][1] = feedbackSums[maxIndex][1];
            feedbackSums[maxIndex][0] = tempRow;
            feedbackSums[maxIndex][1] = tempSum;
        }
    }
}

