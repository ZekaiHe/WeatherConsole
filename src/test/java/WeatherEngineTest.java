import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.*;

public class WeatherEngineTest {
    WeatherEngine we;
    String sqlTable = "testTable";
    Connection connection = CreateConnection.getConnection();
    @Before
    public void setUp() throws Exception {
        we = new WeatherEngine(sqlTable);
        we.loadVeriCities();
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("CREATE TABLE "+ sqlTable +" (City varchar(255), State varchar(255))");
        if (i == 1) {
            System.out.println("table created");
        }
    }

    @After
    public void tearDown() throws Exception {
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("DROP TABLE testTable");
        if (i == 1) {
            System.out.println("table deleted");
        }
        connection.close();
    }

    @Test
    public void changeUnit1() {
        String units = "IMPERIAL";
        we.changeUnit(units);
        Assert.assertTrue(we.units == "METRIC");
    }

    @Test
    public void changeUnit2() {
        String units = "METRIC";
        we.changeUnit(units);
        Assert.assertTrue(we.units == "IMPERIAL");
    }

    @Test
    public void checkLegitCitySub() {
        String city = "philadelphia";
        String state = "pennsylvania";
        String[] expected ={city,state};
        String[] actual = we.checkLegitCitySub(city+","+state);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void feedWeather() {

    }

    @Test
    public void loadInData() {
        String city = "philadelphia";
        String state = "pennsylvania";
        we.cd.addNewCity(city,state,we.connection,sqlTable);

        we.loadInData(we.uriUnits);
        City myCity = we.getMyWeatherList().get(0).getCity();
        Weather weather = we.getMyWeatherList().get(0).getWeather();
        Assert.assertTrue(myCity instanceof City && weather instanceof Weather);
        Assert.assertEquals(city,myCity.getCityName());
;    }

    @Test
    public void removeCity() {
        String city = "philadelphia";
        String state = "pennsylvania";
        we.cd.addNewCity(city,state,we.connection,sqlTable);
        we.loadInData(we.uriUnits);

        we.removeCity(0);

        Assert.assertTrue(we.getMyWeatherList().size() == 0);
    }

    @Test
    public void updateWeather() {
        String city = "philadelphia";
        String state = "pennsylvania";
        we.cd.addNewCity(city,state,we.connection,sqlTable);
        we.loadInData(we.uriUnits);

        Assert.assertTrue(we.updateWeather(we.uriUnits));
    }

    @Test
    public void scroller1() {
        String city1 = "philadelphia";
        String state1 = "pennsylvania";
        String city2 = "baltimore";
        String state2 = "maryland";
        String city3 = "miami";
        String state3 = "florida";
        we.cd.addNewCity(city1,state1,we.connection,sqlTable);
        we.cd.addNewCity(city2,state2,we.connection,sqlTable);
        we.cd.addNewCity(city3,state3,we.connection,sqlTable);
        we.loadInData(we.uriUnits);
        we.setScrollerState(1);

        we.scroller(3);

        Assert.assertTrue(we.getScrollerState() == 0);
    }

    @Test
    public void scroller2() {
        String city1 = "philadelphia";
        String state1 = "pennsylvania";
        String city2 = "baltimore";
        String state2 = "maryland";
        String city3 = "miami";
        String state3 = "florida";
        we.cd.addNewCity(city1,state1,we.connection,sqlTable);
        we.cd.addNewCity(city2,state2,we.connection,sqlTable);
        we.cd.addNewCity(city3,state3,we.connection,sqlTable);
        we.loadInData(we.uriUnits);
        we.setScrollerState(1);

        we.scroller(4);

        Assert.assertTrue(we.getScrollerState() == 2);
    }

    @Test
    public void scroller3() {
        String city1 = "philadelphia";
        String state1 = "pennsylvania";
        String city2 = "baltimore";
        String state2 = "maryland";
        String city3 = "miami";
        String state3 = "florida";
        we.cd.addNewCity(city1,state1,we.connection,sqlTable);
        we.cd.addNewCity(city2,state2,we.connection,sqlTable);
        we.cd.addNewCity(city3,state3,we.connection,sqlTable);
        we.loadInData(we.uriUnits);
        we.setScrollerState(2);

        we.scroller(4);

        Assert.assertTrue(we.getScrollerState() == 2);
    }

    @Test
    public void dbHasCities() {
        Assert.assertFalse(we.dbHasCities());
    }

    @Test
    public void findPreviousCity() {
        String city1 = "philadelphia";
        String state1 = "pennsylvania";
        String city2 = "baltimore";
        String state2 = "maryland";
        String city3 = "miami";
        String state3 = "florida";
        we.cd.addNewCity(city1,state1,we.connection,sqlTable);
        we.cd.addNewCity(city2,state2,we.connection,sqlTable);
        we.cd.addNewCity(city3,state3,we.connection,sqlTable);
        we.loadInData(we.uriUnits);
        we.setScrollerState(1);

        String expected = city1;
        String actual = we.findPreviousCity(we.getMyWeatherList().get(we.getScrollerState()).getCity()).toLowerCase();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findNextCity() {
        String city1 = "philadelphia";
        String state1 = "pennsylvania";
        String city2 = "baltimore";
        String state2 = "maryland";
        String city3 = "miami";
        String state3 = "florida";
        we.cd.addNewCity(city1,state1,we.connection,sqlTable);
        we.cd.addNewCity(city2,state2,we.connection,sqlTable);
        we.cd.addNewCity(city3,state3,we.connection,sqlTable);
        we.loadInData(we.uriUnits);
        we.setScrollerState(1);

        String expected = city3;
        String actual = we.findNextCity(we.getMyWeatherList().get(we.getScrollerState()).getCity()).toLowerCase();

        Assert.assertEquals(expected,actual);
    }
}