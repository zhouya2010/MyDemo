package app.example.com.mydemo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2016/8/9.
 */

public class BookManagerService extends Service {

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public int addBook(Book book) throws RemoteException {
            mBookList.add(book);
            Log.e("BookManagerService", book.bookName);
            return mBookList.size();
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }
    };

    private void onNewBookArrived(Book newBook) throws RemoteException{
        mBookList.add(newBook);
        final int n = mListenerList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
            if (l != null) {
                l.onNewBookArrived(newBook);
            }
        }
        mListenerList.finishBroadcast();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "YU WEN"));
        mBookList.add(new Book(2, "SHU XUE"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class ServiceWork implements Runnable {
        @Override
        public void run() {

        }
    }
}
