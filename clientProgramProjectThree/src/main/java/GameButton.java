
import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.layout.BorderPane;


public class GameButton extends Button {
    private Button button;
    private int row;
    private int col;
    private boolean validMove;

    // @FXML
    // public GridPane gameBoard;

    public GameButton(int r, int c) {
        this.row = r;
        this.col = c;
        this.validMove = true;
    }

    public GameButton(Button button) {
        this.row = 0;
        this.col = 0;
        this.validMove = true;
        this.button = button;
    }

    public Scene getNextScene() {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 650, 450);
        return scene;
    }

    public Scene getPrevScene() {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 650, 450);
        return scene;
    }

    public void setValicMove(boolean validMove) {
        this.validMove = validMove;
    }

    public boolean getValidMove() {
        return this.validMove;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return this.col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
