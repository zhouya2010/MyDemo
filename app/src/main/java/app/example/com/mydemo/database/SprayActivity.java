package app.example.com.mydemo.database;

import android.app.ListActivity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DateFormat;
import java.util.Date;

import app.example.com.mydemo.MyApplication;
import app.example.com.mydemo.R;
import app.example.com.mydemo.greendao.DaoMaster;
import app.example.com.mydemo.greendao.DaoSession;
import app.example.com.mydemo.greendao.SprayDetails;
import app.example.com.mydemo.greendao.SprayDetailsDao;

/**
 * Created by Administrator on 2016/3/14.
 */
@ContentView(R.layout.activity_spray)
public class SprayActivity  extends ListActivity {

    public static final String SCHEME = "content://";

    public static final String AUTHORITY = "com.nxecoii.test";
    public static final Uri URI_CONTENT_TEST = Uri.parse(SCHEME + AUTHORITY);

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SprayDetailsDao sprayDetailsDao;
    private Cursor cursor;
    public static final String TAG = "SprayActivity";
    private Observer observer;

    @ViewInject(R.id.edit_zone)
    private EditText editText;

    @ViewInject(R.id.btn_add)
    private Button add;

    @ViewInject(R.id.btn_search)
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        db = MyApplication.getDb();
        sprayDetailsDao = MyApplication.getSprayDetailsDao();

        String textColumn = SprayDetailsDao.Properties.Zone.columnName;

        cursor = db.query(sprayDetailsDao.getTablename(), sprayDetailsDao.getAllColumns(), null, null, null, null, null);

        String[] from = {textColumn, SprayDetailsDao.Properties.Id.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);

        observer = new Observer(new Handler());
        getContentResolver().registerContentObserver(
                URI_CONTENT_TEST, true, observer);
    }

    @Event(value = {R.id.btn_add,R.id.btn_delete,R.id.btn_search} )
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add_spray();
                break;
            case R.id.btn_delete:
                delete_spray();
                break;
        }
    }


    void delete_spray() {
        String zoneText = editText.getText().toString();
        SprayDetails note = new SprayDetails(null, Integer.parseInt(zoneText), 100, 50, 60, 1, 2, 1234L, 4567L,1234L, null,  null);
        sprayDetailsDao.deleteByKey(Long.parseLong(zoneText));
        getContentResolver().notifyChange(URI_CONTENT_TEST,null);
    }

    void  add_spray() {
        String zoneText = editText.getText().toString();
        editText.setText("");
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        // 插入操作，简单到只要你创建一个 Java 对象
        SprayDetails note = new SprayDetails(null, Integer.parseInt(zoneText), 100, 50, 60, 1, 2, 1234L, 4567L,1234L, true,  new Date());
        sprayDetailsDao.insertOrReplace(note);
        Log.d(TAG, "Inserted new note, ID: " + note.getId());
//        cursor.requery();
        getContentResolver().notifyChange(URI_CONTENT_TEST,null);
    }


    /**
     * ListView 的监听事件，用于删除一个 Item
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 删除操作，你可以通过「id」也可以一次性删除所有
        Log.d(TAG, "id:" + id);
        sprayDetailsDao.deleteByKey(id);
//        getNoteDao().deleteAll();
        Log.d(TAG, "Deleted note, ID: " + id);
//        cursor.requery();
    }


    private class Observer extends ContentObserver {
        public Observer(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(TAG, "onChange");
            cursor.requery();
        }
    }

}
