package logic;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;

public class DMS {

    public String location = "Albuquerque";
    private String data = null;

    public DMS() {
        getWeatherInformation();
    }

    public void getWeatherInformation() {
        String apiKey = "51c31f20e1556e5135af143cd32e3e48";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey +
                "&exclude=minutely,hourly,daily,alerts";

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            data = response.body();
            //this.data = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);

        } catch (IOException | InterruptedException e) {
            System.out.println("Error getting weather info\n");
        }
    }

    public String parseWeatherData() {

        String output = "";
        System.out.println(data);
        if(data != null) {

            try {
                // Parse the data using org.json
                JSONObject jsonObject = new JSONObject(data);

                JSONObject main = jsonObject.getJSONObject("main");
                double temperature = ((main.getDouble("temp") - 273.15) * 1.8) + 32;  // Convert Kelvin to Celsius
                double feelsLike = ((main.getDouble("feels_like") - 273.15) * 1.8) + 32;  // Convert Kelvin to Celsius

                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                String description = weatherArray.getJSONObject(0).getString("description");

                output = String.format("Temperature: %.2f°F\nFeels Like: %.2f°F\nCondition: %s", temperature, feelsLike, description);

            } catch (Exception e) {
                System.err.println("Error parsing weather data: " + e.getMessage());
            }

        }

        return output;
    }

}
