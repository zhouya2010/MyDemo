package app.example.com.mydemo.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

/**
 * Created by junjun on 16/8/8.
 */

@ContentView(R.layout.activity_first_ipc)
public class FirstActivity extends BaseActivity {

    @ViewInject(R.id.sent_message_btn)
    Button mSentMessageBtn;


    private Messenger mService;
    private boolean isBinding = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            isBinding = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            isBinding = false;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindService(new Intent(this, RemoteService.class), mConnection,BIND_AUTO_CREATE);
    }

    @Event(value ={R.id.sent_message_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.sent_message_btn:
                Log.d("FirstActivity", "sent message");
                //向进程B发一条消息，并接收来自进程B回复过来的消息
                Message message = Message.obtain(null, RemoteService.GET_RESULT);
                message.replyTo = mMessenger;
                try {
                    mService.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    //处理来自进程B回复的消息
    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RemoteService.GET_RESULT) {
                Log.i("TAG", "Int form process B is "+msg.arg1);//msg.arg1就是remoteInt
            } else {
                super.handleMessage(msg);
            }
        }
    });
}
