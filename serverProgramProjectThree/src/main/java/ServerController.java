import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ServerController implements Initializable {

    @FXML
    private Button enterBtn;

    @FXML
    private MenuItem exitServerBtn;

    @FXML
    private MenuItem newServerBtn;

    @FXML
    private Button port55;

    @FXML
    private Button port56;

    @FXML
    private Button port57;

    @FXML
    private Button cancelBtn;

    @FXML
    private MenuBar serverMenu;

    @FXML
    private AnchorPane serverWelcomeScene;

    @FXML
    private Text welcomeText;

    @FXML
    public Text serverNum;

    @FXML
    public Text numConnected;

    @FXML
    public ListView<String> serverListView;

    @FXML
    private AnchorPane serverMainScene;

    static Server server;
    private String portNumber;
    private String ipAddress;
    private Integer numPlayers = 0;

    @FXML
    void exitServerBtnMethod(ActionEvent event) {

    }

    @FXML
    void newServerBtnMethod(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void enterBtnMethod(ActionEvent event) throws IOException {
        // Move to serverMainScene
        AnchorPane pane;
        try {
            // Initiate a loader for the new FXML template
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serverMainScene.fxml"));
            loader.setController(this); // Set this controller instance to overlapped and avoid deleting properties
            pane = loader.load(); // And loader for FXML MainScene
            serverWelcomeScene.getChildren().setAll(pane); // Both templates files share the same controller
            serverNum.setText("Server #" + portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server = new Server(portNumber, data -> {
            Platform.runLater(() -> {
                serverNum.setText(data.toString());
            });
        }, data2 -> {
            Platform.runLater(() -> {
                numConnected.setText(data2.toString());
            });
        }, data3 -> {
            Platform.runLater(() -> {
                serverListView.getItems().add(data3.toString());
            });
        });
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

    public void cancelBtnMethod(ActionEvent event) throws IOException {
        port55.setDisable(false);
        port56.setDisable(false);
        port57.setDisable(false);
    }

}
