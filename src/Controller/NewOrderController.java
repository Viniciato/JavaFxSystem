package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Nadin on 13/11/15.
 */
public class NewOrderController {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML private TableView<Customer> customerList;
    @FXML private TableView<Card> cardList;
    @FXML private AnchorPane newOrderPane;
    @FXML private TextField cardField;
    @FXML private TextField customerField;

    @FXML public void initialize(){
        setCustomers();
        setCardList();
        findCustomer();
        findCard();
        setToolTips();
    }

    public void setToolTips(){

    }

    @FXML void saveOrder(ActionEvent event) throws IOException{
        if (cardList.getSelectionModel().getSelectedItem() == null)
            new AlertBox("Você deve selecionar um Cartão!!", "Erro", new Alert(Alert.AlertType.WARNING));
        else{
            Card card = cardList.getSelectionModel().getSelectedItem();
            card.setStatus(2);
            card.update();
            save();
        }
    }

    private void findCustomer(){
        customerField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = text.getText();
                    ObservableList<Customer> oList = FXCollections.observableArrayList(Customer.findByName(name));
                    customerList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    customerList.setItems(oList);
                }
                else if (text.getLength() < 2)
                {
                    setCustomers();
                }
            }
        });
    }

    private void findCard(){
        cardField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 0){
                    String name = text.getText();
                    ObservableList<Card> oList = FXCollections.observableArrayList(Card.findByName(name));
                    cardList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("serial"));
                    cardList.setItems(oList);
                }
                else if (text.getLength() < 2)
                {
                    setCardList();
                }
            }
        });
    }

    public void setCustomers(){
        ObservableList<Customer> oList = FXCollections.observableArrayList(Customer.all());
        customerList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        customerList.setItems(oList);
    }

    public void setCardList(){
        ObservableList<Card> oList = FXCollections.observableArrayList(Card.allNotBusy());
        cardList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("serial"));
        cardList.setItems(oList);
    }

    private void save(){
        Order order = new Order(cardList.getSelectionModel().getSelectedItem(), user);
        if (customerList.getSelectionModel().getSelectedItem() != null)
            order.setCustomer(customerList.getSelectionModel().getSelectedItem());
        order.setTotal(0.0d);
        order.setStatus(OrderStatus.findById(1));
        order.create();
        new AlertBox("Pedido Registrado Com Sucesso!!!", "Concluído", new Alert(Alert.AlertType.INFORMATION));
        newOrderPane.getScene().getWindow().hide();
    }
}
