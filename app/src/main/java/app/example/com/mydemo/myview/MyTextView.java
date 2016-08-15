package app.example.com.mydemo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/11.
 */

public class MyTextView extends TextView {

    private Scroller mScroller;
    private VelocityTracker vTracker = null;
    private int lastX;
    private int lastY;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Interpolator interpolator = new DecelerateInterpolator();
        mScroller = new Scroller(context, interpolator);

//        smoothScroller(300,300);
    }

    private void smoothScroller(int destX, int destY) {

        int scrollX = getScrollX();
        int scrollY = getScrollY();

        int centerX =(getRight() - getLeft()) / 2;
        int  centerY =(getBottom() - getTop()) / 2;

        Log.d("MyTextView", "scrollX==>" + scrollX + "    scrolly===> " + scrollY);

        int dx = centerX - scrollX - destX;
        int dy = centerY - destY - scrollY;

        mScroller.startScroll(scrollX, scrollY, dx, dy, 500);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("MyTextView", "MyTextView | onTouchEvent --> " + TouchEventUtil.getTouchAction(event.getAction()));

        int x = (int)event.getX();
        int y = (int)event.getY();

        Log.d("MyTextView", "x:" + x + "   y:" + y);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;

                if(vTracker == null) {
                    vTracker = VelocityTracker.obtain();
                }
                else {
                    vTracker.clear();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - lastX;
                int dy = y - lastY;
//                layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(100);
                Log.d("MyTextView", "vTracker.getXVelocity():" + vTracker.getXVelocity());
                Log.d("MyTextView", "vTracker.getYVelocity():" + vTracker.getYVelocity());
                break;
            case MotionEvent.ACTION_UP:
                smoothScroller(x,y);
                break;

        }
//        return super.onTouchEvent(event);
        return true;
    }



    @Override
    public void computeScroll() {
//        Log.v("MyTextView", "MyTextView | onTouchEvent --> computeScroll");

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        if(vTracker != null) {
            vTracker.recycle();
        }
        super.onDetachedFromWindow();
    }
}
