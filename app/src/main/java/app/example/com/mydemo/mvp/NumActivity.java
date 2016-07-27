package app.example.com.mydemo.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.example.com.mydemo.R;

/**
 * Created by Administrator on 2016/7/26.
 */

public class NumActivity extends AppCompatActivity implements NumView, View.OnClickListener {

    private Button addBtn;
    private Button subBtn;
    private Button autoBtn;
    private TextView text;

    private NumPresenterImpl numPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_mvp);

        addBtn = (Button) findViewById(R.id.mvp_add);
        subBtn = (Button) findViewById(R.id.mvp_sub);
        autoBtn = (Button) findViewById(R.id.mvp_auto);
        text = (TextView) findViewById(R.id.mvp_text);

        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        autoBtn.setOnClickListener(this);

        numPresenter = new NumPresenterImpl(this);
    }

    @Override
    public void update(int num) {
        text.setText(""+num);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mvp_add:
                numPresenter.add(1);
                break;
            case R.id.mvp_sub:
                numPresenter.sub(1);
                break;
            case R.id.mvp_auto:
                numPresenter.autoRun(2);
                break;
        }
    }
}
