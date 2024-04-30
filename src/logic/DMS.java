package logic;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import org.json.JSONObject;

public class DMS {

    public State state;
    public String wxMessage = "";

    public enum State {
        DEFAULT,
        EMERGENCY,
        CONSTRUCTION,
        ACCIDENT,
        WEATHER
    }

    public DMS(String location) {
        state = State.DEFAULT;
        getWeatherInformation(location); // TODO: make this selectable?
    }

    private void getWeatherInformation(String location) {
        String apiKey = "51c31f20e1556e5135af143cd32e3e48";

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey +
                "&exclude=minutely,daily,alerts&units=imperial";

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            wxMessage = parseWeatherData(response.body());

        } catch (IOException | InterruptedException e) {
            System.out.println("Error getting weather info\n");
        }
    }


    private String parseWeatherData(String data) {

        String output = "";

        if(data != null) {

            double temperature, feelsLike, windGust, windDir;
            int humidity;

            JSONObject jsonObject;

            try {

                // Parse the data using org.json
                jsonObject = new JSONObject(data);

                try {

                    JSONObject main = jsonObject.getJSONObject("main");
                    temperature = main.getDouble("temp");
                    feelsLike = main.getDouble("feels_like");

                    output = String.format("Temperature: %.0f°F\nFeels like: %.0f°F\n",
                            temperature, feelsLike);

                } catch(Exception e) {

                    // no main weather data found
                }

                try {

                    JSONObject wind = jsonObject.getJSONObject("wind");
                    windGust = wind.getDouble("gust");
                    windDir = wind.getDouble("deg");

                    output = String.format("%sWind gusts: %.0fmph %s", output, windGust, degreesToCardinal(windDir));

                } catch(Exception e) {

                    // fail back with humidity on 3rd line
                    try {

                        JSONObject main = jsonObject.getJSONObject("main");
                        humidity = main.getInt("humidity");

                        output = output + "Humidity: " + humidity + "%";

                    } catch(Exception ef) {
                        // should not go in here
                    }

                }

            } catch(Exception e) {

                // fails gracefully with empty output

            }

        }

        return output;
    }

    /**
     * Converts from meteorological degrees to cardinal direction
     * @param degrees 0-360
     * @return string representing approximate direction
     */
    private static String degreesToCardinal(double degrees) {

        String[] directions = {
                "N", "NE", "E", "SE", "S", "SW", "W", "NW"
        };

        int index = (int)((degrees + 22.5) / 45);
        index = index % 8;

        return directions[index];
    }

}
