package Controller;

import Model.AlertBox;
import Model.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 19/11/15.
 */
public class SaveStateController {
    private int id;
    private String text;

    @FXML private TextField name;
    @FXML private TextField acronym;
    @FXML private AnchorPane window;
    @FXML private Label title;

    @FXML void onSave(ActionEvent event) {
        if (name.getLength()!=0 && acronym.getLength()!=0){
            if (acronym.getLength()>2){
                new AlertBox("O campo Sigla só pode ter 2 letras!!!", "Erro", new Alert(Alert.AlertType.ERROR));
            }
            else{
                State state = new State(name.getText(), acronym.getText());
                if (String.valueOf(id)!=null)
                    state.setId(id);
                state.save();
                new AlertBox("Estado Criado Com Sucesso!!", "Sucesso", new Alert(Alert.AlertType.INFORMATION));
                window.getScene().getWindow().hide();
            }
        }
        else
            new AlertBox("Os campos Nome e Sigla não podem ser nulos!!", "Erro", new Alert(Alert.AlertType.ERROR));
    }

    public void setState(State state){
        name.setText(state.getName());
        acronym.setText(state.getAcronym());
        id = state.getId();
    }

    public void setHeader(String headerText){
        text = headerText;
    }

    public void setTitle(String title){
        this.title.setText(title);
        this.title.setAlignment(Pos.TOP_LEFT);
    }
}
