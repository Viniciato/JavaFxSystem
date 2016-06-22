package Controller;

import Model.Category;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 12/11/15.
 */
public class ShowProductsController {

    @FXML private AnchorPane showProductsWindow;
    @FXML private TableView<Product> tableProducts;
    @FXML private TextField searchProduct;

    @FXML public void initialize(){
        showAllProducts();
        updateChoiceCategory();
        searchProducts();
    }

    @FXML private ComboBox<Category> choiceCategory;

    @FXML void listAll(ActionEvent event) {
        choiceCategory.getSelectionModel().clearSelection();
        searchProduct.clear();
        showAllProducts();
    }

    @FXML void listWithCategory(ActionEvent event) {
        searchProduct.clear();
        if (choiceCategory.getSelectionModel().getSelectedIndex() != -1)
            showProductsWithCategory();
    }

    private void searchProducts(){
        searchProduct.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                choiceCategory.getSelectionModel().clearSelection();
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = text.getText();
                    ObservableList<Product> oList = FXCollections.observableArrayList(Product.findByName(name));
                    tableProducts.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    tableProducts.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
                    tableProducts.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
                    tableProducts.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
                    tableProducts.setItems(oList);
                }
                else if(text.getLength() < 1)
                    showAllProducts();
            }
        });
    }

    private void showProductsWithCategory() {
        ObservableList<Product> oList = FXCollections.observableArrayList(Product.allWithCategory(choiceCategory.getSelectionModel().getSelectedItem()));
        tableProducts.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProducts.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
        tableProducts.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        tableProducts.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tableProducts.setItems(oList);
    }

    private void updateChoiceCategory(){
        ObservableList<Category> oList = FXCollections.observableArrayList(Category.all());
        choiceCategory.getItems().addAll(oList);
    }

    private void showAllProducts() {
        ObservableList<Product> oList = FXCollections.observableArrayList(Product.all());
        tableProducts.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProducts.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
        tableProducts.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        tableProducts.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("description"));
        tableProducts.setItems(oList);
    }

}
