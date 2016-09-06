package app.example.com.mydemo.RxJava;

import android.os.AsyncTask;
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
                .flatMap(new Func1<WeatherData, Observable<WeatherData.DataBean.WeatherBean>>() {
                    @Override
                    public Observable<WeatherData.DataBean.WeatherBean> call(WeatherData weatherData) {
                        return Observable.from(weatherData.getData().getWeather());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData.DataBean.WeatherBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherData.DataBean.WeatherBean weatherBean) {
                        Log.d("RxJavaActivity", weatherBean.toString());
                    }
                });
    }

    public class NetOperator {

        public void operator(){
            try {
                //休眠1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {

        private TextView textView;

        public ProgressBarAsyncTask (TextView textView) {
            super();
            this.textView = textView;
        }

        @Override
        protected String doInBackground(Integer... params) {

            NetOperator netOperator = new NetOperator();
            int i = 0;
            for (i = 10; i <= 100; i+=10) {
                netOperator.operator();
                publishProgress(i);
            }
            return i + params[0].intValue() + "";
        }

        @Override
        protected void onPreExecute() {
            textView.setText("开始执行异步线程");
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText("异步操作执行结束" + s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText("异步操作执行..." + values);
        }
    }


}
