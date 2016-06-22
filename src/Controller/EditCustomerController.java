package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 11/11/15.
 */
public class EditCustomerController {

    private int id;

    @FXML private AnchorPane window;
    @FXML private ComboBox<State> states;
    @FXML private ComboBox<City> cities;
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField cpf;
    @FXML private TextField telephone;
    @FXML private TextField street;
    @FXML private TextField addressNumber;
    @FXML private TextField neighborhood;
    @FXML private TextField zipCode;
    @FXML private TextArea comments;

    @FXML void saveEditCustomer(ActionEvent event) {
        if (name.getText().isEmpty()){
            new AlertBox(Dictionary.emptyNameField, Dictionary.error, new Alert(Alert.AlertType.ERROR));
        }
        else if(cities.getValue() == null){
            new AlertBox(Dictionary.emptyCityField, Dictionary.error, new Alert(Alert.AlertType.ERROR));
        }
        else if(telephone.getText().isEmpty()){
            new AlertBox(Dictionary.emptyPhoneField, Dictionary.error, new Alert(Alert.AlertType.ERROR));
        }
        else{
            try {
                boolean error = false;
                String name = this.name.getText();
                City city = cities.getValue();
                Customer customer = new Customer(name, city);
                customer.setId(id);
                customer.setEmail(email.getText());
                customer.setStreet(street.getText());
                if (!addressNumber.getText().isEmpty())
                    customer.setAddressNumber(Integer.valueOf(addressNumber.getText()));
                customer.setNeighborhood(neighborhood.getText());
                if (!zipCode.getText().isEmpty())
                    customer.setZipCode(Integer.valueOf(zipCode.getText()));
                customer.setComments(comments.getText());
                if (!cpf.getText().isEmpty())
                    if (cpf.getText().length() == 11)
                        customer.setCpf(Long.parseLong(cpf.getText()));
                    else {
                        new AlertBox(Dictionary.minCpfNumbers, Dictionary.error, new Alert(Alert.AlertType.ERROR));
                        error = true;
                    }
                if (telephone.getText().length()<12 && telephone.getText().length() > 9){
                    customer.setPhoneNumber(Long.parseLong(telephone.getText()));
                }
                else{
                    new AlertBox(Dictionary.minPhoneNumbers, Dictionary.error, new Alert(Alert.AlertType.ERROR));
                    error = true;
                }
                if (!error){
                    try {
                        customer.save();
                        new AlertBox(Dictionary.clientUpSuccess, Dictionary.completed, new Alert(Alert.AlertType.INFORMATION));
                        window.getScene().getWindow().hide();
                    }catch (InvalidCpfException e){
                        new AlertBox(e.getMessage(), Dictionary.error, new Alert(Alert.AlertType.ERROR));
                    }catch (RuntimeException e){
                        new AlertBox(e.getMessage(), Dictionary.error, new Alert(Alert.AlertType.ERROR));
                    }
                }
            }catch (NumberFormatException e){
                new AlertBox(Dictionary.fieldsNumbersError, Dictionary.error, new Alert(Alert.AlertType.ERROR));
            }
        }
    }
    @FXML void onSelectState(ActionEvent event){
        cities.getSelectionModel().select(null);
        setCitys();
    }

    private void setStates(){
        ObservableList<State> oList = FXCollections.observableArrayList(State.all());
        states.getItems().addAll(oList);
    }

    private void setCitys(){
        cities.getItems().clear();
        ObservableList<City> oList = FXCollections.observableArrayList(City.findByState(states.getSelectionModel().getSelectedItem()));
        cities.getItems().addAll(oList);
    }

    public void setCustomer(Customer customer) {
        name.setText(customer.getName());
        if (customer.getCpf() != 0)
            cpf.setText(String.valueOf(customer.getCpf()));
        email.setText(customer.getEmail());
        telephone.setText(String.valueOf(customer.getPhoneNumber()));
        id = customer.getId();
        street.setText(customer.getStreet());
        if (customer.getAddressNumber() != 0)
            addressNumber.setText(String.valueOf(customer.getAddressNumber()));
        neighborhood.setText(customer.getNeighborhood());
        if (customer.getZipCode() != 0)
            zipCode.setText(String.valueOf(customer.getZipCode()));
        comments.setText(customer.getComments());
        setStates();
        states.getSelectionModel().select(customer.getCity().getState());
        setCitys();
        cities.getSelectionModel().select(customer.getCity());
    }

}
