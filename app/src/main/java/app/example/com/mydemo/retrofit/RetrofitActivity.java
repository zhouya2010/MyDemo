package app.example.com.mydemo.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/8/22.
 */

public class RetrofitActivity extends BaseActivity {

    @ViewInject(R.id.retrofit_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://45.33.46.130")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        final Call<ZoneData> zoneDatas =  httpInterface.getZoneInfo(5450);

       zoneDatas.enqueue(new Callback<ZoneData>() {
           @Override
           public void onResponse(Call<ZoneData> call, Response<ZoneData> response) {
               ZoneData data = response.body();
//               List<ZoneInfo> datas = new ArrayList<ZoneInfo>();
//               datas.addAll(data.datas);

               textView.setText("size =  " + data.getData().size());
           }

           @Override
           public void onFailure(Call<ZoneData> call, Throwable t) {
               textView.setText("onFailure");
           }
       });

    }


}
