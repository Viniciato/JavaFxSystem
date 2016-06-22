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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nadin on 13/11/15.
 */
public class EditOrderController {
    private Order order;
    private User user;

    public void setOrder(Order order){
        this.order = order;
        if (order.getCustomer() != null)
            customer.setText(order.getCustomer().getName());
        else
            customer.setText(Dictionary.noRegistry);
        status.getSelectionModel().select(order.getStatus());
        updateItensList();
        updateTables();
        if (this.order.getStatus().getId() != 1){
            status.setDisable(true);
        }
        createdAt.setText(new SimpleDateFormat(Dictionary.dateFormat).format(order.getCreatedAt()));
        timeCreatedAt.setText(order.getCreatedAt().toLocalDateTime().toLocalTime().toString());
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML public void initialize(){
        //setCustomers();
        setStatus();
    }

    @FXML private AnchorPane newOrderPane;
    @FXML private Label createdAt;
    @FXML private AnchorPane showProductsWindow;
    @FXML private Label customer;
    @FXML private TableView<OrderItem> OrderItensList;
    @FXML private Label totalOrder;
    @FXML private ComboBox<OrderStatus> status;
    @FXML private TableView<OrderTable> tables;
    @FXML private Label timeCreatedAt;

    @FXML public void onAddItemOrder() throws IOException{
        addItemOrder();
    }

    public void setCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Customer.all());
        //customers.getItems().addAll(allCustomers);
    }

    @FXML public void onSaveOrder() {
        saveOrder();
        new AlertBox(Dictionary.orderUpdated, Dictionary.completed, new Alert(Alert.AlertType.INFORMATION));
        newOrderPane.getScene().getWindow().hide();
    }

    @FXML void onEditItemOrder(ActionEvent event) throws IOException{
        if (OrderItensList.getSelectionModel().getSelectedIndex() != -1)
            editItemOrder();
        else
            new AlertBox(Dictionary.selectItemEdit, Dictionary.error, new Alert(Alert.AlertType.WARNING));
    }

    @FXML void onDeleteItemOrder(ActionEvent event) {
        deleteItemOrder();
    }

    @FXML void onAddTable(ActionEvent event) throws IOException {
        addTable();
    }

    @FXML void onDeleteTableOrder(ActionEvent event) throws IOException{
        deleteTableOrder();
    }

    private void deleteTableOrder(){
        if( tables.getSelectionModel().getSelectedIndex() != -1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Dictionary.confirmRemoveTable +
                    tables.getSelectionModel().getSelectedItem().getTable().getName(), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            //Delete current record in database
            if(result.get() == ButtonType.YES) {
                OrderTable toRemove = tables.getSelectionModel().getSelectedItem();
                Table table = toRemove.getTable();
                table.setStatus(TableStatus.findById(1));
                table.save();
                toRemove.delete();
                updateTables();
                saveOrder();
            }
        }
        else {
             new AlertBox(Dictionary.selectTableRemove, Dictionary.error, new Alert(Alert.AlertType.WARNING));
        }
    }

    private void deleteItemOrder(){
        if(OrderItensList.getSelectionModel().getSelectedIndex() != -1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Dictionary.confirmRemoveProduct, ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            //Delete current record in database
            if(result.get() == ButtonType.YES) {
                OrderItem toRemove = OrderItensList.getSelectionModel().getSelectedItem();
                toRemove.delete();
                updateItensList();
                saveOrder();
            }
        }
        else {
            new AlertBox(Dictionary.selectItemRemove, Dictionary.error, new Alert(Alert.AlertType.WARNING));
        }
    }

    private void updateTables(){
        ObservableList<OrderTable> oList = FXCollections.observableArrayList(OrderTable.findByOrder(order));
        tables.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("TableName"));
        tables.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("TablePlaces"));
        tables.setItems(oList);
    }

    private void addTable() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.newTableOrderPath));
        Parent addTableOrder_parent = loader.load();
        NewTableOrderController controller = loader.getController();
        controller.setOrder(order);
        Scene addTableOrder_scene = new Scene(addTableOrder_parent);
        Stage addTableOrder_stage = new Stage();
        addTableOrder_stage.setScene(addTableOrder_scene);
        addTableOrder_stage.setTitle(Dictionary.addTableOrder);
        addTableOrder_stage.initModality(Modality.WINDOW_MODAL);
        addTableOrder_stage.initOwner(newOrderPane.getScene().getWindow());
        addTableOrder_stage.setResizable(false);
        addTableOrder_stage.show();
        //Update Order Table after Order be edited.
        addTableOrder_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event){
                saveOrder();
                updateTables();
                verifyCardsAndTables();
            }
        });
    }

    public void saveOrder(){
        order.setUser(user);
        order.setTotal(Double.parseDouble(totalOrder.getText()));
        order.setStatus(status.getSelectionModel().getSelectedItem());
        order.update();
        verifyCardsAndTables();
    }

    private void verifyCardsAndTables(){
        if (order.getStatus().getId() == 2 || order.getStatus().getId() == 3)
        {
            Card card = order.getCard();
            card.setStatus(1);
            card.update();
            ObservableList<OrderTable> oList = FXCollections.observableArrayList(OrderTable.findByOrder(order));
            for (OrderTable orderTables: oList) {
                Table table = orderTables.getTable();
                table.setStatus(TableStatus.findById(1));
                table.save();
            }
        }
        else
        {
            Card card = order.getCard();
            card.setStatus(2);
            card.update();
            ObservableList<OrderTable> oList = FXCollections.observableArrayList(OrderTable.findByOrder(order));
            for (OrderTable orderTables: oList) {
                Table table = orderTables.getTable();
                table.setStatus(TableStatus.findById(2));
                table.save();
            }
        }
    }

    public void addItemOrder()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.newItemOrderPath));
        Parent addItemOrder_parent = loader.load();
        NewItemOrderController controller = loader.getController();
        controller.setOrder(order);
        Scene addItemOrder_scene = new Scene(addItemOrder_parent);
        Stage addItemOrder_stage = new Stage();
        addItemOrder_stage.setScene(addItemOrder_scene);
        addItemOrder_stage.setTitle(Dictionary.addProductOrder);
        addItemOrder_stage.initModality(Modality.WINDOW_MODAL);
        addItemOrder_stage.initOwner(newOrderPane.getScene().getWindow());
        addItemOrder_stage.setResizable(false);
        addItemOrder_stage.show();
        //Update Order Table after Order be edited.
        addItemOrder_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateItensList();
                saveOrder();
            }
        });
    }

    private void editItemOrder()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.editItemOrderPath));
        Parent editItemOrder_parent = loader.load();
        EditItemOrderController controller = loader.getController();
        controller.setItem(OrderItensList.getSelectionModel().getSelectedItem());
        Scene editItemOrder_scene = new Scene(editItemOrder_parent);
        Stage editItemOrder_stage = new Stage();
        editItemOrder_stage.setScene(editItemOrder_scene);
        editItemOrder_stage.setTitle(Dictionary.editItemOrder);
        editItemOrder_stage.initModality(Modality.WINDOW_MODAL);
        editItemOrder_stage.initOwner(newOrderPane.getScene().getWindow());
        editItemOrder_stage.setResizable(false);
        editItemOrder_stage.show();
        //Update Order Table after Order be edited.
        editItemOrder_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateItensList();
                saveOrder();
            }
        });
    }

    private void updateItensList() {
        OrderItensList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("product"));
        OrderItensList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("amount"));
        OrderItensList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        OrderItensList.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("total"));
        ObservableList<OrderItem> oList = FXCollections.observableArrayList(OrderItem.allByOrder(order));
        OrderItensList.setItems(oList);
        setTotal();
    }

    public void setTotal(){
        List<OrderItem> orders = OrderItem.allByOrder(order);
        Double totalItens = 0d;
        for (OrderItem order: orders) {
            totalItens = totalItens + order.getTotal();
        }
        totalOrder.setText(String.valueOf(totalItens));
    }

    private void setStatus(){
        ObservableList<OrderStatus> allStatus = FXCollections.observableArrayList(OrderStatus.all());
        status.getItems().addAll(allStatus);
    }

}
