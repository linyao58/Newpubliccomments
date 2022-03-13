package com.example.newpubliccomments;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }
}
