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

    VtourCallBackListener callBackListener;

    int resId;

    int previousScene = 0;

    public MyPanoramaHelper(Context context) {
        super(context);
        this.context = context;
        super.setCallBackListener(this);
    }

    public MyPanoramaHelper(Context context, int containerResId, VtourCallBackListener listener){
        super(context);
        this.context = context;
        this.resId = containerResId;
        this.callBackListener = listener;
        super.setCallBackListener(listener);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

//    @Override
//    public GLSurfaceView getGlSurfaceView() {
//        GLSurfaceView glSurfaceView = (GLSurfaceView) ((Activity)context).findViewById(R.id.activity_main_gl_view);
//        return glSurfaceView;
//    }


    @Override
    public long getAutoplayDuration() {
        if(callBackListener instanceof VtourCallBackListener && callBackListener != null)
            return callBackListener.getAutoPlayDuration();
        return 0;
    }

    @Override
    public String getTourDataPath() {
        if(callBackListener instanceof VtourCallBackListener && callBackListener != null)
            return callBackListener.getTourDataPath();
        return "HotelSwaroopvilas_Udaipur";
    }

    @Override
    public ViewGroup getContainer() {
        return (ViewGroup) ((Activity)context).findViewById(resId);
    }

    @Override
    public String getImageBaseUrl() {
        if(callBackListener instanceof VtourCallBackListener && callBackListener != null)
            return callBackListener.getImageBaseUrl();
        return Constants.HOST_ADDRESS_IMAGE;
    }

    @Override
    public String getJsonBaseUrl() {
        if(callBackListener instanceof VtourCallBackListener && callBackListener != null)
            return callBackListener.getJsonBaseUrl();
        return Constants.HOST_ADDRESS_JSON;
    }

    Date startTime = new Date();
    @Override
    public void changeScene(int sceneNum) {
        super.changeScene(sceneNum);
        Date endTime = new Date();
        long timeSpent = endTime.getTime() - startTime.getTime();
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
        Log.i("MyPanoHelper", "unit testing tourDataLoaded()");
    }

    @Override
    public void onTap() {
        Log.i("MyPanoHelper", "unit testing onTap()");
    }

    @Override
    public void onTouchDown() {
        Log.i("MyPanoHelper", "unit testing onTouchDown()");
    }

    @Override
    public void onTouchUp() {
        Log.i("MyPanoHelper", "unit testing onTouchUp()");
    }

    @Override
    public void onLowQualityPanoLoaded(int sceneNum) {
        Log.i("MyPanoHelper", "unit testing onLowQualityLoaded");
    }

    @Override
    public void onHighQualityPanoLoaded(int sceneNum) {
        Log.i("MyPanoHelper", "unit testing onHighQualityLoaded");
    }

    @Override
    public void onUpdateProgress(float progress) {
        Log.i("MyPanoHelper", "unit testing updateProgress() "+progress);
    }

    @Override
    public void onFailedToLoadTourData() {
        Log.i("MyPanoHelper", "unit testing failedToLoadTourData() ");
    }

    @Override
    public void onFailedToLoadImages() {
        Log.i("MyPanoHelper", "unit testing failedToLoadImages() ");
    }

    @Override
    public void onArrowClicked() {
        Log.i("MyPanoHelper", "unit testing onArrowClicked() ");
        Date endTime = new Date();
        long timeSpent = endTime.getTime() - startTime.getTime();
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
        Log.i("MyPanoHelper", "unit testing autoplay completed");
    }
}
