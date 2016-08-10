// IOnNewBookArrivedListener.aidl
package app.example.com.mydemo.ipc;
import app.example.com.mydemo.ipc.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}
