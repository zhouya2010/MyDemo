package app.example.com.mydemo.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dell on 2016/11/1.
 */

@Module
public class ActivityMoudule {
    @Provides
    Person providePerson(Context context){
        //　此方法需要Context 对象
        return new Person(context);
    }
}
