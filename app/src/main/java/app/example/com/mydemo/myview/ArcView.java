package app.example.com.mydemo.myview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import app.example.com.mydemo.R;


public class ArcView extends View {

    private int arcColor;
    private int textSize;
    private int textColor;

    //弧线底部颜色
    private int lineColor;

    //圆弧的画笔
    private Paint arcPaint;
    private RectF arcRect;

    private float arcNum = 0;

    private Path weavPath;

    private float weavX,weavY;

    //背景的坐标
    private int radiusBg, widthBg, heightBg;
    private int walkNum, rankNum;
    private int myWalkNum,myRankNum;

    //文字画笔
    private Paint textPaint;

    private AnimatorSet animSet;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcView, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i=0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ArcView_arcColor:
                    arcColor =  a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ArcView_textColor:
                    textColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.ArcView_textSize:
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ArcView_lineColor:
                    lineColor = a.getColor(attr, Color.BLACK);
                    break;
            }
        }
        a.recycle();
        init();
    }

    private void init() {
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        animSet = new AnimatorSet();
        weavPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

       if (widthMode == MeasureSpec.EXACTLY) {
           width = widthSize;
       }
        else {
           width = widthSize/2;
       }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的高度的3/4
            height = heightSize * 3 / 4;
        }

        widthBg = width;
        heightBg = height;
        setMeasuredDimension(width, height);

        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        arcPaint.setStrokeWidth(widthBg / 20);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setDither(true);
        //画笔的笔触为圆角
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setColor(lineColor);
        //圆弧范围
        arcRect = new RectF(widthBg * 1 / 4, widthBg * 1 / 4, widthBg * 3 / 4, widthBg * 3 / 4);
        //绘制背景大圆弧
        canvas.drawArc(arcRect, 120, 300, false, arcPaint);
        //内弧
        arcPaint.setColor(arcColor);
        canvas.drawArc(arcRect, 120, arcNum, false, arcPaint);

        //文字
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        canvas.drawText("第" + rankNum + "名", widthBg/2 - textSize, widthBg * 3 / 4 + 10,textPaint);

        textPaint.setColor(lineColor);
        textPaint.setTextSize(widthBg / 25);
        canvas.drawText("截止13:45已走", widthBg/2 - 120, widthBg * 5 / 12 - 10, textPaint);

        textPaint.setColor(textColor);
        textPaint.setTextSize(widthBg / 10);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(String.valueOf(walkNum), widthBg * 3 / 8, widthBg * 1 / 2 + 20, textPaint);

        //底部波纹
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        weavPath.reset();
        weavPath.moveTo(0, heightBg);
        weavPath.lineTo(0, heightBg * 10 / 12);
        weavPath.cubicTo(weavX, weavY, widthBg * 3 / 10, heightBg * 11 / 12, widthBg, heightBg * 10 / 12);
        weavPath.lineTo(widthBg, heightBg);
        weavPath.lineTo(0, heightBg);
        canvas.drawPath(weavPath, textPaint);
    }

    private void startAnim() {
        ValueAnimator  arcAnimator = ValueAnimator.ofFloat(0, (float)260);
        arcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arcNum = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator rankAnimator = ValueAnimator.ofInt(20, myRankNum);
        rankAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rankNum = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator walkAnimator = ValueAnimator.ofInt(0, myWalkNum);
        walkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                walkNum = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });

        //水波纹动画的实现
        ValueAnimator weavXAnimator = ValueAnimator.ofFloat(widthBg * 1 / 10, widthBg * 2/ 10);
        weavXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                weavX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator weavYAnimator = ValueAnimator.ofFloat(heightBg*10/12, heightBg*11/12);
        weavYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                weavY = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        animSet.setDuration(3000);
        animSet.playTogether(arcAnimator,rankAnimator, walkAnimator, weavXAnimator, weavYAnimator);
        animSet.start();
    }

    public void setWalkNum(int myWalkNum) {
        this.myWalkNum = myWalkNum;
    }

    public void setRankNum(int myRankNum) {
        this.myRankNum = myRankNum;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("ArcView", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_BUTTON_PRESS:
                Log.d("ArcView", "ACTION_BUTTON_PRESS");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("ArcView", "ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("ArcView", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ArcView", "ACTION_MOVE");
                break;
        }

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d("ArcView", "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d("ArcView", "onAttachedToWindow");
        super.onAttachedToWindow();
    }
}
