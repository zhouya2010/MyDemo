package app.example.com.mydemo.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/11.
 */

public class MyTextView extends TextView {

    Scroller mScroller;

    int lastX;
    int lastY;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
//        smoothScroller(300,300);
    }

    private void smoothScroller(int destX, int destY) {

        int scrollX = getScrollX();
        int scrollY = getScrollY();

        Log.d("MyTextView", "scrollX==>" + scrollX + "    scrolly===> " + scrollY);

        int dx = destX - scrollX;
        int dy = destX - scrollY;

        mScroller.startScroll(scrollX, scrollY, dx, dy, 1000);

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
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - lastX;
                int dy = y - lastY;
//                layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);
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


}
