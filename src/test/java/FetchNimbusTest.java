import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class FetchNimbusTest {
    FetchNimbus fn;
    @Before
    public void setUp() throws Exception {
       fn = new FetchNimbus();
    }

    @Test
    public void connectToWeather() {
        String city = "Philadelphia";
        String state = "Pennsylvania";
        String unit = FetchNimbus.units.IMPERIAL.unit;
        Weather w = fn.connectToWeather(city,state,unit);
        Assert.assertTrue(w instanceof Weather);
    }
}