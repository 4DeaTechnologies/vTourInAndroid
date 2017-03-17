package com.fourdea.a360viewerdemoapp.PanoramaHelpers;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.viewerlibrary.Presenters.ViewerPresenters.PanoramaHelper;

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
        return null;
    }

    @Override
    public String getJsonBaseUrl() {
        return null;
    }


    Date startTime = new Date();
    @Override
    public void changeScene(int sceneNum) {
        super.changeScene(sceneNum);
//        Date endTime = new Date();
//        long timeSpent = endTime.getTime() - startTime.getTime();
//        if(callBackListener != null && callBackListener.getTracker() != null)
//            callBackListener.getTracker().send(new HitBuilders.TimingBuilder()
//                    .setCategory("Time spent - tour "+getPureShortUrl())
//                    .setVariable("Spent time in scene number: "+previousScene)
//                    .setLabel("time spent in scene number "+previousScene)
//                    .setValue(timeSpent)
//                    .build());
//        previousScene = sceneNum;
//        startTime = new Date();
    }
}
