public class WeatherPair {
    private City city;
    private Weather weather;

    public WeatherPair(City city, Weather weather){
        this.city = city;
        this.weather = weather;
    }

    public City getCity() {
        return city;
    }

/*    public void setCity(City city) {
        this.city = city;
    }*/

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
