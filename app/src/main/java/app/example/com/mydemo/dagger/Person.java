package app.example.com.mydemo.dagger;

import android.content.Context;
import android.util.Log;

/**
 * Created by dell on 2016/11/1.
 */

public class Person {

    private Context mContext;
    private String sex;

    public Person() {
        sex = "无";
    }

    public Person(Context context){
        Log.i("dagger","person create!!!");
    }

    public Person(String sex) {
        this.sex = sex;
    }

    public void show() {
        Log.d("Person", "this is person show "  + sex);
    }
}
