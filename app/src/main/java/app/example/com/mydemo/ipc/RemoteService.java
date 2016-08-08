package app.example.com.mydemo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by junjun on 16/8/8.
 */

public class RemoteService extends Service{

    public static final int GET_RESULT = 1;
    private final Messenger mMessenger = new Messenger(new Handler() {
        private int remoteInt = 1;//返回到进程A的值
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_RESULT) {
                try {
                    msg.replyTo.send(Message.obtain(null, GET_RESULT, remoteInt++, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                super.handleMessage(msg);
            }
        }
    });



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
