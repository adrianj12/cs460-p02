package logic;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import org.json.JSONObject;

public class DMS {

    public State state;
    public String wxMessage;

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

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            wxMessage = parseWeatherData(response.body());

        } catch (IOException | InterruptedException e) {
            System.out.println("Error getting weather info\n");
        } finally {
            // Close the HttpClient
            client = null;
        }
    }


    private String parseWeatherData(String data) {

        String output = "";

        if(data != null) {

            try {
                // Parse the data using org.json
                JSONObject jsonObject = new JSONObject(data);

                JSONObject main = jsonObject.getJSONObject("main");
                double temperature = main.getDouble("temp");
                double feelsLike = main.getDouble("feels_like");

                JSONObject wind = jsonObject.getJSONObject("wind");
                double windGust = wind.getDouble("gust");
                double windDir = wind.getDouble("deg");

                output = String.format("Temperature: %.0f°F\nFeels like: %.0f°F\nWind gusts: %.0fmph %s",
                                        temperature, feelsLike, windGust, degreesToCardinal(windDir));

            } catch (Exception e) {
                System.err.println("Error parsing weather data: " + e.getMessage());
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
