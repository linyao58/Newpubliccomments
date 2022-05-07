package com.example.newpubliccomments;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.umeng.commonsdk.UMConfigure;

import io.rong.imkit.RongIM;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);

        RongIM.init(this.getApplicationContext(), "82hegw5u8ewyx");

        UMConfigure.init(this, "62748aaed024421570e0338b", "美食点评", UMConfigure.DEVICE_TYPE_PHONE, "");

    }
}
