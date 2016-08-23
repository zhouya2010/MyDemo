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
import rx.functions.Func2;
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

        Observable<Integer> observable1 = Observable.just(10,20,30);
        Observable<Integer> observable2 = Observable.just(4, 8, 12, 16);
        Observable.zip(observable1, observable2, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onNext(Integer value) {
                Log.d("RxJavaActivity", "Next:" + value);
            }
        });

    }
}
