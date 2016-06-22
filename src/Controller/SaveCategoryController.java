package Controller;

import Model.AlertBox;
import Model.Category;
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
public class SaveCategoryController {
    private String text;
    private int id;
    @FXML private TextField name;
    @FXML private TextField description;
    @FXML private AnchorPane window;
    @FXML private Label title;

    @FXML void onSave(ActionEvent event) {
        save();
    }

    private void save(){
        if (name.getLength()!=0 && description.getLength()!=0){
            String cname = name.getText();
            String cdescription = description.getText();
            Category category = new Category(cname);
            category.setDescription(cdescription);
            if (String.valueOf(id)!=null)
                category.setId(id);
            category.save();
            new AlertBox("Categoria Criada Com Sucesso!!", "Erro", new Alert(Alert.AlertType.INFORMATION));
            window.getScene().getWindow().hide();
        }
        else
            new AlertBox("Os campos nome e descrição não podem ser vazios!!!", "ERRO", new Alert(Alert.AlertType.ERROR));
    }

    public void setCategory(Category category){
        name.setText(category.getName());
        description.setText(category.getDescription());
        id = category.getId();
    }

    public void setHeader(String headerText){
        text = headerText;
    }

    public void setTitle(String title){
        this.title.setText(title);
        this.title.setAlignment(Pos.TOP_LEFT);
    }
}
