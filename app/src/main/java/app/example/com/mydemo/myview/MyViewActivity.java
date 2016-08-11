package app.example.com.mydemo.myview;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import org.xutils.view.annotation.ContentView;

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
    }

//    private void initview() {
//        view = (MyQQHealthView) findViewById(R.id.myQQView);
//        view.setMySize(2345);
//        view.setRank(11);
//        view.setAverageSize(5436);
//        sizes.add(1234);
//        sizes.add(2234);
//        sizes.add(4234);
//        sizes.add(6234);
//        sizes.add(3834);
//        sizes.add(7234);
//        sizes.add(5436);
//        btn = (Button) findViewById(R.id.set_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.reSet(6534, 8, 4567);
//                Toast.makeText(MyViewActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
