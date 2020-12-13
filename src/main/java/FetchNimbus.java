import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class FetchNimbus {

    private String apiKey = "&appid=670678f74bec2b7e8316c16091b941a6";
    private String uriCore = "http://api.openweathermap.org/data/2.5/weather?q=";

    public enum units{
        METRIC("&units=Metric&"), IMPERIAL("&units=Imperial&");

        final String unit; //field for each enum above

        units(String unit){ //constructor for enums above
            this.unit = unit;
        }
    }

/*    public static void main(String[] args) {
        FetchNimbus controller = new FetchNimbus();
        controller.connectToWeather("Longwood","Florida", units.IMPERIAL.unit);
    }*/

    public Weather connectToWeather(String city, String state, String units){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseString = "";

        try {
            //api.openweathermap.org/data/2.5/{forecasetype}={city name},{state name}&{units}&appid={API key}
            HttpGet httpGet = new HttpGet(uriCore+city+","+state+units+apiKey);
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity received = response.getEntity();

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(received.getContent()));
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            responseString = sb.toString();

            Weather weather = new Gson().fromJson(responseString, Weather.class);
            return weather;
        } catch (
                IOException e) {
            e.printStackTrace();
            System.out.println("something went wrong");
        }
        return null;
    }

}
