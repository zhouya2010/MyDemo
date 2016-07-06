package app.example.com.mydemo.download;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

/**
 * Created by Administrator on 2016/3/23.
 */

@ContentView(R.layout.activity_download)
public class DownLoadActivity extends BaseActivity {

    @ViewInject(R.id.btn_download)
    Button btnDownload;

    @ViewInject(R.id.tx_download_info)
    TextView tx;

    private DownloadManager downloadManager;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadManager = DownloadManager.getInstance(getApplicationContext());
        handler = new Handler();
    }


    @Event(R.id.btn_download)
    private void click(View view) {
        Log.d("DownLoadActivity", "开始下载");

        DownloadTask task = new DownloadTask();
        task.setId("3");
        task.setSaveDirPath(Environment.getExternalStorageDirectory().getPath() + "/Download/");
        task.setFileName("QQMusic.apk");
        task.setUrl("http://dldir1.qq.com/music/clntupate/QQMusic.apk");
        downloadManager.addDownloadTask(task, new DownloadTaskListener() {
            @Override
            public void onPrepare(DownloadTask downloadTask) {

                handler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                    tx.setText("prepare");
                                 }
                             });

                Log.d("DownLoadActivity", "onPrepare");
            }

            @Override
            public void onStart(DownloadTask downloadTask) {
                Log.d("DownLoadActivity", "onStart");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tx.setText("onStart");
                    }
                });
            }

            @Override
            public void onDownloading(final DownloadTask downloadTask) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tx.setText((int) downloadTask.getPercent() + "%       ");
                    }
                });
            }

            @Override
            public void onPause(DownloadTask downloadTask) {
                Log.d("DownLoadActivity", "onPause");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tx.setText("onPause");
                    }
                });
            }

            @Override
            public void onCancel(DownloadTask downloadTask) {
                Log.d("DownLoadActivity", "onCancel");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tx.setText("onCancel");
                    }
                });
            }

            @Override
            public void onCompleted(DownloadTask downloadTask) {
                Log.d("DownLoadActivity", "onCompleted");
            }

            @Override
            public void onError(DownloadTask downloadTask, int errorCode) {
                Log.d("DownLoadActivity", "onError");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tx.setText("onError");
                    }
                });
            }
        });
    }

}
