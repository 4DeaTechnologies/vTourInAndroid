package com.fourdea.a360viewerdemoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fourdea.a360viewerdemoapp.PanoramaHelpers.MyPanoramaHelper;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.VtourCallBackListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VtourCallBackListener, ThumbnailListener {

    MyPanoramaHelper myPanoramaHelper;

    public RecyclerView recyclerView;
    boolean stateShow = true, isTourDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPanoramaHelper = new MyPanoramaHelper(this, this);
        myPanoramaHelper.initialize();
    }

    public void start(View view){
        myPanoramaHelper.startAutoPlay();
    }

    public void stop(View view){
        myPanoramaHelper.stopAutoPlay();
    }

    public void cardBoard(View view){
        myPanoramaHelper.goToCardBoardMode();
    }

    public void gyroToggle(View view){
        if(myPanoramaHelper.isGyroOn()){
            myPanoramaHelper.turnGyroOff();
        }
        else{
            myPanoramaHelper.turnGyroOn();
        }
    }

    @Override
    public void onTourDataLoaded() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        isTourDataLoaded = true;
        int numOfScenes = 0;
        try {
            numOfScenes = myPanoramaHelper.getTotalNumOfScenes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ThumbnailBean> mainList = new ArrayList<>();

        for(int i=0; i<numOfScenes; i++){
            ThumbnailBean bean = new ThumbnailBean();
            bean.id = i;
            bean.sceneNum = i;
            try {
                bean.sceneName = myPanoramaHelper.getSceneName(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bean.selected = false;
            if(i == 0){
                bean.selected = true;
            }
            mainList.add(bean);
        }

        ThumbnailsAdapter adapter = new ThumbnailsAdapter(this, this, mainList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

    }

    @Override
    public void onHighQualityPanoLoaded(int i) {

    }

    @Override
    public void onUpdateProgress(float v) {

    }

    @Override
    public void onFailedToLoadTourData() {

    }

    @Override
    public void onFailedToLoadImages() {

    }

    @Override
    public void onArrowClicked() {

    }

    @Override
    public void autoPlayCompleted() {

    }

    @Override
    public void onThumbnailClicked(int sceneNum, String sceneName) throws Exception {
        myPanoramaHelper.changeScene(sceneNum);
    }

    @Override
    public String getThumbUrl(int sceneNum) throws Exception {
        return myPanoramaHelper.getThumbnailUrl(sceneNum);
    }

    @Override
    public long getAutoPlayDuration() {
        return 0;
    }

    @Override
    public String getTourDataPath() {
        return "HotelSwaroopvilas_Udaipur";
    }

    @Override
    public int getContainerResId() {
        return R.id.activity_main_gl_view;
    }

    @Override
    public String getImageBaseUrl() {
        return "http://4dea-development-commonpanos.s3-website.eu-central-1.amazonaws.com/vtour/";
    }

    @Override
    public String getJsonBaseUrl() {
        return "http://testingpurpose4dea.s3-website.eu-central-1.amazonaws.com/vtour/";
    }
}
