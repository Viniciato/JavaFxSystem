package Controller;

import Model.AlertBox;
import Model.Paths;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

public class ManageUsersController {
    private User currentUser;
    private User user;
    @FXML private TextField searchUser;
    @FXML private AnchorPane window;
    @FXML private TableView<User> users;
    @FXML private MenuBar userMenuBar;

    @FXML void initialize(){
        allUsers();
        onSearchUser();
    }


    @FXML void onNewUser(ActionEvent event) throws IOException{
        User user = null;
        userActions(user, "Usuário criado com Sucesso!!", "Novo Usuario");
    }

    @FXML void onEditUser(ActionEvent event) throws IOException{
        if (users.getSelectionModel().getSelectedItem() !=null) {
            userActions(users.getSelectionModel().getSelectedItem(), "Usuario Atualizado com Sucesso!!", "Editar Usuario");
        }else
            new AlertBox("Você deve selecionar um Usuario para editar!!", "Erro", new Alert(Alert.AlertType.ERROR));
    }

    private void userActions(User user, String text, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveUserPath));
        Parent parent = loader.load();
        SaveUserController controller = loader.getController();
        controller.setSettings(userMenuBar, currentUser, window);
        if (user != null){
            controller.setUser(user);
            controller.setTitle(title);
        }
        controller.setHeader(text);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window.getScene().getWindow());
        stage.setResizable(false);
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                allUsers();
            }
        });
        stage.show();
    }

    @FXML void onDeleteUser(ActionEvent event) {
        if(users.getSelectionModel().getSelectedIndex() != -1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja deletar o Usuario: " +
                    users.getSelectionModel().getSelectedItem().getName(), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.YES) {
                User user = users.getSelectionModel().getSelectedItem();
                user.delete();
                allUsers();
            }
        }
        else {
            new AlertBox("Você deve Selecionar um Usuario para Excluir!!", "Erro", new Alert(Alert.AlertType.WARNING));
        }
    }

    private void onSearchUser(){
        searchUser.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = searchUser.getText();
                    ObservableList<User> usersList = FXCollections.observableArrayList(User.findByName(name));
                    users.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    users.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cpf"));
                    users.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("access"));
                    users.setItems(usersList);
                }
                else
                    allUsers();
            }
        });
    }

    private void allUsers(){
        ObservableList<User> usersList = FXCollections.observableArrayList(User.all());
        users.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        users.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cpf"));
        users.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("access"));
        users.setItems(usersList);
    }

    public void setSettings(MenuBar userMenuBar, User user) {
        this.userMenuBar = userMenuBar;
        currentUser = user;
    }
}
