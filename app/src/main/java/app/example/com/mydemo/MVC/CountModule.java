package app.example.com.mydemo.mvc;

/**
 * Created by Administrator on 2016/7/25.
 */

public class CountModule implements CountInfer {

    private Num num;
    private UpdateListener updateListener;

    public CountModule(UpdateListener updateListener) {
        num = new Num(0);
        this.updateListener = updateListener;
    }

    @Override
    public void add(int interval) {
        num.setCount(num.getCount() + interval);
        updateListener.update(num.getCount());
    }

    @Override
    public void sub(int interval) {
        num.setCount(num.getCount() - interval);
        updateListener.update(num.getCount());
    }
}
