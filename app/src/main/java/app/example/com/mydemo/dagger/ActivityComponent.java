package app.example.com.mydemo.dagger;

import android.app.Activity;

import app.example.com.mydemo.MainActivity;
import dagger.Component;

/**
 * Created by dell on 2016/11/1.
 */
@Component(dependencies = AppComponent.class,modules = ActivityMoudule.class)
public interface ActivityComponent {
    // 注入
    void inject(Activity activity);
}
