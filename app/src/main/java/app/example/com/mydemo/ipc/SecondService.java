package app.example.com.mydemo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SecondService extends Service {


    List<Stu> stus = new ArrayList<>();

    public SecondService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("SecondService", "onCreate");

        Stu stu = new Stu();
        stu.setName("zhouya");
        stu.setAge(26);
        stu.setId(1);
        stu.setSex("nan");

        synchronized (stus) {
            stus.add(stu);

        }
    }

    private final IStu.Stub mBinder = new IStu.Stub() {
        @Override
        public void add(Stu stu) throws RemoteException {
            if (!stus.contains(stu)) {
                stus.add(stu);
            }
        }

        @Override
        public void delete(int id) throws RemoteException {
            synchronized (stus) {
                if (stus.get(id) != null) {
                    stus.remove(id);
                }
            }
        }

        @Override
        public List<Stu> query() throws RemoteException {

            synchronized (stus) {
                return stus;
            }

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("SecondService", "Service is start....");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("SecondService", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
