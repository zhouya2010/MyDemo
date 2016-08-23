package app.example.com.mydemo.RxJava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;
import app.example.com.mydemo.retrofit.HttpInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/18.
 */

@ContentView(R.layout.activity_rxjava)
public class RxJavaActivity extends BaseActivity {

    @ViewInject(R.id.rxjava_text)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://45.33.46.130")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        final HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        httpInterface.getConnectCloud("325EA1B7CB96E9C2")
              .flatMap(new Func1<ConnectCloudBean, Observable<WeatherData>>() {
                  @Override
                  public Observable<WeatherData> call(ConnectCloudBean connectCloudBean) {

                      Log.d("RxJavaActivity", connectCloudBean.getData().getConnuid());

                      return httpInterface.getWeather(connectCloudBean.getData().getConnuid());
                  }
              })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData>() {
                    @Override
                    public void onCompleted() {
                        Log.d("RxJavaActivity", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RxJavaActivity", "onError:  " + e.toString());
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        Log.d("RxJavaActivity", "weatherData.getData().getWeather():" + weatherData.getData().getRegion());
                        textView.setText(weatherData.getData().getRegion());
                    }
                });

//        httpInterface.getWeather("9df624ae12694cd8d76144eacdcddfa6")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<WeatherData>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(WeatherData weatherData) {
//                        Log.d("RxJavaActivity", "weatherData.getData().getWeather():" + weatherData.getData().getRegion());
//                    }
//                });
//
//        httpInterface.getZoneInfo2(5450)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ZoneData>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("RxJavaActivity", "e:" + e.toString());
////                        textView.setText("onError=>" + e.toString());
//                    }
//
//                    @Override
//                    public void onNext(ZoneData zoneData) {
//                        Log.d("RxJavaActivity", "zoneData.getError():" + zoneData.getError());
//                        textView.setText(""+zoneData.getData().size());
//                    }
//                });
//
//        Observable.just(1, 2, 3, 4)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        Log.d("RxJavaActivity", "integer:" + integer);
//                    }
//                });
    }
}
