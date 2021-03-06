package app.example.com.mydemo.dagger;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dell on 2016/11/1.
 */

@Module
public class MainModule {

    private Context mContext;

    public MainModule(Context context){
        mContext = context;
    }

    @Provides
    Context providesContext(){
        // 提供上下文对象
        return mContext;
    }

    @Provides
    @Named("context")
    Person providerPerson(Context context){
        //提供Person对象
        return new Person(context);
    }

    @Provides
    @Named("male")
    Person providerPersonMale() {
        return new Person("male");
    }

    @Provides
    @Named("female")
    Person providerPersonFemale() {
        return new Person("female");
    }
}
