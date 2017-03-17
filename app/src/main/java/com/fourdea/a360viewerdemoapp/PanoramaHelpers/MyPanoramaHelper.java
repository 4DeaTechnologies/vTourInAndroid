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
public class MyPanoramaHelper extends PanoramaHelper {

    private final String TAG = "MyPanoramaHelper";
    Context context;

    private String shortUrl;
    VtourCallBackListener callBackListener;

    int resId;

    int previousScene = 0;

    public MyPanoramaHelper(Context context) {
        super(context);
        this.context = context;
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

    @Override
    public void changeScene(int sceneNum) {
        super.changeScene(sceneNum);
    }
}
