package app.example.com.mydemo.RxJava;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */

public class WeatherData {


    /**
     * error : 200
     * msg :
     * data : {"region":"Nanjing","weather":[{"descript":"Patchy rain nearby","humidity":"59","ave_temp":"73","min_temp":"50","max_temp":"73","rain_probability":"67","day":"4","sunrise":"06:11 AM","sunset":"06:15 PM","finetune":"91","tag":"0"},{"descript":"Overcast","humidity":"69","ave_temp":"69","min_temp":"58","max_temp":"69","rain_probability":"70","day":"3","sunrise":"06:12 AM","sunset":"06:14 PM","finetune":"92","tag":"0"},{"descript":"Sunny","humidity":"49","ave_temp":"68","min_temp":"53","max_temp":"68","rain_probability":"25","day":"2","sunrise":"06:14 AM","sunset":"06:13 PM","finetune":"88","tag":"0"},{"descript":"Partly Cloudy","humidity":"44","ave_temp":"59","min_temp":"47","max_temp":"59","rain_probability":"23","day":"1","sunrise":"06:15 AM","sunset":"06:13 PM","finetune":"89","tag":"0"},{"descript":"Sunny","humidity":"37","ave_temp":"62","min_temp":"41","max_temp":"62","rain_probability":"0","day":"0","sunrise":"06:16 AM","sunset":"06:12 PM","finetune":"94","tag":"0"}]}
     */

    private int error;
    private String msg;
    /**
     * region : Nanjing
     * weather : [{"descript":"Patchy rain nearby","humidity":"59","ave_temp":"73","min_temp":"50","max_temp":"73","rain_probability":"67","day":"4","sunrise":"06:11 AM","sunset":"06:15 PM","finetune":"91","tag":"0"},{"descript":"Overcast","humidity":"69","ave_temp":"69","min_temp":"58","max_temp":"69","rain_probability":"70","day":"3","sunrise":"06:12 AM","sunset":"06:14 PM","finetune":"92","tag":"0"},{"descript":"Sunny","humidity":"49","ave_temp":"68","min_temp":"53","max_temp":"68","rain_probability":"25","day":"2","sunrise":"06:14 AM","sunset":"06:13 PM","finetune":"88","tag":"0"},{"descript":"Partly Cloudy","humidity":"44","ave_temp":"59","min_temp":"47","max_temp":"59","rain_probability":"23","day":"1","sunrise":"06:15 AM","sunset":"06:13 PM","finetune":"89","tag":"0"},{"descript":"Sunny","humidity":"37","ave_temp":"62","min_temp":"41","max_temp":"62","rain_probability":"0","day":"0","sunrise":"06:16 AM","sunset":"06:12 PM","finetune":"94","tag":"0"}]
     */

    private DataBean data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String region;
        /**
         * descript : Patchy rain nearby
         * humidity : 59
         * ave_temp : 73
         * min_temp : 50
         * max_temp : 73
         * rain_probability : 67
         * day : 4
         * sunrise : 06:11 AM
         * sunset : 06:15 PM
         * finetune : 91
         * tag : 0
         */

        private List<WeatherBean> weather;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public List<WeatherBean> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherBean> weather) {
            this.weather = weather;
        }

        public static class WeatherBean {
            private String descript;
            private String humidity;
            @SerializedName("ave_temp")
            private String aveTemp;
            @SerializedName("min_temp")
            private String minTemp;
            @SerializedName("max_temp")
            private String maxTemp;
            @SerializedName("rain_probability")
            private String rainProbability;
            private String day;
            private String sunrise;
            private String sunset;
            private String finetune;
            private String tag;

            public String getDescript() {
                return descript;
            }

            public void setDescript(String descript) {
                this.descript = descript;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getAveTemp() {
                return aveTemp;
            }

            public void setAveTemp(String aveTemp) {
                this.aveTemp = aveTemp;
            }

            public String getMinTemp() {
                return minTemp;
            }

            public void setMinTemp(String minTemp) {
                this.minTemp = minTemp;
            }

            public String getMaxTemp() {
                return maxTemp;
            }

            public void setMaxTemp(String maxTemp) {
                this.maxTemp = maxTemp;
            }

            public String getRainProbability() {
                return rainProbability;
            }

            public void setRainProbability(String rainProbability) {
                this.rainProbability = rainProbability;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public String getFinetune() {
                return finetune;
            }

            public void setFinetune(String finetune) {
                this.finetune = finetune;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }
        }
    }
}
