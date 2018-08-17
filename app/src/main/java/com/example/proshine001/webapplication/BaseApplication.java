package com.example.proshine001.webapplication;

import android.app.Application;

import com.example.proshine001.webapplication.mqtt.MqttWrapService;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by proshine001 on 2018-08-17.
 */

public class BaseApplication extends Application {
    private static BaseApplication mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        initLOG();
        MqttWrapService.start(this);
    }
    private void initLOG() {
        Logger.clearLogAdapters();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("bunnytouch")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
