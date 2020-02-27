package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.datamodel.DataUtils;
import sample.datamodel.Location;
import sample.datamodel.Location.Day;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static MainController controller;
    private WeatherPane weatherPane = new WeatherPane();
    private DetailsPane detailsPane = new DetailsPane();
    private ContextMenu contextMenu;
    @FXML
    private WeatherBox forecast1;
    @FXML
    private BorderPane borderPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private WeatherBox forecast2;
    @FXML
    private WeatherBox forecast3;
    @FXML
    private WeatherBox forecast4;
    @FXML
    private WeatherBox forecast5;
    @FXML
    private Button todaysWeatherButton;
    @FXML
    private Button fiveDayButton;
    @FXML
    private Button refreshButton;
    @FXML
    private ChoiceBox<Location> choiceBox;
    @FXML
    private ListView<Location> locListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Layouts/main.fxml"));
        loader.setController(this);
        controller = loader.getController();
        contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Location loc = choiceBox.getSelectionModel().getSelectedItem();
                deleteLocation(loc);
            }
        });
        contextMenu.getItems().addAll(deleteMenuItem);
        choiceBox.setContextMenu(contextMenu);
        choiceBox.setItems(DataUtils.getInstance().getLocations());
        choiceBox.getSelectionModel().selectFirst();
        fiveDayButton.fire();
    }

    @FXML
    private void refresh() {
        if (borderPane.getCenter() instanceof DetailsPane) {
            fiveDayForecast();
        } else if (borderPane.getCenter() instanceof WeatherPane) {
            currWeather();
        } else {
            fiveDayForecast();
        }
    }

    @FXML
    private void fiveDayForecast() {
        borderPane.setCenter(gridPane);
        Location location = choiceBox.getSelectionModel().getSelectedItem();
        location.updateFiveDayForecast();
        Day[] days = location.getDays();
        WeatherBox[] weatherBoxes = {forecast1, forecast2, forecast3, forecast4, forecast5};
        for (int i = 0; i < weatherBoxes.length; i++) weatherBoxes[i].loadData(days[i]);
    }

    @FXML
    private void addLocationDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("New Location");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Layouts/newLocationDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());

        } catch (IOException e) {
            System.out.println("New Item Dialog didn't load.");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewLocationController controller = loader.getController();
            Location location = controller.newLocation();
            location.updateFiveDayForecast();
        }
    }

    @FXML
    private void deleteLocation() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("New Location");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Layouts/deleteLocationDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            System.out.println("Delete Location Dialog wasn't able to load");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DeleteLocationController controller = loader.getController();
            controller.deleteLocation();
        }
    }

    @FXML
    private void currWeather() {
        Location location = choiceBox.getSelectionModel().getSelectedItem();
        location.updateCurrWeather();
        Day today = location.getToday();
        System.out.println(today.toString());
        weatherPane.loadData(today);
        setCenter(weatherPane);
    }

    public void setCenter(Node node) {
        borderPane.setCenter(node);
    }

    @FXML
    private void goBack() {
        if (borderPane.getCenter() instanceof WeatherPane) {
            setCenter(gridPane);
        } else{
            setCenter(weatherPane);
        }
    }

    @FXML
    public void deleteLocation(Location location) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Location");
        alert.setHeaderText("Delete Location " + location.getCity());
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            DataUtils.getInstance().getLocations().remove(location);
        }
    }

    public static MainController getController() {
        return controller;
    }
}