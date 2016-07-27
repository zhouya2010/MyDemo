package app.example.com.mydemo.mvp;

/**
 * Created by Administrator on 2016/7/26.
 */

public class TemBean implements TemBeanBiz {

    private int num;

    public TemBean(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void add(int interval) {
        num += interval;
    }

    @Override
    public void sub(int interval) {
        num -= interval;
    }

    @Override
    public void autoIncrease(final int interval, final NumListener listener) {
        new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    num += interval;
                    listener.increasing(num);

                    if (num > 100) {
                        listener.finish();
                        break;
                    }
                }
            }
        }.start();
    }

}
