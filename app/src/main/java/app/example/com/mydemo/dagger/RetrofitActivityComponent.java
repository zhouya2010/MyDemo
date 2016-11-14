package app.example.com.mydemo.dagger;

import app.example.com.mydemo.retrofit.RetrofitActivity;
import dagger.Component;

/**
 * Created by dell on 2016/11/2.
 */

@Component(modules = {MainModule.class})
public interface RetrofitActivityComponent {
    void inject(RetrofitActivity activity);
}
