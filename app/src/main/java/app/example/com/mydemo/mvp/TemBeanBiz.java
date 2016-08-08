package app.example.com.mydemo.mvp;

/**
 * Created by Administrator on 2016/7/26.
 */

public interface TemBeanBiz {
    void add(int interval);
    void sub(int interval);
    void autoIncrease(int interval, NumListener listener);
}
