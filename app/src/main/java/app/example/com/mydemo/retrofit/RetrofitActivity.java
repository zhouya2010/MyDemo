package app.example.com.mydemo.retrofit;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.util.Log;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;
import app.example.com.mydemo.dagger.ActivityMoudule;
import app.example.com.mydemo.dagger.AppComponent;
import app.example.com.mydemo.dagger.AppModule;
import app.example.com.mydemo.dagger.DaggerActivityComponent;
import app.example.com.mydemo.dagger.DaggerAppComponent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/22.
 */

@ContentView(R.layout.activity_retrofit)
public class RetrofitActivity extends BaseActivity {

    @ViewInject(R.id.retrofit_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://45.33.46.130")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<ZoneData> zoneDatas =  httpInterface.getZoneInfo(5450);

       zoneDatas.enqueue(new Callback<ZoneData>() {
           @Override
           public void onResponse(Call<ZoneData> call, Response<ZoneData> response) {
               ZoneData data = response.body();
               if(data != null) {
                   Log.d("RetrofitActivity", "data.getError():" + data.getError());
                   textView.setText("size =  " + data.getError());
               }

           }

           @Override
           public void onFailure(Call<ZoneData> call, Throwable t) {
               textView.setText("onFailure");
           }
       });

        AppComponent con = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        DaggerActivityComponent.builder().appComponent(con).activityMoudule(new ActivityMoudule()).build().inject(this);

    }


}
