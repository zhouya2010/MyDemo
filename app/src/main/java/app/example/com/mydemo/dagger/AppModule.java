package app.example.com.mydemo.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dell on 2016/11/1.
 */
@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context){
        mContext = context;
    }

    @Provides
    Context providesContext(){
        // 提供Context对象　
        return mContext;
    }
}
