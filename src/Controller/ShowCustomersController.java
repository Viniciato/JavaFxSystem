package Controller;

import Model.AlertBox;
import Model.Customer;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by Nadin on 12/11/15.
 */
public class ShowCustomersController {

    @FXML private TableView<Customer> tableCustomers;
    @FXML private AnchorPane showCustomerWindow;
    @FXML private TextField customerName;

    @FXML public void initialize(){
        updateCustomerTable();
        searchCustomer();
    }

    @FXML void onNewCustomerClick(ActionEvent event)throws IOException {
        newCustomer();
    }

    @FXML void onEditCustomerClick(ActionEvent event)throws IOException {
        editCustomer(tableCustomers.getSelectionModel().getSelectedItem());
    }

    private void searchCustomer(){
        customerName.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = text.getText();
                    ObservableList<Customer> oList = FXCollections.observableArrayList(Customer.findByName(name));
                    tableCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    tableCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("email"));
                    tableCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                    tableCustomers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("city"));
                    tableCustomers.setItems(oList);
                }
                else if(text.getLength() < 1)
                    updateCustomerTable();
            }
        });
    }

    private void newCustomer()throws IOException {
        Parent newCustomer_parent = FXMLLoader.load(getClass().getResource(Paths.newCustomerPath));
        Scene newCustomer_scene = new Scene(newCustomer_parent);
        Stage newCustomer_stage = new Stage();
        newCustomer_stage.setScene(newCustomer_scene);
        newCustomer_stage.setTitle("Registrar Novo Cliente");
        newCustomer_stage.initModality(Modality.WINDOW_MODAL);
        newCustomer_stage.initOwner(showCustomerWindow.getScene().getWindow());
        newCustomer_stage.setResizable(false);
        newCustomer_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateCustomerTable();
            }
        });
        newCustomer_stage.show();
    }

    private void editCustomer(Customer customer)throws IOException {
        if(customer != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.editCustomerPath));
            Parent editCustomer_parent = loader.load();
            EditCustomerController controller = loader.getController();
            controller.setCustomer(customer);
            Scene editCustomer_scene = new Scene(editCustomer_parent);
            Stage editCustomer_stage = new Stage();
            editCustomer_stage.setScene(editCustomer_scene);
            editCustomer_stage.setTitle("Editar Cliente");
            editCustomer_stage.initModality(Modality.WINDOW_MODAL);
            editCustomer_stage.initOwner(showCustomerWindow.getScene().getWindow());
            editCustomer_stage.setResizable(false);
            editCustomer_stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    updateCustomerTable();
                }
            });
            editCustomer_stage.show();
        }
        else{
            new AlertBox("Você deve Selecionar um Cliente para Editar!!", "erro", new Alert(Alert.AlertType.WARNING));
            }
    }

    private void updateCustomerTable() {
        ObservableList<Customer> oList = FXCollections.observableArrayList(Customer.all());
        tableCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("email"));
        tableCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableCustomers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("city"));
        tableCustomers.setItems(oList);
    }

}
