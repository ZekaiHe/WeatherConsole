import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MenuTest {
    City city = new City();
    Weather weather = new Weather("hi");
    @Before
    public void setup(){
        city.setCityName("Philadelphia");
        city.setStateName("Pennsylvania");
        weather.getCurrentWeather()[0].setMain("Clear");
        weather.getWind().setDeg("180");
        weather.getWind().setSpeed("15");
        weather.getMainWeather().setFeelsLike("55");
        weather.getMainWeather().setTemp("60");
        weather.getMainWeather().setHumidity("50");
        weather.getMainWeather().setTempMax("65");
        weather.getMainWeather().setTempMin("45");
        weather.getMainWeather().setPressure("1080");
    }

    @Test
    public void printInitial() {
        String expected = "PLEASE PRESS '1' TO START ADDING CITIES";
        String actual = Menu.printInitial();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void printMenu() {
        String thisCity = "PHILADELPHIA";
        String nextCity = "BALTIMORE";
        String expected = String.format("1-ADD CITY * 2-REMOVE CITY * 3-SHOW %s * 4-SHOW %s * 5-REFRESH DATA * 6-CHANGE UNIT * 7-EXIT",thisCity,nextCity);
        String actual = Menu.printMenu(thisCity,nextCity);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void printWeatherHeader() {
        String expected = "|City            |State           |Status  |Temp  |Feels Like  |High  |Low  |Press  |Hum  |Wind Spd  |Dir  |";
        String actual = Menu.printWeatherHeader();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void printLine() {
        String expected = "+----------------+----------------+--------+------+------------+------+-----+-------+-----+----------+-----+";
        String actual = Menu.printLine();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void printWeather() {
        String expected = String.format("|%-16s|%-16s|%-8s|%-6s|%-12s|%-6s|%-5s|%-7s|%-5s|%-10s|%-5s|",city.getCityName(),city.getStateName(),
                weather.getCurrentWeather()[0].getMain(), weather.getMainWeather().getTemp(), weather.getMainWeather().getFeelsLike(),
                weather.getMainWeather().getTempMax(),weather.getMainWeather().getTempMin(),weather.getMainWeather().getPressure(),
                weather.getMainWeather().getHumidity(),weather.getWind().getSpeed(),weather.getWind().getDeg());
        String actual = Menu.printWeather(city,weather);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void printAddCity() {
        String expected = "PLEASE ENTER IN {CITY},{STATE<--FULL NAME} FORMAT:";
        String actual = Menu.printAddCity();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void printRemoveCity() {
        String city = "Philadelphia";
        String expected = String.format("YOU SURE YOU WANT TO REMOVE %s? '1' FOR YES * '2' FOR NO",city);
        String actual = Menu.printRemoveCity(city);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void printChangeUnits() {
        String currentUnit = "IMPERIAL";
        String newUnit = "METRIC";
        String expected = String.format("YOUR CURRENT UNIT IS SET TO %s, DO YOU WANT TO CHANGE TO %s?\n"+
                "'1' FOR YES * '2' FOR NO",currentUnit,newUnit);
        String actual = Menu.printChangeUnits(currentUnit,newUnit);
        Assert.assertEquals(expected,actual);
    }
}