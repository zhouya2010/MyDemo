package app.example.com.mydemo.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

/**
 * Created by junjun on 16/9/21.
 */

@ContentView(R.layout.activity_second_ipc)
public class SecondActivity extends BaseActivity {


    private IStu iStu;
    private int id = 1;

    @ViewInject(R.id.edit_name)
    private EditText editName;

    @ViewInject(R.id.edit_age)
    private EditText editAge;

    @ViewInject(R.id.edit_id)
    private EditText editId;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iStu =  IStu.Stub.asInterface(service);
            try {
                List<Stu> stus = iStu.query();

                if (stus.size() > 0) {
                    Log.d("SecondActivity", "stus.size():" + stus.size());
                }
                else {
                    Log.d("SecondActivity", "stus is empty");
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iStu = null;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SecondActivity", "onCreate");
        bindService(new Intent(this,SecondService.class), connection, BIND_AUTO_CREATE);
    }

    public void onClick(View view) throws  RemoteException{
        switch (view.getId()) {
            case R.id.btn_add:
                Log.d("SecondActivity", "add");

                Stu stu = new Stu();
                stu.setSex("nv");
                stu.setId(Integer.parseInt(editId.getText().toString()));
                stu.setName(editName.getText().toString());
                stu.setAge(Integer.parseInt(editAge.getText().toString()));
//                id++;

                iStu.add(stu);

                break;
            case R.id.btn_delete:
                Log.d("SecondActivity", "delete");
                iStu.delete(Integer.parseInt(editId.getText().toString()));
                break;
            case R.id.btn_query:
                Log.d("SecondActivity", "query");
                List<Stu> stus = iStu.query();
                for(Stu stu1 : stus) {
                    Log.d("SecondActivity", stu1.toString());
                }
                break;
        }
    }
}
