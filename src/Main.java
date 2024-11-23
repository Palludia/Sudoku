import java.util.Random;

public class Main{
    public static void main(String[] args){
        Sudoku newBoard = new Sudoku(9,50);
        newBoard.fillValues();
        newBoard.printSudoku();
        newBoard.solveSudoku(newBoard.getMat(), 0,0);
        newBoard.printSudoku();
    }
}