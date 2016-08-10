package app.example.com.mydemo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/8/9.
 */

public class BinderPoolService extends Service {

    private BinderPool mBinderPool = new BinderPool();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
