package app.example.com.mydemo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import javax.inject.Inject;

import app.example.com.mydemo.CollapsingToolbar.ScrollingActivity;
import app.example.com.mydemo.RxJava.RxJavaActivity;
import app.example.com.mydemo.bitmap.LargeImageViewActivity;
import app.example.com.mydemo.contact.ContactListActivity;
import app.example.com.mydemo.dagger.DaggerMainComponent;
import app.example.com.mydemo.dagger.MainComponent;
import app.example.com.mydemo.dagger.MainModule;
import app.example.com.mydemo.dagger.Person;
import app.example.com.mydemo.database.SprayActivity;
import app.example.com.mydemo.download.DownLoadActivity;
import app.example.com.mydemo.ipc.SecondActivity;
import app.example.com.mydemo.mvp.NumActivity;
import app.example.com.mydemo.myview.MyViewActivity;
import app.example.com.mydemo.retrofit.RetrofitActivity;
import app.example.com.mydemo.video.VideoDemo;
import app.example.com.mydemo.wifi.WifiSettings;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.contact_btn)
    Button contact_btn;

    @ViewInject(R.id.video_btn)
    Button video_btn;

    @ViewInject(R.id.my_view_btn)
    Button my_view;

    @ViewInject(R.id.data_base_btn)
    Button my_test;

    @ViewInject(R.id.download_btn)
    Button my_download;

    @ViewInject(R.id.wifi_btn)
    Button my_wifi;

    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVAnalytics.trackAppOpened(getIntent());

        Log.d(TAG, "This is dev branch");

        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername("zhouya");// 设置用户名
        user.setPassword("123456");// 设置密码
        user.setEmail("ya.zhou@nxeco.com");// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    Log.d(TAG, "done: 注册成功");
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                }
            }
        });

    }


    @Event(value = {R.id.contact_btn,R.id.my_view_btn,R.id.video_btn,R.id.data_base_btn, R.id.download_btn,
            R.id.wifi_btn, R.id.mvc_test_btn, R.id.ipc_test_btn, R.id.collapsing_test_btn,R.id.bitmap_test_btn,
            R.id.rxjava_test_btn, R.id.retrofit_test_btn, R.id.tcp_btn})
    private void click(View v){
        Intent intent = null;
        switch (v.getId()) {
            case R.id.contact_btn:
                intent = new Intent(this, ContactListActivity.class);
                Toast.makeText(this, "contact_btn", Toast.LENGTH_SHORT).show();
                break;

            case R.id.video_btn:
                Toast.makeText(this, "video_btn", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, VideoDemo.class);
                break;

            case R.id.my_view_btn:
                Toast.makeText(this, "my_view", Toast.LENGTH_SHORT).show();
//                intent = new Intent(this, TouchEventActivity.class);
                intent = new Intent(this, MyViewActivity.class);

                break;

            case R.id.data_base_btn:
                Toast.makeText(this, "data base", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, SprayActivity.class);
                break;

            case R.id.download_btn:
                Toast.makeText(this, "download", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, DownLoadActivity.class);
                break;

            case R.id.wifi_btn:
                Toast.makeText(this, "wifi", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, WifiSettings.class);
                break;

            case R.id.mvc_test_btn:
                intent = new Intent(this, NumActivity.class);
                break;

            case R.id.ipc_test_btn:
                intent = new Intent(this, SecondActivity.class);
                break;

            case R.id.collapsing_test_btn:
                intent = new Intent(this, ScrollingActivity.class);
                break;

            case R.id.bitmap_test_btn:
//                intent = new Intent(this, BitmapActivity.class);
                intent = new Intent(this, LargeImageViewActivity.class);
                break;

            case R.id.rxjava_test_btn:
                intent = new Intent(this, RxJavaActivity.class);
                break;

            case R.id.retrofit_test_btn:
                intent = new Intent(this, RetrofitActivity.class);
                break;

            case R.id.tcp_btn:
                break;

            default:
                break;
        }

        if (intent != null){
            startActivity(intent);
//            this.finish();
            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
//            overridePendingTransition(R.anim.activity_right_in, android.R.anim.slide_out_right);
        }

    }


    public class Stu {

        private  int yuwen;
        private int suxue;

        public Stu(int yuwen, int suxue) {
            this.yuwen = yuwen;
            this.suxue = suxue;
        }

        public int getYuwen() {
            return yuwen;
        }

        public void setYuwen(int yuwen) {
            this.yuwen = yuwen;
        }

        public int getSuxue() {
            return suxue;
        }

        public void setSuxue(int suxue) {
            this.suxue = suxue;
        }
    }


}
