import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherEngine {
    Connection connection;
    CityDao cd;
    FetchNimbus fn;
    ConsoleUtil console;
    String uriUnits;
    String units;
    String sqlTable;

    private ArrayList<WeatherPair> myWeatherList;
    private HashMap<String,String> cityStateList;
    private Integer scrollerState;

    public WeatherEngine(String sqlTable){
        connection = CreateConnection.getConnection(); //start connection on instance of Engine
        cd = new CityDao();
        fn = new FetchNimbus();
        console = new ConsoleUtil();

        myWeatherList = new ArrayList<WeatherPair>(); //need to load the city and weather in
        cityStateList = new HashMap<String, String>();
        uriUnits = FetchNimbus.units.IMPERIAL.unit; //default unit
        units = uriUnits.substring(7,uriUnits.lastIndexOf("&")).toUpperCase();
        scrollerState = 0;
        this.sqlTable = sqlTable;
    }

    public ArrayList<WeatherPair> getMyWeatherList(){
        return myWeatherList;
    }

    public void setScrollerState(Integer input){
        this.scrollerState = input;
    }

    public Integer getScrollerState(){
        return scrollerState;
    }

    public void start(){
        boolean stopLoop = false;
        loadVeriCities();
        while(!stopLoop){
            loadInData(uriUnits);
            feedWeather();
            try {
                switch (console.readIntInput()) {
                    case 1:
                        console.print(Menu.printAddCity());
                        String[] cityToAdd = checkLegitCity();
                        if (!cd.addNewCity(cityToAdd[0].trim(), cityToAdd[1].trim(), connection, sqlTable)) {
                            console.print("CITY ALREADY IN FAVORITES");
                        }
                        break;
                    case 2:
                        console.print(Menu.printRemoveCity(myWeatherList.get(scrollerState).getCity().getCityName()));
                        if (console.readIntInput() == 1) {
                            removeCity(scrollerState);
                        }
                        break;
                    case 3:
                        scroller(3);
                        break;
                    case 4:
                        scroller(4);
                        break;
                    case 5:
                        updateWeather(uriUnits);
                        break;
                    case 6:
                        String newUnit = (units.equals("IMPERIAL")) ? "METRIC" : "IMPERIAL";
                        console.print(Menu.printChangeUnits(units, newUnit));
                        changeUnit(units);
                        if (console.readIntInput() == 1) {
                            updateWeather(uriUnits);
                        }
                        break;
                    case 7:
                        stopLoop = true;
                    default:
                        console.print("NOT AN OPTION");
                }
            }
            catch(NullPointerException e){
            }
        }
    }

    public boolean changeUnit(String unit){
        if(unit.equals("IMPERIAL")) {
            units = "METRIC";
            uriUnits = FetchNimbus.units.METRIC.unit;
            return true;
        }
        if(unit.equals("METRIC")){
            units = "IMPERIAL";
            uriUnits = FetchNimbus.units.IMPERIAL.unit;
            return true;
        }
        return false;
    }

    public String[] checkLegitCity() {
        boolean loop = true ;
        while (loop) {
            String userinput = console.readStrInput();
            if(checkLegitCitySub(userinput)!=null){
                loop = false;
                return checkLegitCitySub(userinput);
            }
        }
        return null;
    }

    public String[] checkLegitCitySub(String userInput){
        String[] newCity = userInput.split(",");
        String checkCity = newCity[0].replaceAll(" ","").toLowerCase().trim();
        String checkState = newCity[1].replaceAll(" ","").toLowerCase().trim();
        if (cityStateList.get(checkCity)!=null) {
            if(cityStateList.get(checkCity).equals(checkState)) {
                String[] addCity = {checkCity, checkState};
                return addCity;
            }
        }
        console.print("PLEASE ENTER VALID CITY AND STATE");
        return null;
    }

    public void feedWeather() {
        if(dbHasCities()) {
            printWeather(myWeatherList.get(scrollerState).getCity(), myWeatherList.get(scrollerState).getWeather());
        }else{
            console.print(Menu.printInitial());
        }
    }

    public void loadVeriCities(){
        try {
            List<String> stream = Files.lines(Paths.get("CitiesStatesList.txt"))
                    .collect(Collectors.toList());
            for(String s: stream){
                cityStateList.put(s.split(",")[0].toLowerCase().trim(),s.split(",")[1].toLowerCase().trim());
            }
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("An error occurred");
        }
    }

    //can be used for adding new city too
    public void loadInData(String units){
        boolean foundCity = false;
        for(City c:cd.findAllCities(connection,sqlTable)){
            //provides weather for each city in list and adds to a WeatherPair object to add to my
            for(WeatherPair wp: myWeatherList) {
                if(wp.getCity().getCityName().equals(c.getCityName())){
                    foundCity = true;
                    break;
                }
                foundCity = false;
            }
            if(!foundCity) {
                myWeatherList.add(new WeatherPair(c, fn.connectToWeather(c.getCityName(), c.getStateName(), units)));
            }
        }
    }

    public boolean removeCity(Integer cityIndex){
        try {
            cd.deleteCity(myWeatherList.get(cityIndex).getCity(),myWeatherList.get(cityIndex).getCity().getCityName(), connection,sqlTable);
            myWeatherList.remove((int)cityIndex);
            scrollerState = (cityIndex == myWeatherList.size())?myWeatherList.size()-1:scrollerState;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateWeather(String units){
        try {
            for (WeatherPair wp : myWeatherList) {
                wp.setWeather(fn.connectToWeather(wp.getCity().getCityName(), wp.getCity().getStateName(), units));
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void scroller(Integer input){
        if(input == 3 && scrollerState>0){
            scrollerState--;
        }
        if(input == 4 && scrollerState < myWeatherList.size()-1){
            scrollerState++;
        }
    }

    public void printWeather(City city, Weather weather){
        console.print(Menu.printLine()+"\n"+
        Menu.printWeatherHeader()+"\n"+
        Menu.printLine()+"\n"+
        Menu.printWeather(city,weather)+"\n"+
        Menu.printLine()+"\n"+
        Menu.printMenu(findPreviousCity(city),findNextCity(city)));
    }

    public boolean dbHasCities(){
        if(myWeatherList.size() == 0){
            return false;
        }
        return true;
    }

    public String findPreviousCity(City city){
        Integer i = -1;
        for(WeatherPair wp: myWeatherList){
            i = (wp.getCity()==city)? myWeatherList.indexOf(wp):i;
        }
        if(i!=-1 && i>0) { //make sure the city displayed is not first city to get a previous city
            return myWeatherList.get(i - 1).getCity().getCityName().toUpperCase();
        }
        else{
            return "N/A";
        }
    }

    public String findNextCity(City city){
        Integer i = -1;
        for(WeatherPair wp: myWeatherList){
            i = (wp.getCity()==city)? myWeatherList.indexOf(wp):i;
        }
        if(i!=-1 && i< myWeatherList.size()-1) { //make sure the city displayed is not first city to get a previous city
            return myWeatherList.get(i + 1).getCity().getCityName().toUpperCase();
        }
        else{
            return "N/A";
        }
    }

}
