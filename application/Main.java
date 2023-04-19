package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	Parent root = FXMLLoader.load(getClass().getResource("javatest.fxml"));
        primaryStage.setTitle("Emist");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("images/emist.png"));
        primaryStage.show();
        // textArea.setVisible(false);
    } 

    public static void main(String[] args) {
        launch(args);
    }
}

