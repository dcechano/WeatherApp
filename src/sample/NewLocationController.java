package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.datamodel.DataUtils;
import sample.datamodel.Location;

public class NewLocationController {

    @FXML
    private TextField cityName;

    @FXML
    public Location newLocation() {
        String name = cityName.getText().trim();
        Location location = new Location(name);
        DataUtils.getInstance().addLocation(location);
        return location;
    }
}
