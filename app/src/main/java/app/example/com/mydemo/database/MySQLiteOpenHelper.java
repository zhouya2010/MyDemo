package app.example.com.mydemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import app.example.com.mydemo.greendao.DaoMaster;
import app.example.com.mydemo.greendao.SprayDetailsDao;

/**
 * Created by Administrator on 2016/3/23.
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.getInstance().migrate(db, SprayDetailsDao.class);
    }
}
