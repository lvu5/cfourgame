import javafx.scene.layout.GridPane;

public class GameBoard extends GridPane {
    CFourInfo info;
    // Create a gameBoard with 42 GameButton
    public GameBoard() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                GameButton button = new GameButton(r, c);
                button.setPrefSize(40, 40);
                button.setStyle("-fx-background-radius: 50px; -fx-background-color: #e5e5e5;");
                this.add(button, c, r);
            }
        }
    }
   
    public GameButton getButton(int r, int c) {
        return (GameButton) this.getChildren().get(r * 7 + c);
    }

}
