package Controller;

import Model.SoftwareSpecifications;
import Model.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Nadin on 11/11/15.
 */
public class MainController extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Paths.loginPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(SoftwareSpecifications.getName());
        stage.centerOnScreen();
        stage.show();
    }
}
