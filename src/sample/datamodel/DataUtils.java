package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DataUtils {
    private static DataUtils instance;
    private ObservableList<Location> locations;
    private static HashMap<String, Image> imageMap = new HashMap<>();
    private final String FILE_NAME = "Locations.txt";

    static {
        instance = new DataUtils();
        imageMap.put("01d", new Image("WeatherIcons/png/010-sun.png"));
        imageMap.put("01n", new Image("WeatherIcons/png/032-half-moon.png"));
        imageMap.put("02d", new Image("WeatherIcons/png/046-cloudy.png"));
        imageMap.put("02n", new Image("WeatherIcons/png/045-cloudy-1.png"));
        imageMap.put("03d", new Image("WeatherIcons/png/044-cloudy-2.png"));
        imageMap.put("03n", new Image("WeatherIcons/png/044-cloudy-2.png"));
        imageMap.put("04d", new Image("WeatherIcons/png/044-cloudy-2.png"));
        imageMap.put("04n", new Image("WeatherIcons/png/044-cloudy-2.png"));
        imageMap.put("09d", new Image("WeatherIcons/png/030-storm.png"));
        imageMap.put("09n", new Image("WeatherIcons/png/030-storm.png"));
        imageMap.put("10d", new Image("WeatherIcons/png/029-storm-1.png"));
        imageMap.put("10n", new Image("WeatherIcons/png/028-storm-2.png"));
        imageMap.put("11d", new Image("WeatherIcons/png/012-storm-4.png"));
        imageMap.put("11n", new Image("WeatherIcons/png/012-storm-4.png"));
        imageMap.put("13d", new Image("WeatherIcons/png/020-snow.png"));
        imageMap.put("13n", new Image("WeatherIcons/png/018-snowy.png"));
        imageMap.put("50d", new Image("WeatherIcons/png/039-foggy-1.png"));
        imageMap.put("50n", new Image("WeatherIcons/png/038-foggy-2.png"));
    }

    private DataUtils() {}

    public static DataUtils getInstance() {
        return instance;
    }

    public Image getImage(String key) {
        return imageMap.get(key);
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public ObservableList<Location> getLocations() {
        return locations;
    }

    public void loadLocations() throws IOException {
        locations = FXCollections.observableArrayList();
        Path path = Paths.get(FILE_NAME);

        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] locationData = input.split("\t");
                for (String city : locationData) {
                    Location location = new Location(city);
                    locations.add(location);
                }
            }
        }
    }

    public void storeLocations() throws IOException {
        Path path = Paths.get(FILE_NAME);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            Iterator<Location> iterator = locations.iterator();
            while (iterator.hasNext()) {
                Location location = iterator.next();
                bufferedWriter.write(String.format("%s\t", location.getCity()));
                bufferedWriter.newLine();
            }
        }
    }
}