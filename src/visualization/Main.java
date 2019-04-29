package visualization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("RulesVisualization.fxml"));
        primaryStage.setTitle("MultiscaleModelling");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setWidth(700);
        primaryStage.setHeight(700);
        primaryStage.setResizable(true);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
