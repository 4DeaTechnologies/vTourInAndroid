package com.fourdea.a360viewerdemoapp.HotelList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fourdea.a360viewerdemoapp.MyApplication;
import com.fourdea.a360viewerdemoapp.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;

public class HotelsListActivity extends AppCompatActivity implements HotelListViewListener {

    RecyclerView recyclerView;

    HotelListPresenter presenter;

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_list);

        initializeViews();

        // Obtain the shared Tracker instance.
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("HotelListActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Activity create")
                .setAction("onCreate()")
                .setLabel("Time")
                .setValue(new Date().getTime())
                .build());

        presenter = new HotelListPresenter(this);
        presenter.initialize();
    }

    private void initializeViews() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_hotels_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public String getRawResString(@RawRes int resId) throws IOException {
        InputStream is = getResources().openRawResource(resId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setAdapter(HotelListAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Intent getIntent(Class cls) {
        return new Intent(this, cls);
    }
}
