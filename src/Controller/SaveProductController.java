package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 19/11/15.
 */
public class SaveProductController {
    private int id;
    private String text;

    @FXML private AnchorPane window;
    @FXML private TextField name;
    @FXML private TextField price;
    @FXML private TextField description;
    @FXML private ComboBox<Category> categories;
    @FXML private Label title;

    @FXML public void initialize(){
        updateCategories();
    }

    @FXML void onSave(ActionEvent event) {
        if (name.getText().isEmpty()) {
            new AlertBox("O Campo nome não pode ser vazio!!", "Erro", new Alert(Alert.AlertType.WARNING));
        } else if (categories.getValue()==null) {
            new AlertBox("O Campo de Categoria não pode ser vazio!!", "Erro", new Alert(Alert.AlertType.WARNING));
        } else if (price.getText().isEmpty()) {
            new AlertBox("O Campo preço não pode ser vazio!!", "Erro", new Alert(Alert.AlertType.WARNING));
        } else {
            try {
                String pName = name.getText();
                Double pPrice = Double.parseDouble(price.getText());
                Category category = Category.findById(categories.getSelectionModel().getSelectedItem().getId());
                Product product = new Product(pName, pPrice, category);
                product.setDescription(description.getText());
                if (String.valueOf(id)!=null)
                    product.setId(id);
                product.save();
                new AlertBox(text, "Concluido", new Alert(Alert.AlertType.INFORMATION));
                window.getScene().getWindow().hide();
            } catch (NumberFormatException e) {
                new AlertBox("O campo de Preço Deve Ser Número!!", "Erro", new Alert(Alert.AlertType.WARNING));
            }
        }
    }

    public void setProduct(Product product){
        name.setText(product.getName());
        categories.getSelectionModel().select(product.getCategory());
        price.setText(String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
        id = product.getId();
    }

    public void setHeader(String headerText){
        text = headerText;
    }

    public void updateCategories(){
        ObservableList<Category> allCategories = FXCollections.observableArrayList(Category.all());
        categories.getItems().addAll(allCategories);
    }

    public void setTitle(String title){
        this.title.setText(title);
        this.title.setAlignment(Pos.TOP_LEFT);
    }
}
