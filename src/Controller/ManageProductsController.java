package Controller;

import Model.AlertBox;
import Model.Paths;
import Model.Product;
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

public class ManageProductsController {

    @FXML private AnchorPane window;
    @FXML private TableView<Product> productsTable;
    @FXML private TextField searchProduct;

    @FXML public void initialize(){
        updateProducts();
        onSearchProduct();
    }

    @FXML
    void onAllProducts(ActionEvent event) {
        updateProducts();
    }

    @FXML
    void onNewProduct(ActionEvent event) throws IOException{
        productActions(null, "Produto Criado Com Sucesso", "Novo Produto");
    }

    @FXML
    void onEditProduct(ActionEvent event) throws IOException{
        if (productsTable.getSelectionModel().getSelectedItem() !=null)
            productActions(productsTable.getSelectionModel().getSelectedItem(), "Produto Atualizado com Sucesso!!", "Editar Produto");
        else
            new AlertBox("Você deve selecionar um produto para editar!!", "Erro", new Alert(Alert.AlertType.ERROR));
    }

    @FXML
    void onDeleteProduct(ActionEvent event) {
        deleteProduct();
    }

    private void productActions(Product product, String text, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveProductPath));
        Parent parent = loader.load();
        SaveProductController controller = loader.getController();
        if (product != null){
            controller.setProduct(product);
        }
        controller.setTitle(title);
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
                updateProducts();
                searchProduct.clear();
            }
        });
        stage.show();
    }

    private void deleteProduct(){
        if (productsTable.getSelectionModel().getSelectedItem() == null) {
            new AlertBox("Você deve selecionar um produto antes!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja deletar o produto "
                    + productsTable.getSelectionModel().getSelectedItem().getName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Product product = productsTable.getSelectionModel().getSelectedItem();
                product.delete();
                new AlertBox("Produto Excluido Com Sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
                updateProducts();
            }
        }
    }

    private void updateProducts(){
        productsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        productsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<Product> oList = FXCollections.observableArrayList(Product.all());
        productsTable.setItems(oList);
    }

    private void onSearchProduct(){
        searchProduct.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = searchProduct.getText();
                    productsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    productsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("price"));
                    ObservableList<Product> oList = FXCollections.observableArrayList(Product.findByName(name));
                    productsTable.setItems(oList);
                }
                else
                    updateProducts();
            }
        });
    }
}
