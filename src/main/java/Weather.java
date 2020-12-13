import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("weather")
    private CurrentWeather[] currentWeather;
    @SerializedName("main")
    private MainWeather mainWeather;
    private Wind wind;

    public Weather(){
    }

    //for testing only
    public Weather(String whatever){
        CurrentWeather cw = new CurrentWeather();
        currentWeather = new CurrentWeather[]{cw};
        mainWeather = new MainWeather();
        wind = new Wind();
    }

    public class Wind {
        private String speed;
        private String deg;

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }
    }

    public class CurrentWeather {
        private String main;
        @Expose(deserialize = false)
        private String description;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }

    public class MainWeather {
        private String temp;
        @SerializedName("feels_like")
        private String feelsLike;
        @SerializedName("temp_min")
        private String tempMin;
        @SerializedName("temp_max")
        private String tempMax;
        private String pressure;
        private String humidity;

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(String feelsLike) {
            this.feelsLike = feelsLike;
        }

        public String getTempMin() {
            return tempMin;
        }

        public void setTempMin(String tempMin) {
            this.tempMin = tempMin;
        }

        public String getTempMax() {
            return tempMax;
        }

        public void setTempMax(String tempMax) {
            this.tempMax = tempMax;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }
    }

    public CurrentWeather[] getCurrentWeather() {
        return currentWeather;
    }

//    public void setCurrentWeather(CurrentWeather[] currentWeather) {
//        this.currentWeather = currentWeather;
//    }

    public MainWeather getMainWeather() {
        return mainWeather;
    }

//    public void setMainWeather(MainWeather mainWeather) {
//        this.mainWeather = mainWeather;
//    }

    public Wind getWind() {
        return wind;
    }

//    public void setWind(Wind wind) {
//        this.wind = wind;
//    }


}
