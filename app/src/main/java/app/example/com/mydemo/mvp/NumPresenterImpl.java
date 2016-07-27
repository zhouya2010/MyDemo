package app.example.com.mydemo.mvp;

import android.os.Handler;

/**
 * Created by Administrator on 2016/7/26.
 */

public class NumPresenterImpl {

    private NumView view;
    private TemBean bean;

    private Handler mHandler = new Handler();

    public NumPresenterImpl(NumView view) {
        this.view = view;
        bean = new TemBean(0);
    }

    void add(int interval) {
        bean.add(interval);
        view.update(bean.getNum());
    }

    void sub(int  interval) {
        bean.sub(interval);
        view.update(bean.getNum());
    }

    void autoRun(int num) {
        bean.autoIncrease(num, new NumListener() {
            @Override
            public void increasing(final int current) {

                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        view.update(current);
                    }
                });

            }

            @Override
            public void finish() {
            }
        });
    }
}
