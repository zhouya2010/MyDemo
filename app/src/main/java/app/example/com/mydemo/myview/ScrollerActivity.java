package app.example.com.mydemo.myview;

import android.os.Bundle;
import android.widget.Scroller;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

/**
 * Created by junjun on 16/8/11.
 */

@ContentView(R.layout.activity_scoller)
public class ScrollerActivity extends BaseActivity {

    @ViewInject(R.id.text_test)
    TextView textView;

    Scroller scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scroller = new Scroller(this);

    }



}
