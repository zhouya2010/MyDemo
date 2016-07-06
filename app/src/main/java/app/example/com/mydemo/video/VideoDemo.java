package app.example.com.mydemo.video;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;


@ContentView(R.layout.video)
public class VideoDemo extends BaseActivity {

    @ViewInject(R.id.video)
    VideoView video;

    MediaController mediaco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaco=new MediaController(this);
        String path = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/VID_20151002_160733.mp4";
        Log.d("VideoDemo", path);
        File file=new File(path);

        if(file.exists()){
            //VideoView与MediaController进行关联
            video.setVideoPath(file.getAbsolutePath());
            video.setMediaController(mediaco);
            mediaco.setMediaPlayer(video);
            //让VideiView获取焦点
            video.requestFocus();
        }


    }
}
