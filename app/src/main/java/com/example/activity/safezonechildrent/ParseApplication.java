package com.example.activity.safezonechildrent;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Tien on 04-Dec-15.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "TsiPMDfb6FogqPDYk2cpMZvcnIZTBwiImN4f10Yj", "sES4X1vFFSU6QohAmyAGnKI6P2AUlWnwUoQ2ihdG");
    }
}
