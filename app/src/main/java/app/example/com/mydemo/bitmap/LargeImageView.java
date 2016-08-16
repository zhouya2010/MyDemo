package app.example.com.mydemo.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/16.
 */

public class LargeImageView extends View implements GestureDetector.OnGestureListener {

    Rect rect;

    BitmapFactory.Options options;

    BitmapRegionDecoder mDecoder;

    GestureDetector gestureDetector;

    /**
     * 图片的宽度和高度
     */
    private int mImageWidth, mImageHeight;

    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        rect.left = mImageWidth / 2 - width / 2;
        rect.right = rect.left + width;
        rect.top = mImageHeight / 2 - height / 2;
        rect.bottom = rect.top + height;
//        checkRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mDecoder.decodeRegion(rect, options);
        canvas.drawBitmap(bitmap,0,0,null);
    }

    public void setInputStream(InputStream is) {
        try {
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, tmpOptions);

            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    void init() {
        rect = new Rect();
        gestureDetector = new GestureDetector(getContext(), this);

        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        int action = event.getAction();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.e("LargeImageView", "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.e("LargeImageView", "ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("LargeImageView", "ACTION_MOVE");
        }

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        rect.offset((int)distanceX, (int)distanceY);
        checkRect();
        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private void checkRect() {

        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }

        if (rect.right > mImageWidth) {
            rect.right = mImageHeight;
            rect.left = rect.right - getWidth();
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }

        if (rect.bottom > mImageHeight) {
            rect.bottom = mImageHeight;
            rect.top = rect.bottom - getHeight();
        }

    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0)-event.getX(1);
        float y = event.getY(0)-event.getY(1);
        return (float)Math.sqrt(x*x+y*y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
