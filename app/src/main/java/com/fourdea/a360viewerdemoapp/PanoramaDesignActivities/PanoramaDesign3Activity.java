package com.fourdea.a360viewerdemoapp.PanoramaDesignActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.a360viewerdemoapp.MyApplication;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.MyPanoramaHelper;
import com.fourdea.a360viewerdemoapp.PanoramaHelpers.VtourCallBackListener;
import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.a360viewerdemoapp.ThumbnailsAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class PanoramaDesign3Activity extends AppCompatActivity {

    MyPanoramaHelper myPanoramaHelper;

    String shortUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_design3);

        shortUrl = getIntent().getStringExtra("ShortURL");

        myPanoramaHelper = new MyPanoramaHelper(this, R.id.activity_panorama_design3_gl_view,shortUrl, null);
        myPanoramaHelper.initialize();
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
        if(id == R.id.menu_panorama_design3_gyro){
            gyroToggle();
        }
        else if(id == R.id.menu_panorama_design3_cardBoard){
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
}
