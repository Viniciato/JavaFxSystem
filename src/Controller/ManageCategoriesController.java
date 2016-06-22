package Controller;

import Model.AlertBox;
import Model.Category;
import Model.Dictionary;
import Model.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Nadin on 19/11/15.
 */
public class ManageCategoriesController {
    private Stage newCategory_stage;

    @FXML private AnchorPane window;
    @FXML private TableView<Category> categoriesTable;
    @FXML private TextField searchCategory;

    @FXML public void initialize(){
        updateCategories();
        onSearchCategory();
    }

    @FXML
    void onNewCategory(ActionEvent event) throws IOException{
        categoryActions(null, Dictionary.categoryCreatedSuccessfully, Dictionary.newCategory);
    }

    @FXML
    void onEditCategory(ActionEvent event) throws IOException{
        if (categoriesTable.getSelectionModel().getSelectedItem() != null)
            categoryActions(categoriesTable.getSelectionModel().getSelectedItem(), Dictionary.categoryUpSuccessfully, Dictionary.editCategory);
        else
            new AlertBox(Dictionary.selectCategoryEdit, Dictionary.error, new Alert(Alert.AlertType.ERROR));
    }

    @FXML
    void onDeleteCategory(ActionEvent event) {
        deleteCategory();
    }

    private void deleteCategory(){
        if (categoriesTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Dictionary.confirmDeleteCategory
                    + categoriesTable.getSelectionModel().getSelectedItem().getName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.YES) {
                Category category = categoriesTable.getSelectionModel().getSelectedItem();
                category.delete();
                new AlertBox(Dictionary.categoryDeleteSuccessfully, Dictionary.completed, new Alert(Alert.AlertType.INFORMATION));
                updateCategories();
            }
        }
        else
            new AlertBox(Dictionary.selectCategoryDelete, Dictionary.error, new Alert(Alert.AlertType.ERROR));
    }

    private void categoryActions(Category category, String text, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveCategoryPath));
        Parent newCategory_parent = loader.load();
        SaveCategoryController controller = loader.getController();
        if (category != null){
            controller.setCategory(category);
            controller.setTitle(title);
        }
        controller.setHeader(text);
        newCategory_stage = new Stage();
        Scene newOrder_scene = new Scene(newCategory_parent);
        newCategory_stage.setScene(newOrder_scene);
        newCategory_stage.setTitle(title);
        newCategory_stage.initModality(Modality.WINDOW_MODAL);
        newCategory_stage.initOwner(window.getScene().getWindow());
        newCategory_stage.setResizable(false);
        newCategory_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateCategories();
            }
        });
        newCategory_stage.show();
    }

    private void updateCategories() {
        categoriesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        ObservableList<Category> oList = FXCollections.observableArrayList(Category.all());
        categoriesTable.setItems(oList);
    }

    private void onSearchCategory(){
        searchCategory.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = searchCategory.getText();
                    categoriesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    categoriesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
                    ObservableList<Category> oList = FXCollections.observableArrayList(Category.findByName(name));
                    categoriesTable.setItems(oList);
                }
                else
                    updateCategories();
            }
        });
    }
}
