package app.example.com.mydemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.avos.avoscloud.AVOSCloud;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;

import org.xutils.x;

import app.example.com.mydemo.database.MySQLiteOpenHelper;
import app.example.com.mydemo.greendao.DaoMaster;
import app.example.com.mydemo.greendao.DaoSession;
import app.example.com.mydemo.greendao.SprayDetailsDao;

/**
 * Created by Administrator on 2016/1/8.
 */
public class MyApplication extends Application{

    private static  SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置
                trackingCrashLog(true).//是否收集crash
                trackingConsoleLog(true).//是否收集console log
                trackingUserSteps(true).//是否收集用户操作步骤
                versionName("1.0.1").//自定义版本名称
                versionCode(10).//自定义版本号
                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则
                build();
        Bugtags.start("96e6e8deacc61b49caf8500e5555af9b", this, Bugtags.BTGInvocationEventBubble, options);

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper (this, "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        AVOSCloud.initialize(this, "t6DfMHfszYL0cQzrTxyF0Oqa-gzGzoHsz", "0ithF6wFhqC7TVDRBiW4e0ew");
    }

    public static SprayDetailsDao getSprayDetailsDao() {
        return daoSession.getSprayDetailsDao();
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
