package com.fourdea.a360viewerdemoapp.PanoramaHelpers;

import com.fourdea.viewerlibrary.Listeners.ViewerListeners.PanoramaCallBackListener;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by dhyeyshah on 08/03/17.
 */

public interface VtourCallBackListener extends PanoramaCallBackListener {

    Tracker getTracker();
}
