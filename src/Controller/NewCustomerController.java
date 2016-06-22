package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 11/11/15.
 */
public class NewCustomerController {

    @FXML private AnchorPane window;
    @FXML private ComboBox<City> cities;
    @FXML private ComboBox<State> states;
    @FXML private TextField name;
    @FXML private TextField cpf;
    @FXML private TextField telephone;
    @FXML private TextField email;


    @FXML public void initialize(){
        setStates();
        setToolTips();
    }

    @FXML void saveCustomer(ActionEvent event) {
        saveCustomer();
    }

    @FXML void onSelectState(ActionEvent event) {
        setCitys();
    }

    private void setToolTips(){
        name.setTooltip(new Tooltip("Nome do Cliente"));
        cpf.setTooltip(new Tooltip("CPF do Cliente"));
        telephone.setTooltip(new Tooltip("Telefone do Cliente"));
        email.setTooltip(new Tooltip("Email do Cliente"));
        cities.setTooltip(new Tooltip("Cidade do Cliente"));
    }

    private void saveCustomer(){
        if (name.getText().isEmpty()){
            new AlertBox("O Nome Do Cliente não Deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        }
        else if(cities.getSelectionModel().isEmpty()){
            new AlertBox("A Cidade Do Cliente não Deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        }
        else if(telephone.getText().isEmpty()){
            new AlertBox("O Telefone Do Cliente não Deve ser Vazio!!", "Erro", new Alert(Alert.AlertType.ERROR));
        }
        else{
            try {
                boolean error = false;
                String name = this.name.getText();
                City city = cities.getValue();
                Customer customer = new Customer(name, city);
                if (!cpf.getText().isEmpty())
                    if (cpf.getText().length() == 11)
                        customer.setCpf(Long.parseLong(cpf.getText()));
                    else {
                        new AlertBox("O CPF Do Cliente Deve Conter 11 Numeros!!", "Erro", new Alert(Alert.AlertType.ERROR));
                        error = true;
                    }
                if (telephone.getText().length()<12 && telephone.getText().length() > 9){
                    customer.setPhoneNumber(Long.parseLong(telephone.getText()));
                }
                else{
                    new AlertBox("O Telefone Do Cliente não Deve Conter entre 10 e 11 Numeros!!", "Erro", new Alert(Alert.AlertType.ERROR));
                    error = true;
                }
                customer.setEmail(email.getText());
                if (!error){
                    try {
                        customer.save();
                        new AlertBox("Cliente Registrado Com Sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
                        window.getScene().getWindow().hide();
                    }catch (InvalidCpfException e){
                        new AlertBox(e.getMessage(), "Erro", new Alert(Alert.AlertType.ERROR));
                    }catch (RuntimeException e){
                        new AlertBox(e.getMessage(), "Erro", new Alert(Alert.AlertType.ERROR));
                    }
                }
            }catch (NumberFormatException e){
                new AlertBox("Os Campos Telefone e CPF Devem ser numeros!!", "Erro", new Alert(Alert.AlertType.ERROR));
            }
        }
    }

    private void setStates(){
        states.getItems().clear();
        ObservableList<State> statesList = FXCollections.observableArrayList(State.all());
        states.getItems().addAll(statesList);
    }

    private void setCitys(){
        cities.getItems().clear();
        ObservableList<City> citiesList = FXCollections.observableArrayList(City.findByState(states.getSelectionModel().getSelectedItem()));
        cities.getItems().addAll(citiesList);
    }
}
