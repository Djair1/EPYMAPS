package com.dev.epymaps;

import android.app.Application;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Djair on 26/09/2017.
 */

public class EpymapsApp extends Application {


    @Override
    public void onCreate() {
            super.onCreate();
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
        }



}







