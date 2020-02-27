package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.datamodel.DataUtils;
import sample.datamodel.Location.Weather;
import sample.datamodel.Location.Day;

import java.io.IOException;

public class WeatherPane extends AnchorPane {

    @FXML
    private VBox summaryData;
    @FXML
    private VBox detailData;
    @FXML
    private ImageView img;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label windSpeedLabel;
    @FXML
    private Label windDirLabel;
    @FXML
    private Label precipLabel;
    @FXML
    private Label windChillLabel;
    @FXML
    private Label cloudsLabel;
    @FXML
    private HBox hourlyData;
    @FXML
    private Label hiTemp;
    @FXML
    private Label loTemp;
    @FXML
    private Label descriptionLabel;


    public WeatherPane() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Layouts/currWeather.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        getStyleClass().add("weatherpane");
        summaryData.getStyleClass().add("box");
        detailData.getStyleClass().add("box");
    }

    private void setDescriptionText(String descriptionText) {
        descriptionLabel.setText(descriptionText);
    }

    private void setHumidityText(int humidity) {
        humidityLabel.setText(humidity + "%");
    }

    private void setPrecipText(double precip) {
        precipLabel.setText(precip + "in");
    }

    private void setWindSpeedText(int windSpeed) {
        windSpeedLabel.setText(windSpeed + "mph");
    }

    private void setWindDirText(int windDir) {
        windDirLabel.setText(windDir + "deg.");
    }

    private void setWindChillText(int windChill) {
        windChillLabel.setText(windChill + " F");

    }

    private void setCloudsText(double clouds) {
        cloudsLabel.setText(clouds + "%");
    }

    public void setImage(Image image) {
        img.setImage(image);
        img.setFitHeight(250);
        img.setPreserveRatio(true);
    }

    public void loadData(Day day) {
        Weather weather = day.getWeather()[0];

        double precip = weather.getPrecipitation();
        setPrecipText(precip);

        int windSpeed = weather.getWindSpeed();
        setWindSpeedText(windSpeed);

        int windDir = weather.getWindDirection();
        setWindDirText(windDir);

        int windChill = weather.getWindChill();
        setWindChillText(windChill);

        int humidity = weather.getHumidity();
        setHumidityText(humidity);

        int hi = day.getDailyHigh();
        int lo = day.getDailyLow();
        setTemperatures(hi, lo);

        int clouds = weather.getClouds();
        setCloudsText(clouds);

        String description = weather.getDescription();
        setDescriptionText(description);

        String key = weather.getIconCode();
        Image image = DataUtils.getInstance().getImage(key);
        setImage(image);
    }

    private void setTemperatures(int hi, int lo) {
        hiTemp.setText((int) hi + " F");
        loTemp.setText((int) lo + " F");
    }
}
