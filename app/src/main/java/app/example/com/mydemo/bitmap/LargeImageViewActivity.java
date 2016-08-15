package app.example.com.mydemo.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

public class LargeImageViewActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);
        mImageView = (ImageView) findViewById(R.id.id_largetImageview);

        try {
            InputStream inputStream = getAssets().open("tangyan.jpg");
            BitmapFactory.Options temOptions = new BitmapFactory.Options();
            temOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, temOptions);
            int width = temOptions.outWidth;
            int height = temOptions.outHeight;

            Log.d("LargeImageViewActivity", "width:" + width);
            Log.d("LargeImageViewActivity", "height:" + height);

            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options);
            mImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
