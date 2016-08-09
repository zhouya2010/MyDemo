package app.example.com.mydemo.ipc;

import android.os.RemoteException;

/**
 * Created by Administrator on 2016/8/9.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
