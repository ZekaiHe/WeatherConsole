import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class City {
    private String City;
    private String State;

    //private static List<City> myCities = new ArrayList<City>();

    public City(){
    }

/*    public static List<City> getMyCities() {
        return myCities;
    }*/

    public String getCityName() {
        return City;
    }

    public void setCityName(String city) {
        City = city;
    }

    public String getStateName() {
        return State;
    }

    public void setStateName(String state) {
        State = state;
    }
}
