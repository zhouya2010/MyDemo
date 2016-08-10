package app.example.com.mydemo.MVC;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

/**
 * Created by Administrator on 2016/7/25.
 */

public class CountActivity extends BaseActivity  implements UpdateListener {

    private Button addBtn;
    private Button subBtn;
    private TextView showText;

    private CountModule countModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        addBtn = (Button) findViewById(R.id.count_add_btn);
        subBtn = (Button) findViewById(R.id.count_sub_btn);
        showText = (TextView) findViewById(R.id.show_text);

        countModule = new CountModule(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countModule.add(10);
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countModule.sub(10);
            }
        });

    }

    @Override
    public void update(int count) {
        showText.setText(""+ count);
    }

}
