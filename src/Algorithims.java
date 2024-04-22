// This is only to be used as framework code for the game
// Java class will prob and should not be utilized : )
import java.util.*;

public class Algorithims {
    private int[] key;
    private int[][] guesses;
    private int[][] hints;
    public final int rows = 4;
    public final int col = 12;
    
    public Algorithims(){
        key = new int[rows];
        guesses = new int[col][rows];
        hints = new int [col][rows];
        for (int i = 0; i < rows; i++){
            key[i] = (int)( Math.random()* 6 + 1);
        }
        for (int y = 0; y < col; y++){
            for (int x = 0; x < rows; x++){
                guesses[y][x] = 0;
                hints[y][x] = 0;
            }
        }
    }
    public int getCol(){
        return col;
    }

    public int getRows(){
        return rows;
    }

    public void setColor(int x, int y, int num){
        guesses[y][x] = num;
    }

    public int[] getKey(){
        return key;
    }

    public int[][] getGuesses(){
        return guesses;
    }

    public String getBoard(){
        String board = "";
        for (int y = 0; y < col; y++){
            board += Arrays.toString(giveHints(y)) + " | ";
            board += Arrays.toString(guesses[y]) +"\n";
        }

        return board;
    }
    public boolean blackCheck(int y, int x){
        if (guesses[y][x] == key[x]){
            return true;
        }
        return false;
    }



    public int whiteCheck(int y, int x, int[] compare){

        for (int i = 0; i < rows; i ++){
            if (guesses[y][x] == compare[i]){

                return i;
            }
        }
        return -1;
    }

    public int[] check(int y){
        int[] hintPegs = {0,0};

        int[] commonColor = key.clone();
        for (int x = 0; x < rows; x++){
            if (blackCheck(y,x)){
                hintPegs[0]++;
            } else if (whiteCheck(y,x, commonColor) != -1){
                hintPegs[1]++;
                commonColor[whiteCheck(y,x, commonColor)] = 0;
            }
        }
        System.out.println(hintPegs[0] + " " + hintPegs[1]);
        return hintPegs;
    }
    public int[] giveHints(int y){
        int blacks = check(y)[0];
        int whites = check(y)[1];
        //blacks will go first just cus
        int i = 0;

        while (blacks > 0){
            hints[y][i] = 1;
            blacks--;
            i++;
        }

       while (whites > 0){
            hints[y][i] = 2;
            whites--;
            i++;
       }
        
        return hints[y];
    }

    
}

