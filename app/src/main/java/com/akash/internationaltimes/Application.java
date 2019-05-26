package com.akash.internationaltimes;

import com.akash.internationaltimes.utils.SharedPreferenceUtils;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceUtils.init(this);
    }
}
