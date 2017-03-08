package com.fourdea.a360viewerdemoapp.HotelList;

import android.content.Context;

import com.fourdea.a360viewerdemoapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by dhyeyshah on 08/03/17.
 */

public class HotelListPresenter implements HotelListAdapter.HotelListAdapterListener {


    private HotelListViewListener hotelListView;

    private HotelListAdapter adapter;

    public HotelListPresenter(HotelListViewListener hotelListView){
        this.hotelListView = hotelListView;
    }

    public void initialize(){
        try {
            String hotelListString = hotelListView.getRawResString(R.raw.hotels_list);

            JSONArray hotelList = new JSONArray(hotelListString);

            ArrayList<HotelItemInfo> mainList = new ArrayList<>();

            int length = hotelList.length();

            for(int i = 0; i < length; i++){
                JSONObject hotelObj = hotelList.getJSONObject(i);
                HotelItemInfo hotelItem = new HotelItemInfo();
                hotelItem.id = i;
                hotelItem.name = hotelObj.getString("title");
                hotelItem.shortUrl = hotelObj.getString("ShortURL");

                mainList.add(hotelItem);
            }

            adapter = new HotelListAdapter(this, mainList);
            hotelListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Context getContext() {
        return hotelListView.getContext();
    }

    @Override
    public void onHotelItemClicked(HotelItemInfo hotelItemInfo) {

    }
}
