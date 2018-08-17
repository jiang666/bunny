package com.example.proshine001.webapplication.mqtt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.code19.library.AppUtils;
import com.example.proshine001.webapplication.bean.ChargeRecordsInfo;
import com.example.proshine001.webapplication.even.MessageEvent;
import com.example.proshine001.webapplication.utils.ListSaveUtils;
import com.orhanobut.logger.Logger;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
/**
 * 开启推送服务 处理命令 业务   注意:并不是真正的mqtt服务
 */
public final class MqttWrapService extends Service implements IMqttMessageListener {


    private ArrayList<ChargeRecordsInfo> list;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        // 订阅Mqtt 状态
        MqttUtil.getInstance(this.getApplicationContext()).subscribe(this);
        // 触发Mqtt连接
        MqttUtil.getInstance(this.getApplicationContext()).connect();
        list = ListSaveUtils.getStorageEntities(this);
     
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Logger.d(topic+"   "+message.toString());
        /*Intent intent = new Intent();
        intent.putExtra("cardnum", message.toString());
        intent.setAction("net.bunnytouch.bunnyad.student");
        getApplicationContext().sendBroadcast(intent);*/
        EventBus.getDefault().post(new MessageEvent(message.toString()));

    }


  
    /**
     * 启动Mqtt 客户端服务
     *
     * @param context
     */
    public static void start(Context context) {
        if (!MqttUtil.getInstance(context).isConnect()) {
            if (AppUtils.isServiceRunning(context, MqttWrapService.class.getName())) {
                AppUtils.stopRunningService(context, MqttWrapService.class.getName());
            }
            Logger.d("服务重启！");
            context.startService(new Intent(context, MqttWrapService.class));
        }

    }

    /**
     * 重新启动Mqtt 客户端服务
     *
     * @param context
     */
    public static void reStart(final Context context) {
        MqttUtil.getInstance(context).disconnect();
        //UiUtils.showToastSafe(context, "正在重连10秒后还未连接就是是链接失败");
        Observable.timer(1L, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {

                    public final void accept(Long it) {
                        MqttWrapService.start(context);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
