package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import sample.datamodel.DataUtils;
import sample.datamodel.Location;

public class DeleteLocationController {
    @FXML
    private ChoiceBox<Location> choiceBox;

    public void initialize() {
        choiceBox.setItems(DataUtils.getInstance().getLocations());
    }

    @FXML
    public void deleteLocation() {
        Location location = choiceBox.getSelectionModel().getSelectedItem();
        DataUtils.getInstance().getLocations().remove(location);
    }

}
