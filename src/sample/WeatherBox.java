package sample;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import sample.datamodel.DataUtils;
import sample.datamodel.Location.Weather;
import sample.datamodel.Location.Day;
import java.io.IOException;
import java.time.*;


public class WeatherBox extends VBox {

    private Day day;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private Label hiTemp;
    @FXML
    private Label loTemp;
    @FXML
    private Label precipitation;
    @FXML
    private Button details;
    @FXML
    private ImageView img;

    public WeatherBox() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Layouts/weatherbox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setSpacing(40);
        getStyleClass().add("weatherbox");
        date.getStyleClass().add("date");
        hiTemp.getStyleClass().add("hitemp");
        loTemp.getStyleClass().add("lotemp");
    }

    @FXML
    public void forecastDetails() throws IOException {
        DetailsPane pane = new DetailsPane();
        pane.loadData(this.day);
        MainController.getController().setCenter(pane);
    }

    public void setDateText(String dateText) {
        this.date.setText(dateText);
    }

    public void setImage(Image image) {
        img.setImage(image);
        img.setFitHeight(100);
        img.setPreserveRatio(true);
    }

    public void setDescriptionText(String description) {
        this.description.setText(description);
    }

    public void setTemperatureText(double hi, double lo, int windChill) {
        hiTemp.setText((int) hi + " F");
        loTemp.setText((int) lo + " F");
    }

    public void setPrecipitation(double precipitation) {
        if (precipitation == 0.0) {
            this.precipitation.setText("No precip. expected");
            return;
        }
        this.precipitation.setText(precipitation + "in. expected");
    }

    public void setDay(Day day) {
        this.day = day;
    }

    private String dateFormat(String date) {
//        assumed that date has form yyyy-mm-dd
        int intMonth = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        Month month = Month.of(intMonth);
        return month.toString().substring(0, 3) + " " + day;
    }

    public void loadData(Day day) {
        this.day = day;
        Weather weather = day.getMidDayWeather();
        String description = weather.getDescription();
        setDescriptionText(description);

        this.date.setText(day.getDate());
        String date = day.getDate().replace("-", " ");
        setDateText(dateFormat(date));
        int hi = day.getDailyHigh();
        int lo = day.getDailyLow();
        int windChill = weather.getWindChill();
        setTemperatureText(hi, lo, windChill);

        double precip = weather.getPrecipitation();
        setPrecipitation(precip);
        String key = weather.getIconCode();
        Image image = DataUtils.getInstance().getImage(key);
        setImage(image);
    }

}

