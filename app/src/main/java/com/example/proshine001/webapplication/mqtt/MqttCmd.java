package com.example.proshine001.webapplication.mqtt;




import com.example.proshine001.webapplication.utils.Identities;

import java.util.Map;

/**
 * 前后台消息通信指令
 */
public class MqttCmd {

    /**
     * 消息ID, 唯一标示符
     */
    private String id   = "";
    /**
     * 服务类型
     */
    private String service  = "";
    /**
     * 操作类型
     */
    private String operation    = "";
    /**
     * 携带的参数列表
     */
    private Map params  = null;

    public MqttCmd() {

    }

    public MqttCmd(String id, String service, String operation, Map params) {
        this.id = id;
        this.service = service;
        this.operation = operation;
        this.params = params;
    }

    /**
     * 快捷创建MqttCmd实例
     * @return
     *  MqttCmd 实例
     */
    public static MqttCmd create(String service, String operation) {
        MqttCmd cmd = new MqttCmd(Identities.uuid2(), service, operation, null);
        return cmd;
    }

    /**
     * 快捷创建MqttCmd实例
     * @param params {@link Map} 参数列表
     * @return
     *  MqttCmd 实例
     */
    public static MqttCmd create(String service, String operation, Map params) {
        MqttCmd cmd = new MqttCmd(Identities.uuid2(), service, operation, params);
        return cmd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }
}
