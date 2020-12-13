import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao {
    List<City> myCities = new ArrayList<City>();

    public City extractCities(ResultSet rs) throws SQLException {
        City city = new City();
        city.setCityName(rs.getString("City"));
        city.setStateName(rs.getString("State"));
        return city;
    }

    public List<City> findAllCities(Connection connection, String table) {

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM "+table);
            while(rs.next()){
                if(myCities.size()==0){
                    myCities.add(extractCities(rs));
                }
                boolean found = false;
                for(City c: myCities){
                    String tempcity = rs.getString("City");
                    String tempstate = rs.getString("State");
                    if(c.getCityName().equals(tempcity) && c.getStateName().equals(tempstate)){
                        found = true;
                    }
                }
                if(!found){
                    myCities.add(extractCities(rs));
                }

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return myCities;
    }

    public boolean addNewCity(String city, String state, Connection connection, String table){

        for(City c: myCities){
            if(c.getCityName().equals(city) && c.getStateName().equals(state)){
                return false;
            }
        }

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO "+table+" VALUES(?,?)");
            ps.setString(1, city);
            ps.setString(2, state);
            int i = ps.executeUpdate();
            if(i == 1){
                System.out.printf("%s ADDED\n", city);
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCity(City cityObj, String city, Connection connection, String table){
        try {
            Statement statement = connection.createStatement();
            int i = statement.executeUpdate("DELETE FROM "+table+" WHERE City ="+ '"'+ city + '"');
            if(i == 1){
                myCities.remove(cityObj);
                System.out.printf("%s DELETED\n", city);
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
