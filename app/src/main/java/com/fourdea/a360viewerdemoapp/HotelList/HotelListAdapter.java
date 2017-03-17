package com.fourdea.a360viewerdemoapp.HotelList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourdea.a360viewerdemoapp.Constants;
import com.fourdea.a360viewerdemoapp.MyImageDownloader;
import com.fourdea.a360viewerdemoapp.R;
import com.fourdea.a360viewerdemoapp.URLUtil;

import java.util.ArrayList;

/**
 * Created by dhyeyshah on 08/03/17.
 */

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.Holder> {


    private HotelListAdapterListener listener;
    private ArrayList<HotelItemInfo> mainList;

    public HotelListAdapter(HotelListAdapterListener listener, ArrayList<HotelItemInfo> mainList){
        setHasStableIds(true);
        this.listener = listener;
        this.mainList = mainList;
    }

    @Override
    public long getItemId(int position) {
        return mainList.get(position).id;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_hotel_list_item, null);

        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        HotelItemInfo hotel = mainList.get(position);

        holder.title.setText(hotel.name);
        if(!hotel.thumbnailSet){
            new ImageSetterTask(hotel.shortUrl, holder.thumb).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            hotel.thumbnailSet = true;
        }
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumb;
        TextView title;

        public Holder(View itemView) {
            super(itemView);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//            itemView.setLayoutParams(layoutParams);
            itemView.setOnClickListener(this);

            thumb = (ImageView) itemView.findViewById(R.id.adapter_hotel_list_item_thumbnail);
            title = (TextView) itemView.findViewById(R.id.adapter_hotel_list_item_title);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(v == itemView){
                listener.onHotelItemClicked(mainList.get(position));
            }
        }
    }


    class ImageSetterTask extends AsyncTask<Void, Void, Bitmap>{

        String shortUrl;
        ImageView imageView;
        public ImageSetterTask(String shortUrl, ImageView imageView){
            this.shortUrl = shortUrl;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                String url = URLUtil.getThumbnailUrl(shortUrl);
                MyImageDownloader downloader = new MyImageDownloader(listener.getContext());
                bitmap = downloader.downloadImageWithCaching(url, shortUrl, 0, "hotel_main_thumb");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    public interface HotelListAdapterListener{
        Context getContext();

        void onHotelItemClicked(HotelItemInfo hotelItemInfo);
    }
}
