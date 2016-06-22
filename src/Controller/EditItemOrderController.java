package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 22/11/15.
 */
public class EditItemOrderController {
    private int id;
    private Order order;
    private Product product;

    @FXML private TextField amount;
    @FXML private AnchorPane window;
    @FXML private TextField productName;

    @FXML
    void onSave(ActionEvent event) {
        save();
    }

    private void save(){
        if (amount.getText().isEmpty()){
            new AlertBox(Dictionary.amountFieldNumber, Dictionary.error, new Alert(Alert.AlertType.WARNING));
        }
        else{
            try {
                int amountProducts = Integer.valueOf(amount.getText());
                Double total = product.getPrice() * amountProducts;
                OrderItem orderItem = new OrderItem(order, product, total, amountProducts, product.getPrice());
                orderItem.setId(id);
                orderItem.save();
                new AlertBox(Dictionary.editedSucessfully, Dictionary.completed, new Alert(Alert.AlertType.INFORMATION));
                window.getScene().getWindow().hide();
            }catch (NumberFormatException e){
                new AlertBox(Dictionary.amountFieldNumber, Dictionary.error, new Alert(Alert.AlertType.WARNING));
            }
        }
    }

    public void setItem(OrderItem orderItem){
        amount.setText(String.valueOf(orderItem.getAmount()));
        productName.setText(orderItem.getProduct().getName());
        product = orderItem.getProduct();
        id = orderItem.getId();
        order = orderItem.getOrder();
    }
}
