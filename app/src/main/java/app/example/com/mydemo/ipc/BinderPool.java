package app.example.com.mydemo.ipc;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by Administrator on 2016/8/9.
 */

public class BinderPool extends IBinderPool.Stub {

    static final int BINDER_SECURITY_CENTER = 1;
    static final int BINDER_COMPUTER = 2;

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl();
                break;
            case BINDER_COMPUTER:
                binder = new ComputeImpl();
                break;
            default:break;
        }

        return binder;
    }
}
