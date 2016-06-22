package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController{

    @FXML private PasswordField passwordField;
    @FXML private TextField loginField;
    @FXML private Text version;
    @FXML private Label logoName;

    @FXML public void initialize(){
        version.setText(String.valueOf(SoftwareSpecifications.getVersion()));
        logoName.setText(SoftwareSpecifications.getName());
    }

    @FXML void onLoginClicked(ActionEvent event) throws IOException{
        User user = User.login(loginField.getText(), passwordField.getText());
        if (user != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.initialPath));
            Parent home_page_parent = loader.load();

            InitialController controller = loader.getController();
            controller.setUser(user);

            Scene home_page_scene = new Scene(home_page_parent);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.close();
            main_stage.setScene(home_page_scene);
            main_stage.setTitle(SoftwareSpecifications.getName());
            main_stage.setResizable(true);
            main_stage.setMaximized(true);
            main_stage.show();
        }
        else
        {
            new AlertBox(Dictionary.userPassIncorrect, Dictionary.error, new Alert(Alert.AlertType.WARNING));
        }
    }
}
