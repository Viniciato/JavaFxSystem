package Controller;

import Model.AlertBox;
import Model.Table;
import Model.TableStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Nadin on 20/11/15.
 */
public class SaveTableController {
    private int id;
    private String text;

    @FXML private TextField places;
    @FXML private TextField name;
    @FXML private AnchorPane window;
    @FXML private Label title;

    @FXML void onSave(ActionEvent event) {
        if (name.getLength()!=0 && places.getLength()!=0)
        {
            Table table = new Table(TableStatus.findById(1), Integer.parseInt(places.getText()), name.getText());
            if (String.valueOf(id)!=null)
                table.setId(id);
            table.save();
            new AlertBox("Mesa Criada Com Sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
            window.getScene().getWindow().hide();
        }
        else
            new AlertBox("Os campos nome e lugar não podem ser vazios!!", "ERRO", new Alert(Alert.AlertType.ERROR));
    }

    public void setTable(Table table){
        name.setText(table.getName());
        places.setText(String.valueOf(table.getPlaces()));
        id = table.getId();
    }

    public void setHeader(String headerText){
        text = headerText;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }
}
