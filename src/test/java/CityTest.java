import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

public class CityTest {
    City city = new City();
    @Before

    @org.junit.Test
    public void getCityName() {
        String expected = "Philadelphia";
        city.setCityName(expected);
        String actual = city.getCityName();
        Assert.assertEquals(expected,actual);

    }

    @org.junit.Test
    public void getStateName() {
        String expected = "Pennsylvania";
        city.setStateName(expected);
        String actual = city.getStateName();
        Assert.assertEquals(expected,actual);
    }
}