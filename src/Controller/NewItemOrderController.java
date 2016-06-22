package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 13/11/15.
 */
public class NewItemOrderController {
    private Order order;
    private OrderItem orderItens;

    public void setOrder(Order order) {
        this.order = order;
    }

    @FXML private TableView<Product> products;
    @FXML private TextField productAmount;
    @FXML private AnchorPane window;
    @FXML private TextField productName;
    @FXML private ComboBox<Category> categories;

    @FXML public void initialize(){
        setProducts();
        setCategories();
        searchProduct();
    }

    @FXML void onAddProduct(ActionEvent event) {
        addProduct();
    }

    @FXML void onConclued(ActionEvent event){
        conclued();
    }

    @FXML void onSeachCategory(ActionEvent event) {
        if (categories.getSelectionModel().getSelectedIndex() != -1){
            searchCategory();
            productName.clear();
        }
    }

    private void searchProduct(){
        productName.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() == 1)
                    categories.getSelectionModel().clearSelection();
                if (text.getLength() > 1){
                    String name = text.getText();
                    ObservableList<Product> allProducts = FXCollections.observableArrayList(Product.findByName(name));
                    products.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    products.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
                    products.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
                    products.setItems(allProducts);
                }
                else
                    setProducts();
            }
        });
    }

    private void searchCategory(){
        Category category = categories.getSelectionModel().getSelectedItem();
        ObservableList<Product> allProducts = FXCollections.observableArrayList(Product.allWithCategory(category));
        products.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        products.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
        products.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        products.setItems(allProducts);
    }

    private void setCategories(){
        ObservableList<Category> categoryList = FXCollections.observableArrayList(Category.all());
        categories.getItems().addAll(categoryList);
    }

    private void conclued(){
        window.getScene().getWindow().hide();
    }

    private void addProduct(){
        if (products.getSelectionModel().getSelectedIndex() == -1){
            new AlertBox("Você deve Selecionar um produto para Adicionar!!", "Erro", new Alert(Alert.AlertType.WARNING));
        }
        else if(productAmount.getText().isEmpty()){
            new AlertBox("Você deve Colocar a Quantidade do produto!!", "Erro", new Alert(Alert.AlertType.WARNING));
        }
        else{
            try {
                Product product = products.getSelectionModel().getSelectedItem();
                int amount = Integer.parseInt(productAmount.getText());
                Double price = product.getPrice();
                Double total = price * amount;
                OrderItem orderItens = new OrderItem(order, product, total, amount, price);
                orderItens.save();
                new AlertBox("Produto Adicionado Com Sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
                products.getSelectionModel().clearSelection();
                productAmount.clear();
                productName.clear();
                setProducts();
                categories.getSelectionModel().clearSelection();
            }catch (NumberFormatException e){
                new AlertBox("A quantidade do Produto deve ser um numero!!", "Erro", new Alert(Alert.AlertType.WARNING));
            }
        }
    }

    public void setProducts(){
        ObservableList<Product> allProducts = FXCollections.observableArrayList(Product.all());
        products.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        products.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
        products.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        products.setItems(allProducts);
    }
}
