import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        try {
            Parent serverRoot = FXMLLoader.load(getClass().getResource("/FXML/serverWelcomeScene.fxml"));

            primaryStage.setTitle("Connect Four Server");
            Scene serverScene = new Scene(serverRoot, 500, 500);
            serverScene.getStylesheets().add("/styles/styles1.css");
            primaryStage.setScene(serverScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
