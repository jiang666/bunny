package com.example.proshine001.webapplication.mqtt;

import java.util.Map;

public class MqttEvent {
    public static final String SERVICE_CONTROL = "servicecontrol";
    public static final String OPERATION_INFOMATION = "INFOMATION";
    public static final String OPERATION_SETVOLUME = "SETVOLUME";
    public static final String OPERATION_SUSPEND = "SUSPEND";
    public static final String OPERATION_WAKE = "WAKE";
    public static final String OPERATION_REBOOT = "REBOOT";
    public static final String OPERATION_SNAPSHOT = "SNAPSHOT";
    public static final String OPERATION_DOWNLOAD = "DOWNLOAD";
    public static final String OPERATION_PLAY = "PLAY";
    public static final String OPERATION_RENAME = "RENAME";
    public static final String OPERATION_SCHEDULE_SWITCH = "SCHEDULE_SWITCH";
    public static final String OPERATION_UPGRADE_APK = "UPGRADE_APK";
    public static final String OPERATION_UPDATE_SETTING = "UPDATE_SETTING";
    public static final String OPERATION_UPDATE_PLAYTASK = "UPDATE_PLAYTASK";
    public static final String OPERATION_STANDBY ="STANDBY";
    public static final String OPERATION_CLEAR_TASK ="CLEAR_TASK";

    //当设备名为NULL的时候，请求重新发送设备信息
    public static final String OPERATION_RESET_DEVICE_INFO = "RESET_DEVICE_INFO";
    /**
     * 针对单终端, 通知终端后台更新乐分配给它的APP/Firmware/Task etc
     */






    public static final String OPERATION_UPDATE_ASSIGNED = "UPDATE_ASSIGNED";

    // 在收到Operation后, 终端通过 ACTION_KEY 从MAP列表中拿出对应的action值, 来完成特定操作
    public static final String ACTION_KEY       = "action";
    // 终端更新操作列表
    // 如下几种 action 会放到 map 中, 通过 "action" 作为key获取详细值
    // 更新轮播节目
    public static final String ACTION_ASSIGNED_LOOP_TASK        = "ACTION_ASSIGNED_LOOP_TASK";
    // 清空录播节目
    public static final String ACTION_CLEAR_LOOP_TASK           = "ACTION_CLEAR_LOOP_TASK";
    // 更新插播节目
    public static final String ACTION_ASSIGNED_PLUGIN_TASK      = "ACTION_ASSIGNED_PLUGIN_TASK";
    // 清空插播节目
    public static final String ACTION_CLEAR_PLUGIN_TASK         = "ACTION_CLEAR_PLUGIN_TASK";
    // 更新互动节目
    public static final String ACTION_ASSIGNED_DEMAND_TASK      = "ACTION_ASSIGNED_DEMAND_TASK";
    // 清空互动节目
    public static final String ACTION_CLEAR_DEMAND_TASK         = "ACTION_CLEAR_DEMAND_TASK";
    // 更新APP版本
    public static final String ACTION_ASSIGNED_APP              = "ACTION_ASSIGNED_APP";
    // 更新固件版本
    public static final String ACTION_ASSIGNED_FIRMWARE         = "ACTION_ASSIGNED_FIRMWARE";
    // 更新配置版本
    public static final String ACTION_ASSIGNED_CONFIG           = "ACTION_ASSIGNED_CONFIG";
    // 更新字幕版本
    public static final String ACTION_ASSIGNED_SUBTITLE         = "ACTION_ASSIGNED_SUBTILE";
    // 更
    public static final String ACTION_LOOPTASK_UPDATED         = "ACTION_LOOPTASK_UPDATED";




    private String id;
    private String operation;//操作:音量cmd
    private String service;// 服务:控制
    private Map<String,String> map;// 音量+1  {"音量":"10","音量+":"1"}

    public String getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getService() {
        return service;
    }

    public Map<String,String>  getMap() {
        return map;
    }
}
