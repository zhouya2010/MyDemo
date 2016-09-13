package app.example.com.mydemo.myview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/9/13.
 */

public class LoadingView extends View {

    //线条最短长度
    private final int MIN_LINE_LENGTH = dp2px(getContext(), 40);
    private final float DefaultCanvasDegree = 60;
    private final int MAX_DURATION = 5000;
    private final int MIN_DURATION = 3000;
    /*
    *线条长度
     */
    private int lineLength;
    //动态线条长度
    private int dynamicLineLength;
    //圆半径
    private int circleRadius;

    private float canvasDegrees = DefaultCanvasDegree;

    private Paint paint;
    private int[] colors = new int[]{0xB07ECBDA, 0xB0E6A92C, 0xB0D6014D, 0xB05ABA94};
    private int width, height;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colors[0]);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initData() {
        lineLength = (int)(width *0.618f);
        circleRadius = width / 16;
        paint.setStrokeWidth(circleRadius * 2);
        dynamicLineLength = lineLength;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height =h;
        initData();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rotateAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i=0 ; i<4; i++) {
            paint.setColor(colors[i]);
            drawLine(canvas, width/2 - lineLength /4f, width/2 - lineLength /2 , width/2 - lineLength /4f, width/2 + dynamicLineLength /2, paint, canvasDegrees + 90 * i);
        }

    }


    void drawLine(Canvas canvas, float startX, float startY, float stopX, float stopY, @NonNull Paint paint, float degrees) {
        canvas.rotate(degrees, width/2, height/2);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.rotate(-degrees, width/2, height/2);
    }

    void rotateAnim() {
        ValueAnimator degreesAnimator = ValueAnimator.ofFloat(DefaultCanvasDegree, DefaultCanvasDegree + 360);
        degreesAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                canvasDegrees = (float)animation.getAnimatedValue();
            }
        });

        ValueAnimator lineAnimator = ValueAnimator.ofInt(lineLength, -lineLength);
        lineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dynamicLineLength = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(MAX_DURATION);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.playTogether(degreesAnimator, lineAnimator);
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rotateAnim();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationSet.start();
//        degreesAnimator.setDuration(MAX_DURATION);
//        degreesAnimator.setInterpolator(new LinearInterpolator());
//        degreesAnimator.start();
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
