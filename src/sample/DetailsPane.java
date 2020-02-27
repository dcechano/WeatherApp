package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.datamodel.DataUtils;
import sample.datamodel.Location;
import sample.datamodel.Location.Weather;
import sample.datamodel.Location.Day;

import java.io.IOException;

public class DetailsPane extends WeatherPane {
    @FXML
    private HBox hourlyData;

    public DetailsPane() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Layouts/details.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void loadData(Day day) {
        super.loadData(day);
        Weather[] weather = day.getWeather();
        for (Weather obj : weather) {
            String key = obj.getIconCode();
            String time = obj.getTime();
            time = time.substring(0, time.length() - 3);
            int temp = obj.getCurrentTemp();
            String desc = obj.getDescription();

            ImageView view = new ImageView();
            Image img = DataUtils.getInstance().getImage(key);
            view.setImage(img);
            view.setFitHeight(75);
            view.setPreserveRatio(true);
            Label temperature = new Label(temp + " F");
            Label description = new Label(desc);
            Label hour = new Label(time);
            VBox vbox = new VBox();
            vbox.getChildren().addAll(view, description, temperature, hour);
            vbox.getStyleClass().addAll("box");
            temperature.getStyleClass().add("label");
            description.getStyleClass().add("label");
            hour.getStyleClass().add("label");
            vbox.setSpacing(10);
            vbox.setAlignment(Pos.CENTER);

            hourlyData.getChildren().add(vbox);
        }
    }
}
