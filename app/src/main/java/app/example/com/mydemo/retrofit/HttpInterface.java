package app.example.com.mydemo.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/8/22.
 */

public interface HttpInterface {

    @GET("/api/rest/v2/device/getzone")
    Call<ZoneData> getZoneInfo(@Query("devid") int devid);

}
