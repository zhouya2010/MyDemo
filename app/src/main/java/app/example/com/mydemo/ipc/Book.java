package app.example.com.mydemo.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/9.
 */

public class Book implements Parcelable {

    public int bookID;
    public String bookName;

    public Book(int bookID, String bookName) {
        this.bookID = bookID;
        this.bookName = bookName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookID);
        dest.writeString(this.bookName);
    }

    protected Book(Parcel in) {
        this.bookID = in.readInt();
        this.bookName = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
