package app.example.com.mydemo.retrofit;

import app.example.com.mydemo.RxJava.ConnectCloudBean;
import app.example.com.mydemo.RxJava.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/22.
 */

public interface HttpInterface {

    @GET("/api/rest/v2/device/conncloud")
    Observable<ConnectCloudBean> getConnectCloud(@Query("dno") String dno);

    @GET("/api/rest/v2/device/getzone")
    Call<ZoneData> getZoneInfo(@Query("devid") int devid);

    @GET("/api/rest/v2/device/getzone")
    Observable<ZoneData> getZoneInfo2(@Query("devid") int devid);

    @GET("/api/rest/v2/device/getweatherbydevid")
    Observable<WeatherData> getWeather(@Query("cno") String cno);

}
