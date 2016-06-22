package Controller;

import Model.AlertBox;
import Model.Paths;
import Model.Table;
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

public class ManageTablesController {

    private Stage saveTable_stage;

    @FXML private TableView<Table> tables;
    @FXML private TextField searchTable;
    @FXML private AnchorPane window;

    @FXML public void initialize(){
        updateTables();
        onSearchTable();
    }

    @FXML void onAllTables(ActionEvent event) {
        updateTables();
    }

    @FXML void onNewTable(ActionEvent event) throws IOException{
        tableActions(null, "Mesa Criada com Sucesso!!", "Nova Mesa");
    }

    @FXML void onEditTable(ActionEvent event) throws IOException{
        if (tables.getSelectionModel().getSelectedItem() !=null)
            tableActions(tables.getSelectionModel().getSelectedItem(), "Mesa Atualizada com Sucesso!!", "Editar Mesa");
        else
            new AlertBox("Você deve selecionar uma Mesa para editar!!", "Erro", new Alert(Alert.AlertType.ERROR));
    }

    @FXML void onDeleteTable(ActionEvent event) {
        deleteTable();
    }

    private void tableActions(Table table, String text, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveTablePath));
        Parent saveTable_parent = loader.load();
        SaveTableController controller = loader.getController();
        if (table != null){
            controller.setTable(table);
            controller.setTitle(title);
        }
        controller.setHeader(text);
        saveTable_stage = new Stage();
        Scene saveTable_scene = new Scene(saveTable_parent);
        saveTable_stage.setScene(saveTable_scene);
        saveTable_stage.setTitle(title);
        saveTable_stage.initModality(Modality.WINDOW_MODAL);
        saveTable_stage.initOwner(window.getScene().getWindow());
        saveTable_stage.setResizable(false);
        saveTable_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateTables();
            }
        });
        saveTable_stage.show();
    }

    private void deleteTable(){
        if (tables.getSelectionModel().getSelectedItem() == null) {
            new AlertBox("Você deve selecionar uma mesa antes!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja deletar a mesa "
                    + tables.getSelectionModel().getSelectedItem().getName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Table table = tables.getSelectionModel().getSelectedItem();
                table.delete();
                new AlertBox("Mesa Excluida Com Sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
                updateTables();
            }
        }
    }

    private void updateTables(){
        tables.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tables.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("places"));
        tables.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Table> oList = FXCollections.observableArrayList(Table.all());
        tables.setItems(oList);
    }

    private void onSearchTable(){
        searchTable.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = searchTable.getText();
                    tables.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    tables.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("places"));
                    tables.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));
                    ObservableList<Table> oList = FXCollections.observableArrayList(Table.findByName(name));
                    tables.setItems(oList);
                }
                else
                    updateTables();
            }
        });
    }

}
