package app.example.com.mydemo.ipc;

import android.os.RemoteException;

/**
 * Created by Administrator on 2016/8/9.
 */

public class SecurityCenterImpl extends ISecurityCenter.Stub {
    @Override
    public String encrypt(String content) throws RemoteException {
        return "###encrypt";
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return "###decrypt";
    }
}
