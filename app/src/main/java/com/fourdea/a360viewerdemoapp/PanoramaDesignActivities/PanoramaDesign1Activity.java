package com.fourdea.a360viewerdemoapp.PanoramaDesignActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.PanoramaHelpers.MyPanoramaHelper;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.VtourCallBackListener;
import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.a360viewerdemoapp.ThumbnailBean;
import com.fourdea.a360viewerdemoapp.ThumbnailListener;
import com.fourdea.a360viewerdemoapp.ThumbnailsAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PanoramaDesign1Activity extends AppCompatActivity implements VtourCallBackListener, ThumbnailListener {

    MyPanoramaHelper myPanoramaHelper;

    public RecyclerView recyclerView;
    TextView loadingText;
    boolean stateShow = true, isTourDataLoaded = false;
    ThumbnailsAdapter adapter;

    String shortUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_design1);

        loadingText = (TextView) findViewById(R.id.loading_text);

        shortUrl = getIntent().getStringExtra("ShortURL");

        myPanoramaHelper = new MyPanoramaHelper(this, R.id.activity_panorama_design1_gl_view, this);
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
    public long getAutoPlayDuration() {
        return 0;
    }

    @Override
    public String getTourDataPath() {
        return shortUrl;
    }

    @Override
    public String getImageBaseUrl() {
        return "http://4dea-development-commonpanos.s3-website.eu-central-1.amazonaws.com/vtour/";
    }

    @Override
    public String getJsonBaseUrl() {
        return "http://testingpurpose4dea.s3-website.eu-central-1.amazonaws.com/vtour/";
    }

    @Override
    public void onTourDataLoaded() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_panorama_design1_recycler_view);
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
        adapter = new ThumbnailsAdapter(this, this, mainList);
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
        loadingText.setVisibility(View.GONE);
    }

    @Override
    public void onHighQualityPanoLoaded(int i) {
        loadingText.setVisibility(View.GONE);
        adapter.setSelected(i);
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

    @Override
    public void onThumbnailClicked(int sceneNum, String sceneName) throws Exception {
        myPanoramaHelper.changeScene(sceneNum);
    }

    @Override
    public String getThumbUrl(int sceneNum) throws Exception {
        return myPanoramaHelper.getThumbnailUrl(sceneNum);
    }
}
