package app.example.com.mydemo.dagger;

import android.content.Context;

import dagger.Component;

/**
 * Created by dell on 2016/11/1.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    // 向其下层提供Context 对象
    Context proContext();
}
