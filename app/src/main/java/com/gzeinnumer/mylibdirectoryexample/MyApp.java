package com.gzeinnumer.mylibdirectoryexample;

import android.app.Application;

import com.gzeinnumer.gzndirectory.helper.FGDir;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String externalFolderName = getApplicationContext().getString(R.string.app_name); //MyLibsTesting
        FGDir.initExternalDirectoryName(externalFolderName);
    }
}
