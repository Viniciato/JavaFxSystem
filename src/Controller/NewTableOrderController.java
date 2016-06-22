package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 18/11/15.
 */
public class NewTableOrderController {
    private Order order;
    public void setOrder(Order order) {
        this.order = order;
    }

    @FXML private ComboBox<Table> tables;
    @FXML private AnchorPane window;

    @FXML void onAddTable(ActionEvent event) {
        if (tables.getValue() != null)
        {
            OrderTable orderTable = new OrderTable(order, setTableStatus());
            orderTable.create();
            new AlertBox("Mesa Adicionado Com Sucesso!!", "Concluido!!!", new Alert(Alert.AlertType.INFORMATION));
            window.getScene().getWindow().hide();
        }
        else
            new AlertBox("Você deve selecionar uma mesa para adiconar!!!", "Erro!!!", new Alert(Alert.AlertType.ERROR));
    }

    private Table setTableStatus(){
        Table table = tables.getSelectionModel().getSelectedItem();
        table.setStatus(TableStatus.findById(2));
        table.save();
        return table;
    }

    @FXML public void initialize(){
        setTables();
    }

    public void setTables(){
        ObservableList<Table> allTables = FXCollections.observableArrayList(Table.allNotBusy());
        tables.getItems().addAll(allTables);
    }


}
