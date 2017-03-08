package com.fourdea.a360viewerdemoapp;

/**
 * Created by dhyeyshah on 08/03/17.
 */

public class URLUtil {

    public static String getThumbnailUrl(String shortUrl){
        return Constants.HOST_ADDRESS_IMAGE+"/"+shortUrl+"/images/MainThumbnail_small.jpg";
    }
}
