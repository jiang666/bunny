package com.example.proshine001.webapplication.utils;


import android.content.Context;

import com.example.proshine001.webapplication.bean.ChargeRecordsInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: ListSaveUtils
 * Author: liuan
 * creatTime:2017-08-03 15:30
 * Email:1377093782@qq.com
 */
public class ListSaveUtils {
    /**
     * 数据存放在本地
     *
     * @param tArrayList
     */
    public static void saveStorage2SDCard(List tArrayList, Context fileName) {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        FileInputStream fileInputStream = null;
        String filedir = ResManager.getInstance().getResRootPath(fileName);
        try {
            File file = new File(filedir);
            if (!file.exists()) {// 判断目录是否存在
                file.mkdirs();  //多层目录需要调用mkdirs
            }
            File file1 = new File(filedir+"/sudent");
            fileOutputStream = new FileOutputStream(file1.toString());  //新建一个内容为空的文件
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 获取本地的List数据
     *
     * @return
     */
    public static ArrayList<ChargeRecordsInfo> getStorageEntities(Context fileName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        ArrayList<ChargeRecordsInfo> savedArrayList = new ArrayList<>();
        String filedir = ResManager.getInstance().getResRootPath(fileName);
        try {
            File file = new File(filedir);
            if (!file.exists()) {// 判断目录是否存在
                file.mkdirs();  //多层目录需要调用mkdirs
            }
            File file1 = new File(filedir+"/sudent");
            fileInputStream = new FileInputStream(file1.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedArrayList = (ArrayList<ChargeRecordsInfo>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return savedArrayList;
    }


    /**
     * 获取本地的List数据
     *
     * @return
     */
    public static ArrayList<Integer> getStorageEntitiesSpinner(Context fileName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        ArrayList<Integer> savedArrayList = new ArrayList<>();
        String filedir = ResManager.getInstance().getResRootPath(fileName);
        try {
            File file = new File(filedir);
            if (!file.exists()) {// 判断目录是否存在
                file.mkdirs();  //多层目录需要调用mkdirs
            }
            File file1 = new File(filedir+"/sudent");
            fileInputStream = new FileInputStream(file1.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedArrayList = (ArrayList<Integer>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return savedArrayList;
    }
}

