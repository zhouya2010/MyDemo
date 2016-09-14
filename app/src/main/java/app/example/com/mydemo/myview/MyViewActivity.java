package app.example.com.mydemo.myview;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

/**
 * Created by Administrator on 2016/1/22.
 */

@ContentView(R.layout.my_view)
public class MyViewActivity extends BaseActivity {

    private MyQQHealthView view;
    public static List<Integer> sizes = new ArrayList<>();
    private Button btn;

    @ViewInject(R.id.rl)
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyViewActivity", "oncreat");
//        ArcView arcView = (ArcView) findViewById(R.id.myArcView);
//        arcView.setRankNum(3);
//        arcView.setWalkNum(8888);

        MyWaveView waveView = (MyWaveView) findViewById(R.id.wave_view);
        waveView.setInterpolator(new AccelerateInterpolator());
        waveView.run();

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.coin);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        rl.addView(imageView, params);

        imageView.setTranslationX(500);
        imageView.setTranslationY(500);

    }

}
