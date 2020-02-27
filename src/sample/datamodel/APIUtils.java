package sample.datamodel;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class APIUtils {

    private static final String API_KEY = "&appid=2e0da12e09c8ba8188bbf6a73584b6d3";

    private static final String SERVER = "https://api.openweathermap.org/data/2.5/";

    private static final String CURR_WEATHER = "weather";

    private static final String FIVE_DAY = "forecast";

    private static final String QUERY = "?q=";

    private static final String UNITS = "&units=imperial";


    private APIUtils() {}

    public static JSONObject fiveDayForecast(Location location) {
        StringBuilder builder = new StringBuilder(SERVER);
        builder.append(FIVE_DAY)
                .append(QUERY)
                .append(location.getCity())
                .append(UNITS)
                .append(API_KEY);
        try {
            URL url = new URL(builder.toString());
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String jsonString = reader.readLine();
            return new JSONObject(jsonString);

        } catch (IOException e) {
            System.out.println("IOException thrown while retrieving five day forecast");
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject todaysForecast(Location location) {
        StringBuilder builder = new StringBuilder(SERVER);
        builder.append(CURR_WEATHER)
                .append(QUERY)
                .append(location.getCity())
                .append(UNITS)
                .append(API_KEY);
        try {
            URL url = new URL(builder.toString());
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String jsonString = reader.readLine();
            return new JSONObject(jsonString);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException thrown while retreiveing today's forecast");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("IOException thrown while retrieving today's forecast");
            e.printStackTrace();
            return null;
        }
    }
}