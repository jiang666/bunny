package com.example.proshine001.webapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proshine001.webapplication.adapter.FullyGridLayoutManager;
import com.example.proshine001.webapplication.adapter.RecognitionRecyclerAdapter;
import com.example.proshine001.webapplication.bean.ChargeRecordsInfo;
import com.example.proshine001.webapplication.even.MessageEvent;
import com.example.proshine001.webapplication.mqtt.MqttUtil;
import com.example.proshine001.webapplication.mqtt.MqttWrapService;
import com.example.proshine001.webapplication.utils.InputPasswordDialog;
import com.example.proshine001.webapplication.utils.ListSaveUtils;
import com.example.proshine001.webapplication.utils.SystemInfo;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity{

    private TextView tv_content;
    private ArrayList<ChargeRecordsInfo> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecognitionRecyclerAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_content = (TextView)findViewById(R.id.tv_content);
        recyclerView = (RecyclerView)findViewById(R.id.rv_recognition);
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitApp();
                Logger.e("---","点击");
            }
        });
        list = ListSaveUtils.getStorageEntities(this);
        recycleAdapter= new RecognitionRecyclerAdapter(this , list );
        layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        layoutManager.setOrientation(FullyGridLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        Observable.interval(200, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            public final void accept(Long code) {
                if(!MqttUtil.getInstance(MainActivity.this).isConnect()&& hasNetwork(MainActivity.this)){
                    MqttWrapService.start(MainActivity.this);
                    Logger.d("mqtt 重连");
                }
            }

        });
        EventBus.getDefault().register(this);
    }

    public static boolean hasNetwork(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Logger.d("message is " + event.getMessage());
        // 更新界面
        String cardnum = event.getMessage();
        Logger.d("Receiver", cardnum);
        ChargeRecordsInfo chargeRecordsInfo = new ChargeRecordsInfo();
        chargeRecordsInfo.beaginLevel = cardnum;
        list.add(chargeRecordsInfo);
        recycleAdapter.notifyDataSetChanged();
        ListSaveUtils.saveStorage2SDCard(list,MainActivity.this);
    }
    private void quitApp() {
        if (this.isDestroyed()) {
            Logger.d("窗口已关闭, 退出窗口不显示!");
            return;
        }
        final InputPasswordDialog dialog = new InputPasswordDialog(this,"绑定卡号",
                "", new InputPasswordDialog.OnInputDialogListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onConfirm(String text) {
                if (text == null) {
                    Toast.makeText(MainActivity.this, "账号不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    MqttWrapService.reStart(MainActivity.this);
                    SystemInfo.setCardNum(MainActivity.this,text);
                }
            }
        });
        dialog.showDialog();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run()  {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                t.cancel();
            }
        }, 50000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
