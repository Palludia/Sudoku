import com.sun.jdi.IntegerValue;

import java.util.Random;

public class Sudoku {
    private int[][] mat;
    private int N; // number of columns/rows
    private int SRN; // Square root of N
    private int K; // Number of Missing digits

    Sudoku(int N, int K){
        this.N = N;
        this.K = K;

        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        mat = new int[N][N];
    }

    public void fillValues(){
        fillDiagonal();

        fillRemaining(0, SRN);

        removeKDigits();
    }

    private boolean unUsedInBox(int rowStart, int colStart, int num){
        for(int i = 0; i<SRN; i++){
            for(int j = 0; j<SRN; j++){
                if(mat[rowStart+i][colStart+j] == num){
                    return false;
                }
            }
        }
        return true;
    }

    private void fillDiagonal() {
        for(int i = 0; i<N; i = i+SRN){
            fillBox(i,i);
        }
    }

    private void fillBox(int row, int col) {
        int num;
        for(int i = 0; i<SRN; i++){
            for(int j = 0; j<SRN; j++){
                do{
                    num = randomGenerator(N);
                }while(!unUsedInBox(row,col,num));
                mat[row+i][col+j] = num;
            }
        }
    }

    private int randomGenerator(int n) {
        return (int) Math.floor((Math.random() * n + 1));
    }

    private boolean checkIfSafe(int i, int j, int num){
        return (unUsedInRow(i,num) && unUsedInCol(j,num) && unUsedInBox(i-i%SRN,j-j%SRN,num));
    }

    private boolean unUsedInCol(int j, int num) {
        for(int i = 0; i<N; i++){
            if(mat[i][j] == num){
                return false;
            }
        }
        return true;
    }

    private boolean unUsedInRow(int i, int num) {
        for(int j = 0; j<N; j++){
            if(mat[i][j] == num){
                return false;
            }
        }
        return true;
    }

    private void removeKDigits() {
        int count = K;
        while(count != 0){
            int cellId = randomGenerator(N*N)-1;

            int i = (cellId/N);
            int j = cellId%N;

            if(mat[i][j] != 0){
                count--;
                mat[i][j] = 0;
            }
        }
    }

    // Recursive Function to fill Remaining
    private boolean fillRemaining(int i, int j) {
        if(j>=N && i <N-1){
            i = i + 1;
            j = 0;
        }
        if(i>=N && j>=N){
            return true;
        }
        if(i < SRN){
            if(j < SRN){
                j = SRN;
            }
        }
        else if(i < N-SRN){
            if(j == (int) (i/SRN)*SRN){
                j = j + SRN;
            }
        }
        else{
            if(j == N-SRN){
                i = i+1;
                j = 0;
                if(i>=N){
                    return true;
                }
            }
        }

        for(int num = 1; num<=N; num++){
            if(checkIfSafe(i,j,num)){
                mat[i][j] = num;
                if(fillRemaining(i,j+1)){
                    return true;
                }
                mat[i][j] = 0;
            }
        }
        return false;
    }

    public void printSudoku(){
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean solveSudoku(int[][] mat, int row, int col){
        N = 9;

        if(row == N - 1 && col == N){
            return true;
        }

        if(col == N){
            row++;
            col = 0;
        }

        if(mat[row][col] != 0){
            return solveSudoku(mat,row,col+1);
        }
        for(int num = 1; num < 10; num++){
            if(isSafe(mat,row,col,num)){
                mat[row][col] = num;

                if(solveSudoku(mat,row,col+1)){
                    return true;
                }
            }

            mat[row][col] = 0;
        }
        return false;
    }

    private boolean isSafe(int[][] mat,int row, int col, int num) {
        for(int x = 0; x<=8; x++){
            if (mat[row][x] == num) return false;
        }
        for(int x = 0; x<=8; x++){
            if(mat[x][col] == num) return false;
        }

        int startRow = row - row % 3, startCol = col - col % 3;

        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(mat[i + startRow][j + startCol] == num) return false;
            }
        }
        return true;
    }

    public int[][] getMat(){
        return mat;
    }
}
