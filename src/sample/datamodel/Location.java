package sample.datamodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class Location {

    private String city;
    private Day[] days = new Day[5];
    private Day today;

    public Location(String city) {
        this.city = city;
        for (int i = 0; i < days.length; i++) {
            days[i] = new Day();
        }
        today = new Day();
    }

    public String getCity() {
        return city;
    }

    public Day[] getDays() {
        return days;
    }

    public Day getToday() {
        return today;
    }

    @Override
    public String toString() {
        return city;
    }

    public synchronized void updateFiveDayForecast() {
        JSONObject forecast = APIUtils.fiveDayForecast(this);
        JSONArray list = (JSONArray) forecast.get("list");
        JSONObject temp;
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        Iterator<Object> iterator = list.iterator();
        String date = list.getJSONObject(0).getString("dt_txt").substring(0, 10);

        for (Day value : days) {
            while (iterator.hasNext()) {
                temp = (JSONObject) iterator.next();

                if (date.equals(temp.getString("dt_txt").substring(0, 10))) {
                    arrayList.add(temp);

                } else {
                    Object[] obj = arrayList.toArray();
                    JSONObject[] json = new JSONObject[obj.length];

                    for (int j = 0; j < obj.length; j++) {
                        json[j] = (JSONObject) obj[j];
                    }

                    value.updateWeather(json);
                    date = temp.getString("dt_txt").substring(0, 10);
                    arrayList.clear();
                    arrayList.add(temp);
                    break;
                }
            }
            if (!iterator.hasNext()) {
                Object[] obj = arrayList.toArray();
                JSONObject[] json = new JSONObject[obj.length];

                for (int j = 0; j < obj.length; j++) {
                    json[j] = (JSONObject) obj[j];
                }

                value.updateWeather(json);
            }
        }
    }

    public void updateCurrWeather() {
        JSONObject forecast = APIUtils.todaysForecast(this);
        today.updateCurrWeather(forecast);
//        Weather weather = this.today.weather[0];
//
//        JSONObject main = forecast.getJSONObject("main");
//        weather.maxTemp = main.getDouble("temp_max");
//        weather.minTemp = main.getDouble("temp_min");
//        weather.currentTemp = main.getDouble("temp");
//        weather.windChill = main.getDouble("feels_like");
//        weather.humidity = main.getDouble("humidity");
//        weather.windDirection = (int) forecast.getJSONObject("wind").getDouble("deg");
//        weather.windSpeed = forecast.getJSONObject("wind").getDouble("speed");
//
//        JSONObject jsonWeather = forecast.getJSONArray("weather").getJSONObject(0);
//        weather.iconCode = jsonWeather.getString("icon");
//        weather.description = jsonWeather.getString("description");
//        try{
//            JSONObject precip = forecast.getJSONObject("rain");
//            weather.precipitation = precip.getDouble("3h");
//        } catch (JSONException ignored){}
//        try {
//            JSONObject precip = forecast.getJSONObject("snow");
//            weather.precipitation = precip.getDouble("3h");
//        } catch (JSONException ignored) {}
//

    }

    public class Weather {

        private String time;

        private String description;

        private double precipitation;

        private int maxTemp;

        private int minTemp;

        private int windChill;

        private int windDirection;

        private int windSpeed;

        private int clouds;

        private int currentTemp;

        private int humidity;

        private String iconCode;

        private Weather() {

        }

        public String getTime() {
            return time;
        }

        public String getIconCode() {
            return iconCode;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public double getPrecipitation() {
            return precipitation;
        }

        public int getMaxTemp() {
            return maxTemp;
        }

        public int getMinTemp() {
            return minTemp;
        }

        public int getWindChill() {
            return windChill;
        }

        public int getWindDirection() {
            return windDirection;
        }

        public int getWindSpeed() {
            return windSpeed;
        }

        public int getClouds() {
            return clouds;
        }

        public int getCurrentTemp() {
            return currentTemp;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public class Day {
        private JSONObject[] data;
        private String date;
        private Weather[] weather;

        private Day() {

        }

        public String getDate() {
            return date;
        }

        public Weather[] getWeather() {
            return weather;
        }

        public void updateWeather(JSONObject[] jsonObjects) {
            weather = new Weather[jsonObjects.length];
            data = jsonObjects;
            JSONObject temp;

            for (int i = 0; i < jsonObjects.length; i++) {
                weather[i] = new Weather();
                temp = jsonObjects[i];
                System.out.println(temp);
                String date = temp.getString("dt_txt").substring(0, 10);

                this.date = date;
                weather[i].setTime(temp.getString("dt_txt").substring(11));
                try{
                    JSONObject precip = temp.getJSONObject("rain");
                    weather[i].precipitation = precip.getDouble("3h");
                } catch (JSONException ignored){}
                try {
                    JSONObject precip = temp.getJSONObject("snow");
                    weather[i].precipitation = precip.getDouble("3h");
                } catch (JSONException ignored) {}
                JSONObject jsonWeather = temp.getJSONArray("weather").getJSONObject(0);
                weather[i].iconCode = jsonWeather.getString("icon");
                weather[i].description = jsonWeather.getString("description");

                JSONObject jsonMain = temp.getJSONObject("main");
                weather[i].currentTemp = jsonMain.getInt("temp");
                weather[i].maxTemp = (int) jsonMain.getDouble("temp_max");
                weather[i].minTemp = (int) jsonMain.getDouble("temp_min");
                weather[i].windChill = (int) jsonMain.getDouble("feels_like");
                weather[i].humidity = jsonMain.getInt("humidity");
                weather[i].clouds = (int) temp.getJSONObject("clouds").getDouble("all");
                weather[i].windDirection = temp.getJSONObject("wind").getInt("deg");
                weather[i].windSpeed = (int) temp.getJSONObject("wind").getDouble("speed");
            }
        }

        public void updateCurrWeather(JSONObject jsonObjects) {
            weather = new Weather[1];
            data = new JSONObject[]{jsonObjects};
            JSONObject temp;

            for (int i = 0; i < data.length; i++) {
                weather[i] = new Weather();
                temp = data[i];
                long date = temp.getLong("dt");
                this.date = Long.toString(date);
                weather[i].setTime(Long.toString(date));
                try{
                    JSONObject precip = temp.getJSONObject("rain");
                    weather[i].precipitation = precip.getDouble("3h");
                } catch (JSONException ignored){}
                try {
                    JSONObject precip = temp.getJSONObject("snow");
                    weather[i].precipitation = precip.getDouble("3h");
                } catch (JSONException ignored) {}
                JSONObject jsonWeather = temp.getJSONArray("weather").getJSONObject(0);
                weather[i].iconCode = jsonWeather.getString("icon");
                weather[i].description = jsonWeather.getString("description");

                JSONObject jsonMain = temp.getJSONObject("main");
                weather[i].currentTemp = jsonMain.getInt("temp");
                weather[i].maxTemp = (int) jsonMain.getDouble("temp_max");
                weather[i].minTemp = (int) jsonMain.getDouble("temp_min");
                weather[i].windChill = (int) jsonMain.getDouble("feels_like");
                weather[i].humidity = jsonMain.getInt("humidity");
                weather[i].clouds = (int) temp.getJSONObject("clouds").getDouble("all");
                weather[i].windDirection = temp.getJSONObject("wind").getInt("deg");
                weather[i].windSpeed = (int) temp.getJSONObject("wind").getDouble("speed");
            }
        }

        public int getDailyLow() {
            int low = weather[0].minTemp;
            for (Weather value : weather) {
                if (value.minTemp < low) {
                    low = value.minTemp;
                }
            }
            return low;
        }

        public int getDailyHigh() {
            int hi = weather[0].maxTemp;
            for (Weather value : weather) {
                if (value.maxTemp > hi) {
                    hi = value.maxTemp;
                }
            }
            return hi;
        }

        public Weather getMidDayWeather() {
            for (Weather value : weather) {
                String time = value.getTime();
                if (time.equals("12:00:00")) {
                    return value;
                }
            }
            return weather[0];
        }

        public String toString() {
            return "Date: " + date;
        }
    }
}