package app.example.com.mydemo.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/6.
 */

    public interface ZhihuApi {

        @GET("/api/4/news/latest")
        Observable<ZhihuDaily> getLastDaily();

        @GET("/api/4/news/before/{date}")
        Observable<ZhihuDaily> getTheDaily(@Path("date") String date);

        @GET("/api/4/news/{id}")
        Observable<ZhihuStory> getZhihuStory(@Path("id") String id);
    }
