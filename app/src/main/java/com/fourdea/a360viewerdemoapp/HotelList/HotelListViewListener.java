package com.fourdea.a360viewerdemoapp.HotelList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.RawRes;

import com.fourdea.a360viewerdemoapp.HotelDetails.HotelDetailsActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by dhyeyshah on 08/03/17.
 */

public interface HotelListViewListener {
    String getRawResString(@RawRes int resId) throws IOException;

    Context getContext();

    void setAdapter(HotelListAdapter adapter);

    Intent getIntent(Class cls);

    void startActivity(Intent intent);
}
