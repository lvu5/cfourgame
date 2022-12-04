import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class CFourInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentPlayer; // 1 = player 1, 2 = player 2, this variable will switch constantly
    public int whichPlayer; // this does not
    public int row;
    public int col;
    public int gameStatus; // 0 is not started, 1 is in progress, 2 is finished, 3 is ready to play, 4 is one player exit
    public static int count = 0; 
    public int moveCount = 0;
    public boolean player1Win = false;
    public boolean player2Win = false;
    public boolean gameDraw = false;
    public ArrayList<ArrayList<Integer>> winningCoordinates = new ArrayList<ArrayList<Integer>>();

    public String message;

    // int row = 5, column = 3;
    int[][] gameBoard = {
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 }
    }; // 0 = empty, 1 = client 1, 2 = client 2

    public int playerNum;

    public void clearBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gameBoard[i][j] = 0;
            }
        }
    }

    CFourInfo() {
        this.currentPlayer = CFourInfo.count;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setMove(int x, int y, int currentPlayer) {
        this.row = x;
        this.col = y;
        this.gameBoard[x][y] = currentPlayer;
    }
    public void setMove(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public boolean checkMove(int x, int y) {
        if (this.gameBoard[x][y] == 0) {
            return true;
        }
        return false;
    }

    public int[] getMove() {
        int[] move = { this.row, this.col };
        return move;
    }

    public void increaseCount() {
        CFourInfo.count++;
    }

    public void decreaseCount() {
        CFourInfo.count--;
    }

    public int getCount() {
        return CFourInfo.count;
    }

    public String testMes() {
        return "test";
    }

    public void setPlayer1Win(boolean b){
        this.player1Win = b;
    }

    public void setPlayer2Win(boolean b){
        this.player2Win = b;
    }

    public boolean getPlayer1Win(){
        return player1Win;
    }

    public boolean getPlayer2Win(){
        return player2Win;
    }



    public boolean isValidMove(int r, int c) {
        // bound check
        if (c > 6 || c < 0 || r > 5 || r < 0)
            return false;
        // case where position is occupied
        if (r == 5) {
            if (this.gameBoard[r][c] == 0) {
                return true;
            }
        } else {
            if (this.gameBoard[r + 1][c] != 0)
                return true;
        }
        return false;
    }

    public boolean areFourConnected(int player) {
        // horizontalCheck
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 6; i++) {
                if (this.gameBoard[i][j] == player && this.gameBoard[i][j + 1] == player
                        && this.gameBoard[i][j + 2] == player && this.gameBoard[i][j + 3] == player) {
                                winningCoordinates.add(0, new ArrayList<>(Arrays.asList(i, j)));
                                winningCoordinates.add(1, new ArrayList<>(Arrays.asList(i, j + 1)));
                                winningCoordinates.add(2, new ArrayList<>(Arrays.asList(i, j + 2)));
                                winningCoordinates.add(3, new ArrayList<>(Arrays.asList(i, j + 3)));
                    return true;
                
                }
            }
        }
        // verticalCheck
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.gameBoard[i][j] == player && this.gameBoard[i + 1][j] == player
                        && this.gameBoard[i + 2][j] == player && this.gameBoard[i + 3][j] == player) {
                            winningCoordinates.add(0, new ArrayList<>(Arrays.asList(i, j)));
                            winningCoordinates.add(1, new ArrayList<>(Arrays.asList(i + 1, j)));
                            winningCoordinates.add(2, new ArrayList<>(Arrays.asList(i + 2, j)));
                            winningCoordinates.add(3, new ArrayList<>(Arrays.asList(i + 3, j)));
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.gameBoard[i][j] == player && this.gameBoard[i - 1][j + 1] == player
                        && this.gameBoard[i - 2][j + 2] == player && this.gameBoard[i - 3][j + 3] == player){
                        winningCoordinates.add(0, new ArrayList<>(Arrays.asList(i, j)));
                        winningCoordinates.add(1, new ArrayList<>(Arrays.asList(i - 1, j + 1)));
                        winningCoordinates.add(2, new ArrayList<>(Arrays.asList(i - 2, j + 2)));
                        winningCoordinates.add(3, new ArrayList<>(Arrays.asList(i - 3, j + 3)));
                    return true;
                }
            }
        }
        // descendingDiagonalCheck
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 7; j++) {
                if (this.gameBoard[i][j] == player && this.gameBoard[i - 1][j - 1] == player
                        && this.gameBoard[i - 2][j - 2] == player && this.gameBoard[i - 3][j - 3] == player){
                        winningCoordinates.add(0, new ArrayList<>(Arrays.asList(i, j)));
                        winningCoordinates.add(1, new ArrayList<>(Arrays.asList(i - 1, j - 1)));
                        winningCoordinates.add(2, new ArrayList<>(Arrays.asList(i - 2, j - 2)));
                        winningCoordinates.add(3, new ArrayList<>(Arrays.asList(i - 3, j - 3)));
                    return true;
                        }
            }
        }
        return false;
    }

    // public static void main(String args[]){
    //     CFourInfo info = new CFourInfo();
    //     System.out.println(info.areFourConnected(1)); 
    //     System.out.println(info.winningCoordinates.get(0).get(0));
    // }
}


