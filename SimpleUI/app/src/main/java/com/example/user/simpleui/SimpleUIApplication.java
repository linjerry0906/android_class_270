package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 2016/7/19.
 */
public class SimpleUIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Order.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("EPJzhi3svf0y6cicPg7LoFbIuPtDExwiTQvEZjes")
                .server("https://parseapi.back4app.com/")
                .clientKey("Eg65GB2uNgIVB7WbATNwaJwcHGiEQZXeNt7sb508")
                .enableLocalDataStore()
                .build());
    }
}
