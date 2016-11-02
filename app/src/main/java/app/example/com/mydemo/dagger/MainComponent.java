package app.example.com.mydemo.dagger;

import javax.inject.Singleton;

import app.example.com.mydemo.MainActivity;
import dagger.Component;
import dagger.Module;

/**
 * Created by dell on 2016/11/1.
 */
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    //定义注入的方法
    void inject(MainActivity activity);
}
