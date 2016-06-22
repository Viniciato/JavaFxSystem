package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SaveUserController {

    private String text;
    private int id;
    private User currentUser;

    @FXML private MenuBar userMenuBar;
    @FXML private TextField password;
    @FXML private TextField access;
    @FXML private TextField name;
    @FXML private TextField cpf;
    @FXML private TextField salary;
    @FXML private AnchorPane window;
    @FXML private AnchorPane windowManage;
    @FXML private Label title;


    @FXML void onSave(ActionEvent event) throws IOException {
        if (name.getText().isEmpty()) {
            new AlertBox("O Nome Do Usuario não deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else if (cpf.getText().isEmpty()) {
            new AlertBox("O CPF Do Usuario não deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else if (cpf.getText().length() != 11) {
            new AlertBox("O CPF Do Usuario Deve Conter 11 Numeros", "Erro", new Alert(Alert.AlertType.ERROR));
        } else if (salary.getText().isEmpty()) {
            new AlertBox("O Salario Do Usuario não Deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else if (password.getText().isEmpty()) {
            new AlertBox("A Senha Do Usuario não Deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else if (access.getText().isEmpty()) {
            new AlertBox("O Acesso Do Usuario não Deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else if (currentUser.verifyCpf(cpf.getText())){
            new AlertBox("Este CPF ja esta registrado no sistema!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else {
            try {
                Long.parseLong(cpf.getText());
            }catch (RuntimeException e){
                new AlertBox("O campo CPF deve ser numero!!", "Erro", new Alert(Alert.AlertType.ERROR));
                return;
            }
            User user = new User(name.getText(), password.getText(), cpf.getText(),
                    Double.parseDouble(salary.getText()), Integer.parseInt(access.getText()));
            if (String.valueOf(id)!=null)
                user.setId(id);
            try {
                user.save();
                new AlertBox(text, "Concluido", new Alert(Alert.AlertType.INFORMATION));
                if (currentUser.getId() == user.getId())
                {
                    new AlertBox("Você foi Deslogado para efetuar as alteracoes!", "Logout", new Alert(Alert.AlertType.INFORMATION));
                    Parent login_page_parent = FXMLLoader.load(getClass().getResource(Paths.loginPath));
                    Scene login_page_scene = new Scene(login_page_parent);
                    Stage stage = (Stage) userMenuBar.getScene().getWindow();
                    stage.setScene(login_page_scene);
                    stage.setTitle(SoftwareSpecifications.getName());
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.show();
                    windowManage.getScene().getWindow().hide();
                }
                window.getScene().getWindow().hide();
            } catch (InvalidCpfException e) {
                new AlertBox(e.getMessage(), "Erro", new Alert(Alert.AlertType.ERROR));
            } catch (NumberFormatException e) {
                new AlertBox("Os Campos Acesso e Salario Devem ser numeros!!", "Erro", new Alert(Alert.AlertType.ERROR));
            }
        }
    }

    public void setUser(User user){
        password.setText(user.getPassword());
        access.setText(String.valueOf(user.getAccess()));
        name.setText(user.getName());
        cpf.setText(String.valueOf(user.getCpf()));
        salary.setText(String.valueOf(user.getSalary()));
        id = user.getId();
    }

    public void setTitle(String title){
        this.title.setText(title);
        this.title.setAlignment(Pos.TOP_LEFT);
    }

    public void setHeader(String headerText){
        text = headerText;
    }

    public void setSettings(MenuBar userMenuBar, User user, AnchorPane window) {
        this.userMenuBar = userMenuBar;
        currentUser = user;
        this.windowManage = window;
    }
}
