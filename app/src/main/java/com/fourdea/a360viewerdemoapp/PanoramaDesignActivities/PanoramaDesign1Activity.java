package com.fourdea.a360viewerdemoapp.PanoramaDesignActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.a360viewerdemoapp.MyApplication;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.MyPanoramaHelper;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.VtourCallBackListener;
import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.a360viewerdemoapp.ThumbnailBean;
import com.fourdea.a360viewerdemoapp.ThumbnailListener;
import com.fourdea.a360viewerdemoapp.ThumbnailsAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class PanoramaDesign1Activity extends AppCompatActivity implements VtourCallBackListener, ThumbnailListener {

    MyPanoramaHelper myPanoramaHelper;

    Tracker mTracker;

    public RecyclerView recyclerView;
    TextView loadingText;
    boolean isTourDataLoaded = false;
    ThumbnailsAdapter adapter;

    String shortUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_design1);

        // Obtain the shared Tracker instance.
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("PanoramaDesign1Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Activity create")
                .setAction("onCreate()")
                .build());

        loadingText = (TextView) findViewById(R.id.loading_text);

        shortUrl = getIntent().getStringExtra("ShortURL");

        myPanoramaHelper = new MyPanoramaHelper(this, R.id.activity_panorama_design1_gl_view, shortUrl, this);
        myPanoramaHelper.initialize();
    }

    public void start(View view){
        myPanoramaHelper.startAutoPlay();
    }

    public void stop(View view){
        myPanoramaHelper.stopAutoPlay();
    }

    public void cardBoard(){
        myPanoramaHelper.goToCardBoardMode();
    }

    public void gyroToggle(){
        if(myPanoramaHelper.isGyroOn()){
            myPanoramaHelper.turnGyroOff();
        }
        else{
            myPanoramaHelper.turnGyroOn();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Constants.SHOW_CARDBOARD_AND_GYRO) {
            getMenuInflater().inflate(R.menu.menu_panorama_design1_activity, menu);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_panorama_design1_gyro){
            gyroToggle();
        }
        else if(id == R.id.menu_panorama_design1_cardBoard){
            cardBoard();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Tracker getTracker() {
        return mTracker;
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
        Date endTime = new Date();
        long timeSpent = endTime.getTime() - startTime.getTime();
        if(mTracker != null)
            mTracker.send(new HitBuilders.TimingBuilder()
                    .setCategory("Time spent - tour "+myPanoramaHelper.getPureShortUrl())
                    .setVariable("Spent time in scene number: "+previousScene)
                    .setLabel("time spent in scene number "+previousScene)
                    .setValue(timeSpent)
                    .build());
        previousScene = myPanoramaHelper.getCurrentSceneNum();
        startTime = new Date();
    }

    @Override
    public void autoPlayCompleted() {

    }

    Date startTime = new Date();
    int previousScene = 0;
    @Override
    public void onThumbnailClicked(int sceneNum, String sceneName) throws Exception {
        myPanoramaHelper.changeScene(sceneNum);
        Date endTime = new Date();
        long timeSpent = endTime.getTime() - startTime.getTime();
        if(mTracker != null)
            mTracker.send(new HitBuilders.TimingBuilder()
                    .setCategory("Time spent - tour "+myPanoramaHelper.getPureShortUrl())
                    .setVariable("Spent time in scene number: "+previousScene)
                    .setLabel("time spent in scene number "+previousScene)
                    .setValue(timeSpent)
                    .build());
        previousScene = sceneNum;
        startTime = new Date();
    }

    @Override
    public String getThumbUrl(int sceneNum) throws Exception {
        return myPanoramaHelper.getThumbnailUrl(sceneNum);
    }
}
