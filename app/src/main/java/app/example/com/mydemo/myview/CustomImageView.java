package app.example.com.mydemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import app.example.com.mydemo.R;

/**
 * Created by Administrator on 2016/6/15.
 */

public class CustomImageView extends View {


    private String mTitle;
    private Bitmap mImage;
    private int mTextColor;
    private int mTextSize;
    private int mImageScale;

    int mWidth = 0;
    int mHeight = 0;

    Rect rect;
    Paint mPaint;
    Rect mTextBound;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, 0, 0);

        mTitle = a.getString(R.styleable.CustomImageView_titleText);
        mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.CustomImageView_image, 0));
        mTextColor = a.getColor(R.styleable.CustomImageView_titleTextColor, Color.WHITE);
        mTextSize= a.getDimensionPixelSize(R.styleable.CustomImageView_titleTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16, getResources().getDisplayMetrics()));

        mImageScale = a.getInt(R.styleable.CustomImageView_imageScaleType, 0);

        a.recycle();

        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {



        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                mWidth = specSize;
                Log.e("widthMeasureSpec", "AT_EXACTLY  "+ mWidth);
                break;
            case MeasureSpec.AT_MOST:
                // 由图片决定的宽
                int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
                // 由字体决定的宽
                int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(desire, specSize);
                Log.e("widthMeasureSpec", "AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default: break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                mHeight = specSize;
                Log.e("heightMeasureSpec", "EXACTLY  "+ mHeight);
                break;
            case MeasureSpec.AT_MOST:
                int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
                mHeight = Math.min(desire, specSize);
                Log.e("heightMeasureSpec", "AT_MOST  "+ mHeight);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default: break;
        }

        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 边框
         */
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
        if (mTextBound.width() > mWidth)
        {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        } else
        {
            //正常情况，将字体居中
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        rect.bottom -= mTextBound.height();

        if (mImageScale == 0)
        {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
        else
        {
            //计算居中的矩形范围
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }
}
