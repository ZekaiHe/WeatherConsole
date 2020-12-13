

public class Menu {

    public static String printInitial(){
        return String.format("PLEASE PRESS '1' TO START ADDING CITIES");
    }

    public static String printMenu(String previousCity, String nextCity){
        return String.format("1-ADD CITY * 2-REMOVE CITY * 3-SHOW %s * 4-SHOW %s * 5-REFRESH DATA * 6-CHANGE UNIT * 7-EXIT",previousCity, nextCity);
    }

    public static String printWeatherHeader(){
        return String.format("|City            |State           |Status  |Temp  |Feels Like  |High  |Low  |Press  |Hum  |Wind Spd  |Dir  |");
    }

    public static String printLine(){
        return String.format("+----------------+----------------+--------+------+------------+------+-----+-------+-----+----------+-----+");
    }

    //

    public static String printWeather(City activeC, Weather activeW){

        return String.format("|%-16s|%-16s|%-8s|%-6s|%-12s|%-6s|%-5s|%-7s|%-5s|%-10s|%-5s|",activeC.getCityName(),activeC.getStateName(),
                activeW.getCurrentWeather()[0].getMain(), activeW.getMainWeather().getTemp(), activeW.getMainWeather().getFeelsLike(),
                activeW.getMainWeather().getTempMax(),activeW.getMainWeather().getTempMin(),activeW.getMainWeather().getPressure(),
                activeW.getMainWeather().getHumidity(),activeW.getWind().getSpeed(),activeW.getWind().getDeg());
    }

    public static String printAddCity(){
        return String.format("PLEASE ENTER IN {CITY},{STATE<--FULL NAME} FORMAT:");
    }

    public static String printRemoveCity(String City){
        return String.format("YOU SURE YOU WANT TO REMOVE %s? '1' FOR YES * '2' FOR NO",City);
    }

    public static String printChangeUnits(String currentUnit, String changeToUnit){
        return String.format("YOUR CURRENT UNIT IS SET TO %s, DO YOU WANT TO CHANGE TO %s?\n"+
                "'1' FOR YES * '2' FOR NO",currentUnit,changeToUnit);
    }


}
