package Controller;

import Model.AlertBox;
import Model.State;
import Model.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Nadin on 19/11/15.
 */
public class ManageStatesController {

    private Stage saveState_stage;

    @FXML private TableView<State> statesTable;
    @FXML private TextField stateName;
    @FXML private AnchorPane window;

    @FXML
    public void initialize() {
        updateStates();
        searchState();
    }

    @FXML
    void onNewState(ActionEvent event) throws IOException{
        stateActions(null, "Estado Criado com Sucesso!!", "Novo Estado");
    }

    @FXML
    void onEditState(ActionEvent event) throws IOException{
        if (statesTable.getSelectionModel().getSelectedItem() !=null)
            stateActions(statesTable.getSelectionModel().getSelectedItem(), "Estado Atualizado com Sucesso!!", "Editar Estado");
        else
            new AlertBox("Você deve selecionar um estado para editar!!", "Erro", new Alert(Alert.AlertType.ERROR));
    }

    @FXML
    void onDeleteState(ActionEvent event) {
        deleteState();
    }

    private void deleteState(){
        if (statesTable.getSelectionModel().getSelectedItem() == null) {
            new AlertBox("Você deve selecionar um estado antes!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja deletar o estado "
                    + statesTable.getSelectionModel().getSelectedItem().getName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                State state = statesTable.getSelectionModel().getSelectedItem();
                state.delete();
                new AlertBox("Estado Excluido Com Sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
                updateStates();
            }
        }
    }

    private void stateActions(State state, String text, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveStatePath));
        Parent saveState_parent = loader.load();
        SaveStateController controller = loader.getController();
        if (state != null){
            controller.setState(state);
            controller.setTitle(title);
        }
        controller.setHeader(text);
        saveState_stage = new Stage();
        Scene saveState_scene = new Scene(saveState_parent);
        saveState_stage.setScene(saveState_scene);
        saveState_stage.setTitle(title);
        saveState_stage.initModality(Modality.WINDOW_MODAL);
        saveState_stage.initOwner(window.getScene().getWindow());
        saveState_stage.setResizable(false);
        saveState_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateStates();
            }
        });
        saveState_stage.show();
    }

    private void updateStates() {
        statesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        statesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("acronym"));
        ObservableList<State> oList = FXCollections.observableArrayList(State.all());
        statesTable.setItems(oList);
    }

    private void searchState(){
        stateName.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = text.getText();
                    statesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    statesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("acronym"));
                    ObservableList<State> oList = FXCollections.observableArrayList(State.findByName(name));
                    statesTable.setItems(oList);
                }
                else
                    updateStates();
            }
        });
    }
}
