package app.example.com.mydemo.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

public class BitmapActivity extends BaseActivity {

    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvitity_bitmap);

        imageView1 = (ImageView) findViewById(R.id.bitmap_img1);
        imageView2 = (ImageView) findViewById(R.id.bitmap_img2);

        Bitmap bitmap = decodeSampleBitmapFromResource(getResources(), R.drawable.test, 100, 100);

        imageView1.setImageBitmap(bitmap);

        Bitmap bitmap2 = decodeSampleBitmapFromResource(getResources(), R.drawable.test, 300, 200);
        imageView2.setImageBitmap(bitmap2);
    }


    public static Bitmap decodeSampleBitmapFromResource(Resources res , int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
