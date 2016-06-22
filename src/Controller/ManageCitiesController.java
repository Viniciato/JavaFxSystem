package Controller;

import Model.AlertBox;
import Model.City;
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

public class ManageCitiesController {

    private Stage saveCity_stage;

    @FXML private AnchorPane window;
    @FXML private TableView<City> citiesTable;
    @FXML private TextField searchCity;

    @FXML public void initialize(){
        updateCities();
        onSearchCity();
    }

    @FXML
    void onAllCities(ActionEvent event) {
        updateCities();
    }

    @FXML
    void onNewCity(ActionEvent event) throws IOException{
        cityActions(null, "Estado Criado com Sucesso!!", "Novo Estado");
    }

    @FXML
    void onEditCity(ActionEvent event) throws IOException{
        if (citiesTable.getSelectionModel().getSelectedItem() !=null)
            cityActions(citiesTable.getSelectionModel().getSelectedItem(), "Cidade Atualizada com Sucesso!!", "Editar Cidade");
        else
            new AlertBox("Você deve selecionar um estado para editar!!", "Erro", new Alert(Alert.AlertType.ERROR));
    }

    @FXML
    void onDeleteCity(ActionEvent event) {
        deleteCity();
    }

    private void cityActions(City city, String text, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.saveCityPath));
        Parent saveCity_parent = loader.load();
        SaveCityController controller = loader.getController();
        if (city != null){
            controller.setCity(city);
            controller.setTitle(title);
        }
        controller.setHeader(text);
        saveCity_stage = new Stage();
        Scene saveCity_scene = new Scene(saveCity_parent);
        saveCity_stage.setScene(saveCity_scene);
        saveCity_stage.setTitle(title);
        saveCity_stage.initModality(Modality.WINDOW_MODAL);
        saveCity_stage.initOwner(window.getScene().getWindow());
        saveCity_stage.setResizable(false);
        saveCity_stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateCities();
            }
        });
        saveCity_stage.show();
    }

    private void deleteCity(){
        if (citiesTable.getSelectionModel().getSelectedItem() == null) {
            new AlertBox("Você deve selecionar uma cidade antes!!", "Erro", new Alert(Alert.AlertType.ERROR));
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja deletar a cidade "
                    + citiesTable.getSelectionModel().getSelectedItem().getName() + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                City city = citiesTable.getSelectionModel().getSelectedItem();
                city.delete();
                new AlertBox("Cidade Excluida Com Sucesso!!", "Concluido", new Alert(Alert.AlertType.INFORMATION));
                updateCities();
            }
        }
    }

    private void updateCities(){
        citiesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        citiesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("state"));
        ObservableList<City> oList = FXCollections.observableArrayList(City.all());
        citiesTable.setItems(oList);
    }

    private void onSearchCity(){
        searchCity.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField text = (TextField) event.getSource();
                if (text.getLength() > 1){
                    String name = searchCity.getText();
                    citiesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
                    citiesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("state"));
                    ObservableList<City> oList = FXCollections.observableArrayList(City.findByName(name));
                    citiesTable.setItems(oList);
                }
                else
                    updateCities();
            }
        });
    }
}
