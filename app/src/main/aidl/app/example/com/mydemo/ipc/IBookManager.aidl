// IBookManager.aidl
package app.example.com.mydemo.ipc;
import app.example.com.mydemo.ipc.Book;
import app.example.com.mydemo.ipc.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
        List<Book> getBookList();
        int addBook(in Book book);
        void registerListener(IOnNewBookArrivedListener listener);
        void unregisterListener(IOnNewBookArrivedListener listener);
}
