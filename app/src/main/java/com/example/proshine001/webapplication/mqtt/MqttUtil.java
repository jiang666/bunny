package com.example.proshine001.webapplication.mqtt;

import android.content.Context;

import com.example.proshine001.webapplication.mqtt.enumer.EnumUniformID;
import com.orhanobut.logger.Logger;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/**
 * 单利多余了  方便用
 */
public class MqttUtil {

    private final Context mApplicationContext;
    private IMqttMessageListener messageListener;
    private final ArrayList<WeakReference<MqttCallbackExtended>> mCallbackExtendeds;
    private MqttAndroidClient mqttAndroidClient;
    private MqttClientPersistence mPersistence;
    private boolean isConnect;
    private static MqttUtil defaultInstance;
    public static final String TOPIC = EnumUniformID.BROADCAST_TOPIC;

    /**
     * 构造函数
     * @param mApplicationContext Context 对象
     */
    private MqttUtil(Context mApplicationContext) {
        this.mApplicationContext = mApplicationContext.getApplicationContext();
        this.mCallbackExtendeds = new ArrayList();
    }

    /**
     * 单立模式, 获取Mqtt实例
     * @param mApplicationContext Context 对象
     * @return
     *  MqttUtil 实例
     */
    public static MqttUtil getInstance(Context mApplicationContext) {
        if (defaultInstance == null) {
            defaultInstance = new MqttUtil(mApplicationContext);
        }
        return defaultInstance;
    }

    /**
     * 判断连接状态
     * @return
     *  true: 已连接
     *  false: 未连接
     */
    public boolean isConnect() {
        return this.isConnect;
    }

    private void setConnect(boolean state) {
        this.isConnect = state;
    }

    /**
     * 设置Mqtt回调
     * @param callbackExtendeds MqttCallbackExtended 实例
     */
    public final void setConnectCallback(MqttCallbackExtended callbackExtendeds) {
        Intrinsics.checkParameterIsNotNull(callbackExtendeds, "callbackExtendeds");
        this.mCallbackExtendeds.add(new WeakReference(callbackExtendeds));
    }

