import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CityDaoTest {
    CityDao cd = new CityDao();
    Connection connection = CreateConnection.getConnection();
    String sqlTable = "testTable";
    @Before
    public void setUp() throws Exception {
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("CREATE TABLE "+ sqlTable +" (City varchar(255), State varchar(255))");
        if (i == 1) {
            System.out.println("table created");
        }
    }

    @After
    public void tearDown() throws Exception{
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("DROP TABLE testTable");
        if (i == 1) {
            System.out.println("table deleted");
        }
        connection.close();
    }

    @Test
    public void findAllCities() {
        String city1 = "Philadelphia";
        String state1 = "Pennsylvania";
        String city2 = "Miami";
        String state2= "Florida";
        Integer expectedListSize = 2;

        cd.addNewCity(city1,state1,connection,sqlTable);
        cd.addNewCity(city2,state2,connection,sqlTable);
        Integer actualListSize = cd.findAllCities(connection,sqlTable).size();

        Assert.assertEquals(expectedListSize,actualListSize);
    }

    @Test
    public void addNewCity() {
        String city = "Philadelphia";
        String state = "Pennsylvania";
        Assert.assertTrue(cd.addNewCity(city,state,connection, sqlTable));
    }

    @Test
    public void deleteCity() {
        String city = "Philadelphia";
        String state = "Pennsylvania";
        cd.addNewCity(city,state,connection,sqlTable);
        List<City> myLlist= cd.findAllCities(connection,sqlTable);
        City cityList = myLlist.get(0);

        cd.deleteCity(cityList,city,connection,sqlTable);

        Assert.assertTrue(myLlist.size()==0);
        Assert.assertTrue(cd.myCities.size()==0);
    }
}