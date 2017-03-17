package com.fourdea.a360viewerdemoapp.PanoramaDesignActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.a360viewerdemoapp.MyApplication;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.MyPanoramaHelper;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.VtourCallBackListener;
import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.a360viewerdemoapp.ThumbnailsAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class PanoramaDesign3Activity extends AppCompatActivity implements VtourCallBackListener {

    MyPanoramaHelper myPanoramaHelper;

    Tracker mTracker;

    TextView loadingText;
    boolean isTourDataLoaded = false;

    String shortUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_design3);

        // Obtain the shared Tracker instance.
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("PanoramaDesign3Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Activity create")
                .setAction("onCreate()")
                .build());

        loadingText = (TextView) findViewById(R.id.loading_text);

        shortUrl = getIntent().getStringExtra("ShortURL");

        myPanoramaHelper = new MyPanoramaHelper(this, R.id.activity_panorama_design3_gl_view,"HotelSwaroopvilas_Udaipur", this);
        myPanoramaHelper.initialize();
    }

    @Override
    public Tracker getTracker() {
        return mTracker;
    }

    @Override
    public void onTourDataLoaded() {

    }

    @Override
    public void onTap() {

    }

    @Override
    public void onTouchDown() {

    }

    @Override
    public void onTouchUp() {

    }

    @Override
    public void onLowQualityPanoLoaded(int i) {
        loadingText.setVisibility(View.GONE);
    }

    @Override
    public void onHighQualityPanoLoaded(int i) {
        loadingText.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateProgress(float v) {

    }

    @Override
    public void onFailedToLoadTourData() {
        loadingText.setText("Failed to load tour data!");
    }

    @Override
    public void onFailedToLoadImages() {
        loadingText.setText("Failed to load images!");
    }

    @Override
    public void onArrowClicked() {

    }

    @Override
    public void autoPlayCompleted() {

    }
}
