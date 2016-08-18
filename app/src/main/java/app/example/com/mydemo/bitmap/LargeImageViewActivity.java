package app.example.com.mydemo.bitmap;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;

public class LargeImageViewActivity extends BaseActivity {

    private ScaleImageView mImageView;
    private LargeImageView myLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);
        mImageView = (ScaleImageView) findViewById(R.id.id_largetImageview);
        myLargeImageView = (LargeImageView) findViewById(R.id.id_my_largeimage);


//        try {
//            InputStream inputStream = getAssets().open("tangyan.jpg");
//            BitmapFactory.Options temOptions = new BitmapFactory.Options();
//            temOptions.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(inputStream, null, temOptions);
//            int width = temOptions.outWidth;
//            int height = temOptions.outHeight;
//
//            Log.d("LargeImageViewActivity", "width:" + width);
//            Log.d("LargeImageViewActivity", "height:" + height);
//
//            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
//            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options);
//            mImageView.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            mImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream2 = getAssets().open("tangyan.jpg");
            myLargeImageView.setInputStream(inputStream2);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
