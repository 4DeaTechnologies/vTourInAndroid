package com.fourdea.a360viewerdemoapp.HotelDetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.a360viewerdemoapp.MainActivity;
import com.fourdea.a360viewerdemoapp.NetworkOperators.ImageDownloader;
import com.fourdea.a360viewerdemoapp.PanoramaDesignActivities.PanoramaDesign1Activity;
import com.fourdea.a360viewerdemoapp.PanoramaDesignActivities.PanoramaDesign2Activity;
import com.fourdea.a360viewerdemoapp.PanoramaDesignActivities.PanoramaDesign3Activity;
import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.a360viewerdemoapp.URLUtil;
import com.fourdea.a360viewerdemoapp.ViewerType;

public class HotelDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title;
    ImageView thumb;

    String shortUrl, titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        initializeViews();

        Intent intent = getIntent();

        titleText = intent.getStringExtra("title");
        shortUrl = intent.getStringExtra("ShortURL");

        title.setText(titleText);

        String uri = URLUtil.getThumbnailUrl(shortUrl);
        ImageDownloader downloader = new ImageDownloader(uri, new ImageDownloader.ImageDownloaderListener() {
            @Override
            public void onImageDownloaded(Bitmap bitmap) {
                thumb.setImageBitmap(bitmap);
            }

            @Override
            public void onFailed() {

            }
        });
        downloader.execute();
    }

    private void initializeViews() {
        thumb = (ImageView) findViewById(R.id.activity_hotel_details_thumb);
        thumb.setOnClickListener(this);
        title = (TextView) findViewById(R.id.activity_hotel_details_title);
    }

    @Override
    public void onClick(View v) {
        if(v == thumb){
            if(Constants.viewerType == ViewerType.DESIGN1) {
                Intent intent = new Intent(this, PanoramaDesign1Activity.class);
                intent.putExtra("ShortURL", shortUrl);
                intent.putExtra("title", titleText);
                startActivity(intent);
            }
            else if(Constants.viewerType == ViewerType.DESIGN2){
                Intent intent = new Intent(this, PanoramaDesign2Activity.class);
                intent.putExtra("ShortURL", shortUrl);
                intent.putExtra("title", titleText);
                startActivity(intent);
            }
            else if(Constants.viewerType == ViewerType.DESIGN3){
                Intent intent = new Intent(this, PanoramaDesign3Activity.class);
                intent.putExtra("ShortURL", shortUrl);
                intent.putExtra("title", titleText);
                startActivity(intent);
            }
        }
    }
}
