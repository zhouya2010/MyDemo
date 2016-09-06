package app.example.com.mydemo.CollapsingToolbar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import app.example.com.mydemo.BaseActivity;
import app.example.com.mydemo.R;
import app.example.com.mydemo.retrofit.ApiManage;
import app.example.com.mydemo.retrofit.ZhihuStory;
import app.example.com.mydemo.util.DensityUtil;
import app.example.com.mydemo.util.WebUtil;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ContentView(R.layout.activity_scrolling)
public class ScrollingActivity extends BaseActivity {

    @ViewInject(R.id.wv_content)
    WebView wvZhihu;

    @ViewInject(R.id.web_img_head)
    ImageView wvHeadImg;

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    int[] mDeviceInfo;
    int width;
    int heigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scrolling);
        toolbar.setTitle("知乎日报");
        setSupportActionBar(toolbar);

        mDeviceInfo = DensityUtil.getDeviceInfo(this);
        width = mDeviceInfo[0];
        heigh = width * 3 / 4;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        InitData();
        GetData();

    }

    private void GetData() {
        Subscription s = ApiManage.getInstence().getZhihuApiService().getZhihuStory("8765512")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuStory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZhihuStory zhihuStory) {
                        showZhihuStory(zhihuStory);
                    }
                });
    }


    void showZhihuStory(ZhihuStory zhihuStory) {
        Log.e("ScrollingActivity", "showZhihuStory");

        Picasso.with(this).load(zhihuStory.getImage()).centerCrop().fit().into(wvHeadImg);
        String data = WebUtil.buildHtmlWithCss(zhihuStory.getBody(), zhihuStory.getCss(), false);

        Log.e("ScrollingActivity", data);

        wvZhihu.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);

    }

    private void InitData() {
        WebSettings settings = wvZhihu.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvZhihu.setWebChromeClient(new WebChromeClient());
    }

}
