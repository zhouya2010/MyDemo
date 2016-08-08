package app.example.com.mydemo.myview;

import java.util.Iterator;

/**
 * Created by Administrator on 2016/7/15.
 */

public abstract class Abtest {

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void add() {
        throw new UnsupportedOperationException();
    }


    public abstract Iterator createIterator();
}
