
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientController implements Initializable {

    @FXML
    private AnchorPane clientWonScene;

    @FXML
    private Button enterBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button playAgainBtn;

    @FXML
    private Text playerWon;

    @FXML
    private AnchorPane clientWelcomeScene;

    @FXML
    private Button ip1;


    @FXML
    private Button port55;

    @FXML
    private Button port56;

    @FXML
    private Button port57;

    @FXML
    private Text welcomeText;

    @FXML
    private AnchorPane clientPlayScene;

    @FXML
    public GridPane gameBoardPane;

    @FXML
    public ListView<String> gameListView;

    @FXML
    public Text playerTurn;

    private static Client clientView;

    private static String ipAddress;
    private static String portNumber;
    CFourInfo info;

    ClientController controller;
    Parent root;

    @FXML
    void guideBtnMethod(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

    
    // MENU BAR

    // Exit Game
    public void exitBtnMethod(ActionEvent event) throws IOException {
        clientView.socketClient.close();
        Platform.exit();
        //System.exit(0);
    }

    // Pause 
    public void pauseBtnMethod(ActionEvent event) throws IOException {
        disableButtons();
        //gameListView.getItems().add("Game Paused");
    }

    // Resume
    public void resumeBtnMethod(ActionEvent event) throws IOException {
        if ((info.whichPlayer == 1 && info.getCurrentPlayer() == 1)
                || (info.whichPlayer == 2 && info.getCurrentPlayer() == 2))
        enableButtons();
        gameListView.getItems().add("Game Resumed");
    }

    // Play Again button
    public void playAgainBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/clientPlayScene.fxml"));
        root = loader.load();
        clientWelcomeScene.getChildren().setAll(root);
        controller = loader.getController();
        controller.updateClients();
        controller.addGameButtons();
        info.setCurrentPlayer(1);
        info.gameStatus = 3;
        info.clearBoard();
        clientView.send(info);
    }

    // Cancel button
    public void cancelBtnMethod(ActionEvent event) throws IOException {
        port55.setDisable(false);
        port56.setDisable(false);
        port57.setDisable(false);
        ip1.setDisable(false);
    }

    // Enter button
    public void enterBtnMethod(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/clientPlayScene.fxml"));
        root = loader.load();
        clientWelcomeScene.getChildren().setAll(root);
        controller = loader.getController();
        controller.updateClients();
        controller.addGameButtons();
    }

    public void port55Method(ActionEvent event) throws IOException {
        portNumber = "5555";
        port56.setDisable(true);
        port57.setDisable(true);
    }

    public void port56Method(ActionEvent event) throws IOException {
        portNumber = "5556";
        port55.setDisable(true);
        port57.setDisable(true);
    }

    public void port57Method(ActionEvent event) throws IOException {
        portNumber = "5557";
        port55.setDisable(true);
        port56.setDisable(true);
    }

    public void ip1Method(ActionEvent event) throws IOException {
        ipAddress = "127.0.0.1";
    }

    
    public Node getNodeByRowCol(int row, int col) {

        for (Node node : gameBoardPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                return node;
        }

        return null;
    }

    public void disableButtons(){
        for(Node node : gameBoardPane.getChildren()){
            node.setDisable(true);
        }
    }

    public void enableButtons(){
        for(Node node : gameBoardPane.getChildren()){
            node.setDisable(false);
        }
    }

    public void highlightNodes(ArrayList<ArrayList<Integer>> list){
        for(int i = 0; i < 4; i++){
            Node node = getNodeByRowCol(list.get(i).get(0), list.get(i).get(1));
           // System.out.println("first : " + list.get(i).get(0) + " second: " + list.get(i).get(1));
            node.setStyle("-fx-background-radius: 50px; -fx-background-color: #2F5C7B;");
        }
    }

    public void timeDelay(long t){
        try {
            TimeUnit.MILLISECONDS.sleep(t);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void updateClients() {
        clientView = new Client(portNumber, ipAddress, callData -> {
            Platform.runLater(() -> {
                info = (CFourInfo) callData;

                System.out.println("winning coordinate is empty" + info.winningCoordinates.isEmpty());
            
                // initially we disable both board
                disableButtons();

                // then we are going to take the client, more like, info.whichPlayer to take the client
                // the condition is always right for info.whichPlayer then we && it with the info.getCurrentPlayer()
                // so it will just enable for ONE client ONLY
                if((info.whichPlayer == 1 && info.getCurrentPlayer() == 1) || (info.whichPlayer == 2 && info.getCurrentPlayer() == 2))
                    enableButtons();
                if(info.whichPlayer == 1){
                    playerTurn.setText("Player " + info.whichPlayer+ " (GREEN)");
                }
                else if (info.whichPlayer == 2){
                    playerTurn.setText("Player " + info.whichPlayer + " (RED)");
                }
                
                if (info.gameStatus == 0) {
                    gameBoardPane.setDisable(true);
                    gameListView.getItems().add("Waiting for another player to join...");

                } 
                
                else {
                    gameBoardPane.setDisable(false);
                    gameListView.getItems().clear();
                    gameListView.getItems().add("GAME START!");
                    gameListView.getItems().add("Player 1 turn");
                    
                }

                Node cur = getNodeByRowCol(info.row, info.col);
                if (info.getCurrentPlayer() == 1 && info.gameStatus == 1) {
                    gameListView.getItems().clear();
                    gameListView.getItems().add("Player 1 turn");
                    if(info.whichPlayer == 1)
                    gameListView.getItems().add("Player 2 moved at (" + info.row + ", " + info.col + ")");
                    //gameListView.getItems().add("You color is GREEN");
                    cur.setStyle("-fx-background-radius: 50px; -fx-background-color: #ff686b;");
                  
                } else if (info.getCurrentPlayer() == 2 && info.gameStatus == 1) {
                    gameListView.getItems().clear();
                    gameListView.getItems().add("Player 2 turn");
                    //gameListView.getItems().add("You color is RED");
                    if(info.whichPlayer == 2)
                    gameListView.getItems().add("Player 1 moved at (" + info.row + ", " + info.col + ")");

                    cur.setStyle("-fx-background-radius: 50px; -fx-background-color: #84dcc6;");
                    
                }
                
                if((info.whichPlayer == 1 && info.getPlayer1Win()) || (info.whichPlayer == 2 && info.getPlayer2Win())){
                    //System.out.println("Conratulation, you won!");               
                    gameListView.getItems().add("Conratulation, you won!");
                    System.out.println(info.winningCoordinates);
                   // disableButtons();
                   
                    // display the clientWonScene

                   // highlightNodes(info.winningCoordinates);
    


                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/clientWonScene.fxml"));

                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    clientPlayScene.getChildren().setAll(root);
                    controller = loader.getController();
                    controller.playerWon.setText("PLAYER " + info.whichPlayer + " WON!");
                    System.out.println(info.winningCoordinates);

                     
                });
                pause.play();
                highlightNodes(info.winningCoordinates);
                disableButtons();

                    
                }
                else if(info.getPlayer1Win() || info.getPlayer2Win()){
                    gameListView.getItems().add("You lost!");                
                
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/clientWonScene.fxml"));

                   try {
                    root = loader.load();
                   
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    clientPlayScene.getChildren().setAll(root);
                    controller = loader.getController();      
                    controller.playerWon.setText("PLAYER " + info.whichPlayer + " LOST!");   
                  
                });
                pause.play();
                highlightNodes(info.winningCoordinates);
                disableButtons();
                
                }
                else if(info.gameDraw == true){
                    gameListView.getItems().add("Game Draw!");

                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/clientTieScene.fxml"));
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    clientPlayScene.getChildren().setAll(root);
                    controller = loader.getController();
                });
                pause.play();
                disableButtons();
            }
                
                else if (info.gameStatus == 4) {
                    //System.out.println("game status is : " + info.gameStatus);
                    gameListView.getItems().add("A player left the game!");
                    disableButtons();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/clientExitScene.fxml"));
                        root = loader.load();
                        clientPlayScene.getChildren().setAll(root);
                        controller = loader.getController();
                        // controller.playerWon.setText("!! PLAYER TIE !!");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        });
        clientView.start();
    }

    public void sendData(ActionEvent e) {
        GameButton button = (GameButton) e.getSource();
       // System.out.println(info.getCurrentPlayer());
        info.moveCount++;
        
        if (info.gameBoard[info.row][info.col] == 0) {
            if (info.getCurrentPlayer() == 1) {
               // System.out.println("Player ONE MOVED");
                info.gameStatus = 1;
                info.setMove(info.row, info.col, 1);
                info.setCurrentPlayer(2);
                button.setStyle("-fx-background-radius: 50px; -fx-background-color: #84dcc6;");   
                clientView.send(info);
            }
            else if (info.getCurrentPlayer() == 2) {
                //System.out.println("Player TWO MOVED");
                info.gameStatus = 1;
                info.setMove(info.row, info.col, 2);
                info.setCurrentPlayer(1);
                button.setStyle("-fx-background-radius: 50px; -fx-background-color: #ff686b;");
                clientView.send(info);
            }
        }
    }

    int r, c;
    public void addGameButtons() throws IOException {
        for (r = 0; r < 6; r++) {
            for (c = 0; c < 7; c++) {
                GameButton button = new GameButton(r, c);
                button.setPrefSize(40, 40);
                button.setStyle("-fx-background-radius: 50px; -fx-background-color: #e5e5e5;");
                button.setOnAction(e -> {
                   // System.out.println("Clicked" + r + c);

                    if(info.isValidMove(button.getRow(), button.getCol())) {
                        info.setMove(button.getRow(), button.getCol());
                        //info.count++;
                        //System.out.println("play count is " + info.count);
                        sendData(e);
                    } else {
                        //System.out.println("Invalid Move!");
                        gameListView.getItems().clear();
                        gameListView.getItems().add("Invalid Move!");
                    }
                });
                gameBoardPane.add(button, c, r);
            }
        }
       
    }

    
}
