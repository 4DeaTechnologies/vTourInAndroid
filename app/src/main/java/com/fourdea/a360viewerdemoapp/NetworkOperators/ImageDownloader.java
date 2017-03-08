package com.fourdea.a360viewerdemoapp.NetworkOperators;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dhyeyshah on 08/03/17.
 */

public class ImageDownloader extends AsyncTask<Void, Void, Void> {

    private String uri;
    private ImageDownloaderListener listener;
    private boolean success = true;
    Bitmap bitmap;

    public ImageDownloader(String uri, ImageDownloaderListener listener){
        this.uri = uri;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(uri);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            Log.d(TAG, "connection open.");

            /*get inputStream
            * store byte by byte into byteBuffer
            * and then make bitmap from byteBuffer
            * */
            InputStream is = con.getInputStream();

            bitmap = null;
            bitmap = BitmapFactory.decodeStream(is);

            is.close();
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            success = false;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(success)
            listener.onImageDownloaded(bitmap);
        else
            listener.onFailed();
    }

    public interface ImageDownloaderListener{
        void onImageDownloaded(Bitmap bitmap);

        void onFailed();
    }
}
