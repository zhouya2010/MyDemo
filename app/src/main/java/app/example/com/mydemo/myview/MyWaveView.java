package app.example.com.mydemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.example.com.mydemo.R;

public class MyWaveView extends View {

    private Paint mPaint;

    private int mSpeed;
    private int mPaintColor;
    private float mInitialRadius;
    private float mMaxRadius;
    private int mDuration;
    private Interpolator mInterpolator = new LinearInterpolator();
    private List<Circle> mCircleList = new ArrayList<Circle>();
    private boolean isRunning = false;

    public MyWaveView(Context context) {
        this(context, null);
    }

    public MyWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyWaveView, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MyWaveView_inCircleColor:
                    mPaintColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyWaveView_initialRadius:
                    mInitialRadius = a.getFloat(attr, 3.0f);
                    break;
                case R.styleable.MyWaveView_maxRadius:
                    mMaxRadius = a.getFloat(attr,getWidth()/2);
                    break;
                case R.styleable.MyWaveView_waveSpeed:
                    mSpeed = a.getInteger(attr, 1000);
                    break;
                case R.styleable.MyWaveView_waveDuration:
                    mDuration = a.getInteger(attr, 1000);
                    break;
            }
        }

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                creatCircle();
                postDelayed(mCreateCircle, mSpeed);
            }
        }
    };

    private void creatCircle() {
        Circle circle = new Circle();
        mCircleList.add(circle);
        postInvalidate();
//        invalidate();
    }

    public void run() {
        isRunning = true;
        mCreateCircle.run();
    }

    public void stop(){
        isRunning = false;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        if (mInterpolator == null) {
            mInterpolator = new LinearInterpolator();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator<Circle> iterator = mCircleList.iterator();
        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            if (System.currentTimeMillis() - circle.getmCreateTime() < mDuration) {
                mPaint.setAlpha(circle.getAlpha());
                canvas.drawCircle(getWidth()/2, getHeight()/2, circle.getCurrentRadius(), mPaint);
            }
            else {
                iterator.remove();
            }
        }
        if (mCircleList.size() > 0) {
            postInvalidateDelayed(10);
        }
//        if (System.currentTimeMillis() - circleTest.getmCreateTime() < mDuration) {
//            mPaint.setAlpha(circleTest.getAlpha());
//            canvas.drawCircle(getWidth()/2, getHeight()/2, circleTest.getCurrentRadius(), mPaint);
//        }
//        else {
//            circleTest.update();
//        }
    }

    public class Circle {
        private long mCreateTime;

        public Circle() {
            this.mCreateTime = System.currentTimeMillis();
        }

        public int getAlpha() {
            float percent = (System.currentTimeMillis() - this.mCreateTime) * 1.0f /mDuration;
            return (int) ((1.0f - mInterpolator.getInterpolation(percent)) * 255);
        }

        public float getCurrentRadius() {
            float percent = (System.currentTimeMillis() - mCreateTime) * 1.0f / mDuration;
            return mInitialRadius + mInterpolator.getInterpolation(percent) * (getWidth()/2 - mInitialRadius);
        }

        public long getmCreateTime() {
            return mCreateTime;
        }

        public void update() {
            this.mCreateTime = System.currentTimeMillis();
        }
    }
}
