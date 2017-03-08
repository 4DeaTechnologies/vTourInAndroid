package com.fourdea.a360viewerdemoapp.PanoramaHelpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.viewerlibrary.Listeners.ViewerListeners.PanoramaCallBackListener;
import com.fourdea.viewerlibrary.Presenters.ViewerPresenters.PanoramaHelper;

/**
 * Created by dhrumil on 8/4/2016.
 */
public class MyPanoramaHelper extends PanoramaHelper implements PanoramaCallBackListener {

    Context context;

    VtourCallBackListener callBackListener;

    public MyPanoramaHelper(Context context) {
        super(context);
        this.context = context;
        super.setCallBackListener(this);
    }

    public MyPanoramaHelper(Context context, VtourCallBackListener listener){
        super(context);
        this.context = context;
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
        if(callBackListener instanceof VtourCallBackListener && callBackListener != null)
            return (ViewGroup) ((Activity)context).findViewById(callBackListener.getContainerResId());
        return (ViewGroup) ((Activity)context).findViewById(R.id.activity_main_gl_view);
    }

    @Override
    public String getImageBaseUrl() {
        if(callBackListener instanceof VtourCallBackListener && callBackListener != null)
            return callBackListener.getImageBaseUrl();
        return "http://4dea-development-commonpanos.s3-website.eu-central-1.amazonaws.com/vtour/";
    }

    @Override
    public String getJsonBaseUrl() {
        if(callBackListener instanceof VtourCallBackListener && callBackListener != null)
            return callBackListener.getJsonBaseUrl();
        return "http://testingpurpose4dea.s3-website.eu-central-1.amazonaws.com/vtour/";
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
    }

    @Override
    public void autoPlayCompleted() {
        Log.i("MyPanoHelper", "unit testing autoplay completed");
    }
}