    /**
     * 断开连接
     */
    public final void disconnect() {
        try {
            if(this.mqttAndroidClient != null) {
                mqttAndroidClient.close();
            }

            if(this.mqttAndroidClient != null) {
                mqttAndroidClient.disconnect(1000L);
            }

            if(this.mPersistence != null) {
                mPersistence.close();
            }

            this.isConnect = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 初始化Client
     * @return
     */
    private MqttUtil initClient() {
        // ask Android where we can put files
        File myDir = this.mApplicationContext.getExternalFilesDir("MqttConnection");
        if(myDir == null) {
            myDir = this.mApplicationContext.getDir("MqttConnection", 0);
        }

        // 由本地文件缓存更新为内存缓存
        //mPersistence = new MqttDefaultFilePersistence(myDir.getAbsolutePath());
        mPersistence = new MemoryPersistence();
        mqttAndroidClient = new MqttAndroidClient(mApplicationContext,
                "tcp://192.168.0.30:18880",
                "" + 111111,
                mPersistence);


        if(mqttAndroidClient != null) {
            mqttAndroidClient.setCallback(new MqttCallbackExtended() {
                public void connectComplete(boolean reconnect, String serverURI) {
                    MqttUtil.this.isConnect = true;
                    if(reconnect) {
                        MqttUtil.this.addToHistory("Reconnected to : " + serverURI);
                    } else {
                        MqttUtil.this.addToHistory("Connected to: " + serverURI);
                    }

                    MqttUtil.this.reSubscribeToTopic();

                    // 触发回调
                    for (WeakReference<MqttCallbackExtended> weakCallback : mCallbackExtendeds) {
                        MqttCallbackExtended temp = weakCallback.get();
                        if(temp != null) {
                            temp.connectComplete(reconnect, serverURI);
                        }
                    }

                }

                public void connectionLost(Throwable cause) {
                    MqttUtil.this.addToHistory("The Connection was lost.");
                    // 触发回调
                    for (WeakReference<MqttCallbackExtended> weakCallback : mCallbackExtendeds) {
                        MqttCallbackExtended temp = weakCallback.get();
                        if(temp != null) {
                            temp.connectionLost(cause);
                        }
                    }

                    MqttUtil.this.isConnect = false;
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    MqttUtil.this.addToHistory("Incoming message: " + new String(message.getPayload(), Charsets.UTF_8));
                    // 触发监听器
                    if(MqttUtil.this.messageListener != null) {
                        messageListener.messageArrived(topic, message);
                    }

                    // 触发回调
                    for (WeakReference<MqttCallbackExtended> weakCallback : mCallbackExtendeds) {
                        MqttCallbackExtended temp = weakCallback.get();
                        if(temp != null) {
                            temp.messageArrived(topic, message);
                        }
                    }

                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 触发回调
                    for (WeakReference<MqttCallbackExtended> weakCallback : mCallbackExtendeds) {
                        MqttCallbackExtended temp = weakCallback.get();
                        if(temp != null) {
                            temp.deliveryComplete(token);
                        }
                    }

                }
            });
        }

        return this;
    }

    /**
     * Mqtt连接服务器
     */
    public final void connect() {
        this.initClient();
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        // 设置MQTT 后台心跳间隔为10秒, 默认为60
        mqttConnectOptions.setKeepAliveInterval(20);
        if(this.mqttAndroidClient != null) {
            try {
                mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        isConnect = true;
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);

                        try {
                            if(mqttAndroidClient != null) {
                                mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        MqttUtil.this.addToHistory("success to connect to: " + "tcp://192.168.0.30:18880");

                        // 触发回调
                        for (WeakReference<MqttCallbackExtended> weakCallback : mCallbackExtendeds) {
                            MqttCallbackExtended temp = weakCallback.get();
                            if(temp != null) {
                                temp.connectComplete(true, null);
                            }
                        }

                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        MqttUtil.this.isConnect = false;
                        if (exception instanceof MqttException) {
                            MqttException me = (MqttException) exception;
                            if (me.getReasonCode() == MqttException.REASON_CODE_CLIENT_CONNECTED) {
                                Logger.d("重连失败, 原因是MQTT后台还未掉线, 更新状态为已连接!");
                                isConnect = true;
                                // 触发回调
                                for (WeakReference<MqttCallbackExtended> weakCallback : mCallbackExtendeds) {
                                    MqttCallbackExtended temp = weakCallback.get();
                                    if(temp != null) {
                                        temp.connectComplete(true, null);
                                    }
                                    return;
                                }
                            }

                        }
                        Logger.d("连接失败: " + exception);
                        if(asyncActionToken.getException().getReasonCode() == 0) {
                        }


                        // 触发回调
                        for (WeakReference<MqttCallbackExtended> weakCallback : mCallbackExtendeds) {
                            MqttCallbackExtended temp = weakCallback.get();
                            if(temp != null) {
                                temp.connectionLost(null);
                            }
                        }

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }

    private final void reSubscribeToTopic() {
        try {
            if(this.mqttAndroidClient != null) {
                mqttAndroidClient.subscribe("all", 0, this.messageListener);
                mqttAndroidClient.subscribe(TOPIC, 2,messageListener);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 添加Mqtt状态监听器
     * @param messageListener
     */
    public final void subscribe(IMqttMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * 打印Log
     * @param message
     */
    private final void addToHistory(String message) {
        Logger.d(message);
    }

    /**
     * 发布消息
     * @param publishMessage 消息内容
     * @param publishTopic 消息Topic
     */
    public final void publishMessage(String publishMessage, String publishTopic) {
        try {
            MqttMessage msg = new MqttMessage();
            Charset charset = Charsets.UTF_8;
            byte[] bytes = publishMessage.getBytes(charset);
            msg.setPayload(bytes);
            if(this.mqttAndroidClient != null) {
                mqttAndroidClient.publish(publishTopic, msg);
            }

            this.addToHistory("Message Published");

            if(!mqttAndroidClient.isConnected()) {
                this.addToHistory((this.mqttAndroidClient != null ?
                        this.mqttAndroidClient.getBufferedMessageCount() : null)
                        + " messages in buffer.");
            }
        } catch (MqttException ex) {
            System.err.println("Error Publishing: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


}

