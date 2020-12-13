import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherPairTest {

    City city = new City();
    Weather weather = new Weather("hi");
    WeatherPair wp = new WeatherPair(city,weather);
    @Before
    public void setup(){
        city.setCityName("Philadelphia");
        city.setStateName("Pennsylvania");
    }

    @Test
    public void getCity() {
        City expected = city;
        City actual = wp.getCity();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getWeather() {
        Weather expected = weather;
        Weather actual = wp.getWeather();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setWeather() {
        Weather weather1 = new Weather();
        wp.setWeather(weather1);
        Weather actual = wp.getWeather();
        Assert.assertEquals(weather1,actual);
    }
}