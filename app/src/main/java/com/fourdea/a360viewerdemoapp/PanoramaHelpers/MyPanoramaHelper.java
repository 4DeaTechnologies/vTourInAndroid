package com.fourdea.a360viewerdemoapp.PanoramaHelpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.viewerlibrary.Listeners.ViewerListeners.PanoramaCallBackListener;
import com.fourdea.viewerlibrary.Presenters.ViewerPresenters.PanoramaHelper;
import com.google.android.gms.analytics.HitBuilders;

import java.util.Date;

/**
 * Created by dhrumil on 8/4/2016.
 */
public class MyPanoramaHelper extends PanoramaHelper implements PanoramaCallBackListener {

    private final String TAG = "MyPanoramaHelper";
    Context context;

    private String shortUrl;
    VtourCallBackListener callBackListener;

    int resId;

    int previousScene = 0;

    public MyPanoramaHelper(Context context) {
        super(context);
        this.context = context;
        super.setCallBackListener(this);
    }

    public MyPanoramaHelper(Context context, int containerResId, String shortUrl, VtourCallBackListener listener){
        super(context);
        this.context = context;
        this.resId = containerResId;
        this.shortUrl = shortUrl;
        this.callBackListener = listener;
        super.setCallBackListener(listener);
    }

    @Override
    public void initialize() {
        super.initialize();
        if(!Constants.AUTOPLAY){
            stopAutoPlay();
        }
    }

//    @Override
//    public GLSurfaceView getGlSurfaceView() {
//        GLSurfaceView glSurfaceView = (GLSurfaceView) ((Activity)context).findViewById(R.id.activity_main_gl_view);
//        return glSurfaceView;
//    }


    @Override
    public long getAutoplayDuration() {
        return 0;
    }

    @Override
    public String getTourDataPath() {
        return shortUrl;
    }

    @Override
    public ViewGroup getContainer() {
        return (ViewGroup) ((Activity)context).findViewById(resId);
    }

    @Override
    public String getImageBaseUrl() {
        return Constants.HOST_ADDRESS_IMAGE;
    }

    @Override
    public String getJsonBaseUrl() {
        return Constants.HOST_ADDRESS_JSON;
    }

    Date startTime = new Date();
    @Override
    public void changeScene(int sceneNum) {
        super.changeScene(sceneNum);
        Date endTime = new Date();
        long timeSpent = endTime.getTime() - startTime.getTime();
        if(callBackListener != null && callBackListener.getTracker() != null)
            callBackListener.getTracker().send(new HitBuilders.TimingBuilder()
                    .setCategory("Time spent - tour "+getPureShortUrl())
                    .setVariable("Spent time in scene number: "+previousScene)
                    .setLabel("time spent in scene number "+previousScene)
                    .setValue(timeSpent)
                    .build());
        previousScene = sceneNum;
        startTime = new Date();
    }

    @Override
    public void onTourDataLoaded() {
        Log.i(TAG, "tourDataLoaded()");
    }

    @Override
    public void onTap() {
        Log.i(TAG, "onTap()");
    }

    @Override
    public void onTouchDown() {
        Log.i(TAG, "onTouchDown()");
    }

    @Override
    public void onTouchUp() {
        Log.i(TAG, "onTouchUp()");
    }

    @Override
    public void onLowQualityPanoLoaded(int sceneNum) {
        Log.i(TAG, "onLowQualityLoaded");
    }

    @Override
    public void onHighQualityPanoLoaded(int sceneNum) {
        Log.i(TAG, "onHighQualityLoaded");
    }

    @Override
    public void onUpdateProgress(float progress) {
        Log.i(TAG, "updateProgress() "+progress);
    }

    @Override
    public void onFailedToLoadTourData() {
        Log.i(TAG, "failedToLoadTourData() ");
    }

    @Override
    public void onFailedToLoadImages() {
        Log.i(TAG, "failedToLoadImages() ");
    }

    @Override
    public void onArrowClicked() {
        Log.i(TAG, "onArrowClicked() ");
        Date endTime = new Date();
        long timeSpent = endTime.getTime() - startTime.getTime();
        if(callBackListener != null && callBackListener.getTracker() != null)
        callBackListener.getTracker().send(new HitBuilders.TimingBuilder()
                .setCategory("Time spent in scene")
                .setVariable("Tour "+getPureShortUrl())
                .setLabel("Scene: "+getSceneName(previousScene))
                .setValue(timeSpent)
                .build());
        previousScene = getCurrentSceneNum();
        startTime = new Date();
    }

    @Override
    public void autoPlayCompleted() {
        Log.i(TAG, "autoplay completed");
    }
}
