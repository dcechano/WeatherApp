module WeatherApp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires org.json;

    opens WeatherIcons.png;
    opens sample;
    opens sample.datamodel;
    opens sample.Layouts;
    opens sample.Layouts.CSS;

}