package Chess.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import Chess.controller.ChessController;

public class ChessApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("ChessView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/ChessView.fxml"));
      // Parent root = loader.load();
        //primaryStage.setTitle("Chess Game");
       // primaryStage.setScene(new Scene(root));
        //primaryStage.show();

        Scene scene = new Scene(root);

        stage.setTitle("Chess Game");

        stage.setResizable(false);
        // stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    }


}