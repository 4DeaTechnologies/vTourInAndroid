package com.fourdea.a360viewerdemoapp.PanoramaHelpers;

import com.fourdea.viewerlibrary.Listeners.ViewerListeners.PanoramaCallBackListener;

/**
 * Created by dhyeyshah on 08/03/17.
 */

public interface VtourCallBackListener extends PanoramaCallBackListener {

    long getAutoPlayDuration();

    String getTourDataPath();

    int getContainerResId();

    String getImageBaseUrl();

    String getJsonBaseUrl();
}
