package com.fourdea.a360viewerdemoapp.PanoramaDesignActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.a360viewerdemoapp.MyApplication;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.MyPanoramaHelper;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.VtourCallBackListener;
import com.fourdea.a360viewerdemoapp.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.w3c.dom.Text;

import java.util.Date;

public class PanoramaDesign2Activity extends AppCompatActivity implements VtourCallBackListener, View.OnClickListener {

    MyPanoramaHelper myPanoramaHelper;

    Tracker mTracker;

    boolean stateShow = true, isTourDataLoaded = false;

    TextView title, loadingText;
    Button bookButton;
    ImageButton previousButton, nextButton;

    String shortUrl, titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_design2);

        // Obtain the shared Tracker instance.
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("PanoramaDesign2Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Activity create")
                .setAction("onCreate()")
                .build());

        initializeViews();

        shortUrl = getIntent().getStringExtra("ShortURL");
        titleText = getIntent().getStringExtra("title");

        myPanoramaHelper = new MyPanoramaHelper(this, R.id.activity_panorama_design2_gl_view
                , "HotelSwaroopvilas_Udaipur", this);
        myPanoramaHelper.initialize();
    }

    public void initializeViews(){
        title = (TextView) findViewById(R.id.activity_panorama_design2_title);
        loadingText = (TextView) findViewById(R.id.loading_text);
        bookButton = (Button) findViewById(R.id.activity_panorama_design2_book_button);
        bookButton.setOnClickListener(this);
        previousButton = (ImageButton) findViewById(R.id.activity_panorama_design2_previous);
        previousButton.setOnClickListener(this);
        nextButton = (ImageButton) findViewById(R.id.activity_panorama_design2_next);
        nextButton.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v == bookButton){

        }
        else if(v == nextButton){
            try{
                if(myPanoramaHelper.getCurrentSceneNum() == (myPanoramaHelper.getTotalNumOfScenes()-1)){
                    myPanoramaHelper.changeScene(0);
                    trackSceneChange(0);
                }
                else{
                    myPanoramaHelper.changeScene(myPanoramaHelper.getCurrentSceneNum() + 1);
                    trackSceneChange(myPanoramaHelper.getCurrentSceneNum() + 1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(v == previousButton){
            try{
                if(myPanoramaHelper.getCurrentSceneNum() == (0)){
                    myPanoramaHelper.changeScene(myPanoramaHelper.getTotalNumOfScenes() - 1);
                    trackSceneChange(myPanoramaHelper.getTotalNumOfScenes() - 1);
                }
                else{
                    myPanoramaHelper.changeScene(myPanoramaHelper.getCurrentSceneNum() - 1);
                    trackSceneChange(myPanoramaHelper.getCurrentSceneNum() - 1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Constants.SHOW_CARDBOARD_AND_GYRO) {
            getMenuInflater().inflate(R.menu.menu_panorama_design2_activity, menu);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_panorama_design2_gyro){
            gyroToggle();
        }
        else if(id == R.id.menu_panorama_design2_cardBoard){
            cardBoard();
        }
        return super.onOptionsItemSelected(item);
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


    Date startTime = new Date();
    int previousScene = 0;
    @Override
    public void onArrowClicked() {
        trackSceneChange(myPanoramaHelper.getCurrentSceneNum());
    }

    @Override
    public void autoPlayCompleted() {

    }

    private void trackSceneChange(int sceneNum){
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
}
