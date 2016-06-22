package Controller;

import Model.AlertBox;
import Model.City;
import Model.State;
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
public class SaveCityController {
    private int id;
    private String text;

    @FXML private TextField name;
    @FXML private AnchorPane window;
    @FXML private ComboBox<State> states;
    @FXML private Label title;

    @FXML public void initialize(){
        updateStates();
    }

    @FXML void onSave(ActionEvent event) {
        if (name.getLength()!=0 && states.getSelectionModel().getSelectedItem()!=null)
        {
            City city = new City(name.getText(), states.getSelectionModel().getSelectedItem());
            if (String.valueOf(id)!=null)
                city.setId(id);
            city.save();
            new AlertBox("Cidade criada com sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
            window.getScene().getWindow().hide();
        }
        else
            new AlertBox("Os campos nome e estados não podem ser nulos!!", "ERRO", new Alert(Alert.AlertType.ERROR));
    }

    public void setCity(City city){
        name.setText(city.getName());
        states.getSelectionModel().select(city.getState());
        id = city.getId();
    }

    public void setHeader(String headerText){
        text = headerText;
    }

    public void updateStates(){
        ObservableList<State> allStates = FXCollections.observableArrayList(State.all());
        states.getItems().addAll(allStates);
    }

    public void setTitle(String title){
        this.title.setText(title);
        this.title.setAlignment(Pos.TOP_LEFT);
    }
}
