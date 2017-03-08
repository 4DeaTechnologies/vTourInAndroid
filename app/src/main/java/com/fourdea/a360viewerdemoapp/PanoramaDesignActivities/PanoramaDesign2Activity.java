package com.fourdea.a360viewerdemoapp.PanoramaDesignActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.PanoramaHelpers.MyPanoramaHelper;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.VtourCallBackListener;
import com.fourdea.a360viewerdemoapp.R;

import org.w3c.dom.Text;

public class PanoramaDesign2Activity extends AppCompatActivity implements VtourCallBackListener, View.OnClickListener {

    MyPanoramaHelper myPanoramaHelper;

    boolean stateShow = true, isTourDataLoaded = false;

    TextView title, loadingText;
    Button bookButton;
    ImageButton previousButton, nextButton;

    String shortUrl, titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_design2);

        initializeViews();

        shortUrl = getIntent().getStringExtra("ShortURL");
        titleText = getIntent().getStringExtra("title");

        myPanoramaHelper = new MyPanoramaHelper(this, R.id.activity_panorama_design2_gl_view, this);
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
                }
                else{
                    myPanoramaHelper.changeScene(myPanoramaHelper.getCurrentSceneNum() + 1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(v == previousButton){
            try{
                if(myPanoramaHelper.getCurrentSceneNum() == (0)){
                    myPanoramaHelper.changeScene(myPanoramaHelper.getTotalNumOfScenes() - 1);
                }
                else{
                    myPanoramaHelper.changeScene(myPanoramaHelper.getCurrentSceneNum() - 1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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
