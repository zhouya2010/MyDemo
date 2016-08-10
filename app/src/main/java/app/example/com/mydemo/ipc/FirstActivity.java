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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

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
    IBookManager bookManager;

    IOnNewBookArrivedListener mOnNewBookArrivedListener;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mService = new Messenger(service);
//            isBinding = true;

            bookManager =  IBookManager.Stub.asInterface(service);
            try {
                List<Book> books = bookManager.getBookList();
                Toast.makeText(FirstActivity.this, "onServiceConnected--->" + books.size(), Toast.LENGTH_SHORT).show();
                for(Book book:books) {
                    Log.e("FirstActivity", "bookName -->" + book.bookName);
                }

                bookManager.addBook(new Book(4, "Android"));

                books = bookManager.getBookList();

                for(Book book:books) {
                    Log.e("FirstActivity", "bookName2 -->" + book.bookName);
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
//            isBinding = false;
            bookManager = null;
            Log.e("FirstActivity", "onServiceDisconnected");
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindService(new Intent(this, BookManagerService.class), mConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        Log.e("FirstActivity", "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e("FirstActivity", "onDestroy");
        unbindService(mConnection);
        super.onDestroy();
    }

    @Event(value ={R.id.sent_message_btn})
    private void click(View v) throws RemoteException {
        switch (v.getId()) {
            case R.id.sent_message_btn:
                Log.e("FirstActivity", "sent message");
                int n = bookManager.addBook(new Book(3,"IOS"));
                Log.e("FirstActivity", "books num----->:" + n);
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
