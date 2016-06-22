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

/**
 * Created by Nadin on 09/11/15.
 */
public class InitialController{
    private User user;

    @FXML private Menu userMenu;
    @FXML private TableView<Order> ordersPanel;
    @FXML private MenuBar userMenuBar;
    @FXML private AnchorPane window;
    @FXML private Menu adm;
    @FXML private Menu manager;
    @FXML private Label version;

    //Quando o Controller é inicializado é chamado este método...
    @FXML public void initialize(){
        updateOrderList();
        version.setText(String.valueOf(SoftwareSpecifications.getVersion()));
    }

    //Carrega a tela de novo cliente...
    @FXML void onNewCustomer(ActionEvent event)throws IOException {
        showWindow(Paths.newCustomerPath, Dictionary.registryNewClient);
    }

    //Carrega a tela de editar um cliente...
    @FXML void onEditCustomer(ActionEvent event)throws IOException {
        editCustomer();
    }

    @FXML void onLogout(ActionEvent event) throws IOException{
        logout();
    }

    @FXML void onShowAllProducts(ActionEvent event) throws IOException{
        showWindow(Paths.showProductsPath, Dictionary.listAllProducts);
    }

    @FXML void onNewOrder(ActionEvent event)throws IOException {
        newOrder();
    }

    @FXML void onEditOrder(ActionEvent event)throws IOException {
        editOrder(ordersPanel.getSelectionModel().getSelectedItem());
    }

    @FXML void onShowCustomers(ActionEvent event) throws IOException{
        showWindow(Paths.showCustomerPath, Dictionary.listAllClients);
    }

    @FXML void onManageProducts(ActionEvent event) throws IOException{
        showWindow(Paths.manageProductsPath, Dictionary.manageProducts);
    }

//    @FXML void onDeleteProduct(ActionEvent event) throws IOException{
//        deleteProduct();
//    }

    @FXML void onManageCategories(ActionEvent event) throws IOException{
        showWindow(Paths.manageCategoriesPath, Dictionary.categorys);
    }

    @FXML void onManageStates(ActionEvent event) throws IOException{
        showWindow(Paths.manageStatesPath, Dictionary.states);
    }

    @FXML void onManageCities(ActionEvent event) throws IOException{
        showWindow(Paths.manageCitiesPath, Dictionary.cities);
    }

    @FXML void onManageTables(ActionEvent event) throws IOException{
        showWindow(Paths.manageTablesPath, Dictionary.tables);
    }

    @FXML void onManagerUsers(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.manageUsersPath));
        Parent parent = loader.load();
        ManageUsersController controller = loader.getController();
        controller.setSettings(this.userMenuBar, this.user);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(Dictionary.users);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window.getScene().getWindow());
        stage.setResizable(false);
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateOrderList();
            }
        });
        stage.show();
    }

    @FXML void onUpdateOrderList(ActionEvent event){
        updateOrderList();
    }

    @FXML void onShowOtherOrders(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.showOrdersPath));
        Parent parent = loader.load();
        ShowOtherOrdersController controller = loader.getController();
        controller.setUser(this.user);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(Dictionary.allOrders);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window.getScene().getWindow());
        stage.setResizable(false);
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateOrderList();
            }
        });
        stage.show();
    }

    private void showWindow(String path, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window.getScene().getWindow());
        stage.setResizable(false);
        stage.show();
    }

    private void logout() throws IOException{
        new AlertBox(Dictionary.logoutSuccessfully, Dictionary.logout, new Alert(Alert.AlertType.INFORMATION));
        Parent login_page_parent = FXMLLoader.load(getClass().getResource(Paths.loginPath));
        Scene login_page_scene = new Scene(login_page_parent);
        Stage stage = (Stage) userMenuBar.getScene().getWindow();
        stage.setScene(login_page_scene);
        stage.setTitle(SoftwareSpecifications.getName());
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void newOrder()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.newOrderPath));
        Parent newOrder_parent = loader.load();
        NewOrderController controller = loader.getController();
        controller.setUser(this.user);
        Stage stage = new Stage();
        Scene newOrder_scene = new Scene(newOrder_parent);
        stage.setScene(newOrder_scene);
        stage.setTitle(Dictionary.newOrder);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window.getScene().getWindow());
        stage.setResizable(false);
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateOrderList();
//                System.out.println(Order.getLastInsertId());
            }
        });
        stage.show();
    }

    private void editOrder(Order order)throws IOException {
        if (order != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.editOrderPath));
            Parent editOrder_parent = loader.load();
            EditOrderController controller = loader.getController();
            controller.setOrder(ordersPanel.getSelectionModel().getSelectedItem());
            controller.setUser(user);
            Stage stage = new Stage();
            Scene editOrder_scene = new Scene(editOrder_parent);
            stage.setScene(editOrder_scene);
            stage.setTitle(Dictionary.editOrder);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(window.getScene().getWindow());
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    updateOrderList();
                }
            });
        }
        else
            new AlertBox(Dictionary.selectOrderEdit, Dictionary.error, new Alert(Alert.AlertType.WARNING));
    }

    private void editCustomer()throws IOException {
        Parent editCustomer_parent = FXMLLoader.load(getClass().getResource(Paths.editCustomerPath));
        Scene editCustomer_scene = new Scene(editCustomer_parent);
        Stage stage = new Stage();
        stage.setScene(editCustomer_scene);
        stage.setTitle(Dictionary.editClient);
        stage.setResizable(false);
        stage.show();
        //Update Order Table after Order be edited.
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateOrderList();
            }
        });
    }

    private void editProduct()throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveProductPath));
        Parent editProduct_parent = loader.load();
        Scene editProduct_scene = new Scene(editProduct_parent);
        Stage stage = new Stage();
        stage.setScene(editProduct_scene);
        stage.setTitle(Dictionary.editProduct);
        stage.setResizable(false);
        stage.show();
        //Update Order Table after Order be edited.
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateOrderList();
            }
        });
    }

//    private void deleteProduct() throws IOException{
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveProductPath));
//        Parent deleteProduct_parent = loader.load();
//        Scene deleteProduct_scene = new Scene(deleteProduct_parent);
//        Stage stage = new Stage();
//        stage.setScene(deleteProduct_scene);
//        stage.setTitle("Editar Produto");
//        stage.setResizable(false);
//        stage.show();
//        //Update Order Table after Order be edited.
//        stage.setOnHiding(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                updateOrderList();
//            }
//        });
//    }

    private void updateOrderList() {
        ObservableList<Order> oList = FXCollections.observableArrayList(Order.allOpen());
        ordersPanel.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("card"));
        ordersPanel.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("TableName"));
        ordersPanel.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customer"));
        ordersPanel.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        ordersPanel.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        ordersPanel.setItems(oList);
    }

    public void setUser(User user) {
        this.user = user;
        userMenu.setText(user.getName());
        if (user.getAccess() > 1)
            manager.setVisible(true);
        if (user.getAccess() > 2)
            adm.setVisible(true);
    }
}
