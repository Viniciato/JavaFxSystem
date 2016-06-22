package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by Nadin on 24/11/15.
 */
public class ShowOtherOrdersController {
    private User user;
    @FXML private ComboBox<OrderStatus> choiceStatus;
    @FXML private ComboBox<Customer> choiceCustomer;
    @FXML private AnchorPane window;
    @FXML private TableView<Order> tableOrders;

    @FXML void initialize(){
        setStatus();
        setCustomers();
        setAllOrders();
    }

    @FXML void listAll(ActionEvent event) {
        choiceStatus.getSelectionModel().clearSelection();
        choiceCustomer.getSelectionModel().clearSelection();
        setAllOrders();
    }

    @FXML void listWithCustomer(ActionEvent event) {
        if (choiceCustomer.getSelectionModel().getSelectedItem()!=null){
            choiceStatus.getSelectionModel().clearSelection();
            Customer customer = choiceCustomer.getSelectionModel().getSelectedItem();
            tableOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("card"));
            tableOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("TableName"));
            tableOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customer"));
            tableOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
            tableOrders.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
            ObservableList<Order> oList = FXCollections.observableArrayList(Order.allWithCustomer(customer));
            tableOrders.setItems(oList);
        }
    }

    @FXML void listWithStatus(ActionEvent event) {
        if (choiceStatus.getSelectionModel().getSelectedItem()!=null){
            choiceCustomer.getSelectionModel().clearSelection();
            OrderStatus status = choiceStatus.getSelectionModel().getSelectedItem();
            tableOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("card"));
            tableOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("TableName"));
            tableOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customer"));
            tableOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
            tableOrders.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
            ObservableList<Order> oList = FXCollections.observableArrayList(Order.allWithStatus(status));
            tableOrders.setItems(oList);
        }
    }

    @FXML void onEditOrder() throws IOException{
        if (tableOrders.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.editOrderPath));
            Parent parent = loader.load();
            EditOrderController controller = loader.getController();
            controller.setOrder(tableOrders.getSelectionModel().getSelectedItem());
            controller.setUser(user);
            Stage stage = new Stage();
            Scene editOrder_scene = new Scene(parent);
            stage.setScene(editOrder_scene);
            stage.setTitle("Editar Ordem");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(window.getScene().getWindow());
            stage.setResizable(false);
            stage.show();
            //Update Order Table after Order be edited.
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    setAllOrders();
                }
            });
        }
        else
            new AlertBox("Você deve Selecionar um Pedido para Editar!!", "Erro", new Alert(Alert.AlertType.WARNING));
    }

    private void setStatus(){
        ObservableList<OrderStatus> allStatus = FXCollections.observableArrayList(OrderStatus.all());
        choiceStatus.getItems().addAll(allStatus);
        choiceStatus.getItems().remove(0);
    }

    private void setCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Customer.all());
        choiceCustomer.getItems().addAll(allCustomers);
    }

    private void setAllOrders(){
        tableOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("card"));
        tableOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("TableName"));
        tableOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customer"));
        tableOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
        tableOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("createdAtOrganized"));
        tableOrders.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        ObservableList<Order> oList = FXCollections.observableArrayList(Order.all());
        tableOrders.setItems(oList);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
