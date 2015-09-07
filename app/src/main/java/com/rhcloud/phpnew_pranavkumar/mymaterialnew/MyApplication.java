package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Pranav on 9/4/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "0QMQDbjtSh1vm0K0HtIzs9InxAEPr5HYP1WQZmbl", "lu8CKLpUF3DSlJtwgZu18RGfHOsO7cLpfzfRhCbW");

    }
}
