package app.example.com.mydemo.RxJava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;
import app.example.com.mydemo.retrofit.HttpInterface;
import app.example.com.mydemo.retrofit.ZoneData;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                .baseUrl("http://45.33.46.130")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

//        httpInterface.getWeather("9df624ae12694cd8d76144eacdcddfa6")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<WeatherData>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        textView.setText("onError");
//                    }
//
//                    @Override
//                    public void onNext(WeatherData weatherData) {
//                        textView.setText(weatherData.toString());
//                    }
//                });

        httpInterface.getZoneInfo2(5450)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZoneData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setText("onError=>" + e.toString());
                    }

                    @Override
                    public void onNext(ZoneData zoneData) {
                        textView.setText(""+zoneData.getError());
                    }
                });

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("RxJavaActivity", "integer:" + integer);
                    }
                });
    }
}
