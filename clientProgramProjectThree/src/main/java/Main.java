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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/clientWelcomeScene.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Connect Four Client");
            Scene clientScene = new Scene(root, 500, 500);
            clientScene.getStylesheets().add("/styles/styles1.css");
            primaryStage.setScene(clientScene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
