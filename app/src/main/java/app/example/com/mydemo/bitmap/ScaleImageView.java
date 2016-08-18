package app.example.com.mydemo.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
 * Created by Administrator on 2016/8/17.
 */

public class ScaleImageView extends View implements GestureDetector.OnGestureListener{

    /**按下时两指之间的距离**/
    private float startDistance;
    /** 两个手指的中间点 */
    private PointF midPoint;

    /** 用于记录图片要进行拖拉时候的坐标位置 */
    private Matrix currentMatrix = new Matrix();
    private Matrix matrix = new Matrix();
    private int mImageWidth, mImageHeight;
    private BitmapRegionDecoder mDecoder;
    private int mode = 0;

    private Rect rect;
    private GestureDetector gestureDetector;

    private BitmapFactory.Options options;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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

        matrix.setScale(1.0f, 1.0f);// 缩小为原来的一半
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mDecoder.decodeRegion(rect, options);
//        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(bitmap,matrix,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if(mImageWidth > width) {
            rect.left = mImageWidth / 2 - width / 2;
            rect.right = rect.left + width;
        }
        else {
            rect.left = 0;
            rect.right = rect.left + width;
        }

        if (mImageHeight > height) {
            rect.top = mImageHeight / 2 - height / 2;
            rect.bottom = rect.top + height;
        }
       else {
            rect.top = 0;
            rect.bottom = rect.top + height;
        }
    }

    private float[] values = new float[9];

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.e("LargeImageView", "ACTION_POINTER_DOWN");
                startDistance = distance(event);
                midPoint = mid(event);
                currentMatrix.set(matrix);
                mode = 1;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.e("LargeImageView", "ACTION_POINTER_UP");
                mode = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == 1) {
                    float endDis = distance(event);// 结束距离
                    if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        float scale = endDis / startDistance;// 得到缩放倍数
                        Log.d("ScaleImageView", "scale:" + scale);
                        matrix.set(currentMatrix);
                        matrix.postScale(scale, scale,midPoint.x,midPoint.y);
                        matrix.getValues(values);

                        for (int i = 0; i < values.length; i++) {
                            Log.d("ScaleImageView", "values[i]:" + values[i]);
                        }
                        invalidate();
                    }
                }

                break;
        }

        return true;
    }

    private float distance(MotionEvent event) {
        float x = event.getX(0)-event.getX(1);
        float y = event.getY(0)-event.getY(1);
        return (float)Math.sqrt(x*x+y*y);
    }

    /** 计算两个手指间的中间点 */
    private PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
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
}
