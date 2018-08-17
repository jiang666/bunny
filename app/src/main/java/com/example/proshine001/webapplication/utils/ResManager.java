package com.example.proshine001.webapplication.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * This class for manage layout/video/audio resource
 * Use singleton pattern
 * <p>
 * The project tree like below
 * --------------------------------------------------------------------
 * /mnt/sdcard
 * --> touch [RES_ROOT]
 * --> program [存放节目相关的]
 * --> PlayRes [存放节目素材的目录]
 * --> my_project [PROJECT_ROOT]
 * --> configs.xml [CONFIG_FILE]
 * --> layout [LAYOUT_ROOT]
 * --> configs.xml [MAIN_LAYOUT]
 * --> 1.xml *****
 * --> extras [Third Party Res]
 * --> CloudImageView [Custom view Res]
 * --> Image1.png
 * --> Image2.png
 * -->
 * --> ...
 * --> apks [APK_ROOT]
 */
public class ResManager {

    private static final String TAG = ResManager.class.getSimpleName();

    private static ResManager manager = null;
    private static final String RECORDLOGSAVEPATH = "log";
    /**
     * The default resource directory name
     */
    private static final String RES_DIR_NAME = "touch";

    /**
     * The default program directory name
     */
    private static final String PROGRAM_DIR_NAME = "program";

    /**
     * The default cloud directory name
     */
    private static final String CLOUD_DIR_NAME = "cloud";

    /**
     * 节目素材配置文件名
     */
    private static final String PROGRAM_MATERIAL_NAME   = "material";

    /**
     * 节目布局配置文件名
     */
    private static final String PROGRAM_LAYOUT_NAME = "program";

    /**
     * The default program directory name
     */
    private static final String MATERIAL_DIR_NAME = "PlayRes";

    private static final String PLAY_FILE_NAME  = "play";

    /**
     * The default apk directory name
     */
    private static final String APKS_DIR_NAME = "apks";

    /**
     * The default layout directory name
     */
//    private static final String LAYOUT_DIR_NAME = "layout";
    private static final String SYSTEMSETTINGPATH = "config/systemsetting";

    private static final String SYSTEMSETTINGNAME = "systemsetting.xml";
    /**
     * The project config file name
     */
    public static final String BUNNY_TOUCH_CONFIG_FILE_NAME = "configs.xml";

    public static final String BUNNY_TOUCH_PROGRAM_XML = "dataProgram.xml";


    /**
     * The project main layout file name
     */
    private static final String BUNNY_TOUCH_MAIN_LAYOUT_NAME = "configs.xml";

    /**
     * The default resource zip file name
     */
    private static final String RES_ZIP_NAME = "touch.zip";

    /**
     * The default bunnytouch apk file name
     */
    private static final String BUNNY_TOUCH_APK_NAME = "bunnytouch.apk";

    /**
     * The default bunnydaemon apk file name
     */
    private static final String BUNNY_DAEMON_APK_NAME = "bunnydaemon.apk";

    /**
     * Constructor
     */
    private ResManager() {
    }

    public static ResManager getInstance() {
        if (manager == null)
            manager = new ResManager();
        return manager;
    }

    /**
     * Get sdcard root path
     * Exp: /mnt/sdcard
     *
     * @return The external storage folder
     */
    public String getStorageRoot(Context context) {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Get the download resource directory path
     * Exp: /mnt/sdcard/touch
     *
     * @return The directory path
     */
    public String getResRootPath(Context context) {
        return getStorageRoot(context) + File.separator
                + RES_DIR_NAME;
    }
    /**
     * Get the download resource directory path
     * Exp: /mnt/sdcard/touch/program
     *
     * @return The directory path
     */
    public String getProgramRootPath(Context context) {
        return getResRootPath(context) + File.separator
                + PROGRAM_DIR_NAME;
    }
    /**
     * Get the download resource directory path
     * Exp: /mnt/sdcard/touch/cloud
     *
     * @return The directory path
     */
    public String getCloudRootPath(Context context) {
        return getResRootPath(context) + File.separator
                + CLOUD_DIR_NAME;
    }

    /**
     * 获取特定节目布局配置文件路径
     * @param context Context 对象
     * @param programId 指定节目ID
     * @return
     *  路径: /mnt/sdcard/touch/program/#id/program
     */
    public String getProgramLayoutPath(Context context, String programId) {
        String path = getProgramRootPath(context)
                + File.separator + programId
                + File.separator + PROGRAM_LAYOUT_NAME;
        return path;
    }

    /**
     * 获取特定节目素材配置文件路径
     * @param context Context 对象
     * @param programId 指定节目ID
     * @return
     *  路径: /mnt/sdcard/touch/program/#id/material
     */
    public String getProgramMaterialPath(Context context, String programId) {
        String path = getProgramRootPath(context)
                + File.separator + programId
                + File.separator + PROGRAM_MATERIAL_NAME;
        return path;
    }

    /**
     * Get the download resource directory path
     * Exp: /mnt/sdcard/touch/program
     *
     * @return The directory path
     */
    public String getMaterialRootPath(Context context) {
        return getProgramRootPath(context) + File.separator
                + MATERIAL_DIR_NAME;
    }

    /**
     * 获取节目播放配置文件, 该文件定义当前正在播放的三类节目
     * @param context Context 对象
     * @return
     *  /mnt/sdcard/touch/program/play
     */
    public String getPlayFilePath(Context context) {
        return getProgramRootPath(context) + File.separator + PLAY_FILE_NAME;
    }

    // 学生信息图片存放目录  必须是图片 必须是图片
    public String getStudentCardPath(Context context) {
        return getResRootPath(context) + File.separator + "studentcard" ;
    }

    public String getWebappRoot(Context context) {
        return getResRootPath(context) + File.separator
                + "webapp";
    }
    /*节目gen根路径*/
    public String getMissiontRoot(Context context) {
        return getResRootPath(context) + File.separator + "project";
    }

    // 资源存放目录
    public String getFileResourcePath(Context context) {
        return getResRootPath(context) + File.separator + "fileresource";
    }
    public String getSysConfigPath(Context context) {
        return getResRootPath(context) + File.separator + "sysconfig";
    }
    //根据资源文件名 获取路径
    public String getResFilePathbyName(Context context, String filename) {
        return getFileResourcePath(context) + File.separator + filename;
    }

    //
    public String getReportAliveDataPath(Context context) {
        return getResRootPath(context) + File.separator
                + "ReportAliveData.json";
    }
    // 获取缓存文件夹
    public String getInterfaceCacheDirPath(Context context) {
        return context.getFilesDir().getAbsolutePath()+File.separator+File.separator+"files";
    }
    //接口数据缓存列表
    public String getPreLoadWebInterfaceCachePath(Context context) {
        return getInterfaceCacheDirPath(context)
                + "PreLoadWebInterfaceCache.json";
    }

    /**
     * Get sdcard root path
     * Exp: /mnt/sdcard
     *
     * @return The external storage folder
     */
    private static String getStorageRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getSystemSettingpath() {
        return getStorageRoot() + File.separator
                + SYSTEMSETTINGPATH + File.separator + SYSTEMSETTINGNAME;
    }


    public static String getRecordlogsavepath() {
        return getStorageRoot() + File.separator
                + RECORDLOGSAVEPATH;
    }

    /**
     * Get screen captured image file saved path on local fs
     * Exp:
     * /mnt/sdcard/screencap.png
     *
     * @return The screen captured image file path
     */
    public String getLocalSnapshotSavePath() {
        return "/mnt/sdcard" + File.separator + "screencap.png";
    }

    /**
     * Get screen captured image uploaded path on remote ftp server
     * root directory
     * Exp:
     * snapshots/snapshot.png
     *
     * @return The file path on remote server
     */
    public String getRemoteSnapshotSavePath() {
        return "snapshots/snapshot.png";
    }

    /**
     * /**
     * Get the received resource zip file path
     *
     * @return The file path
     */
    public String getResZipPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + RES_ZIP_NAME;
    }


    /**
     * Get target video raw file Uri from raw folder or external
     * folder
     * raw: Apk internal raw resource folder
     * external: /mnt/sdcard/touch/raw folder
     *
     * @param context The application Context
     * @param path    The file path(Relative to raw/external directory)
     * @return The target file Uri object
     */
    public Uri getVideoUri(Context context, String path) {
        if (path.contains(getResRootPath(context))) {
            return Uri.parse(path);
        } else {
            String full_path = this.getResRootPath(context) + File.separator + path;
            return Uri.parse(full_path);
        }
    }

    /**
     * Get target image file Uri from drawable folder or external
     * folder
     * drawable: Apk internal drawable resource folder
     * external: /mnt/sdcard/touch/drawable folder
     *
     * @param context The application Context
     * @param path    The file path(Relative to drawable/external directory)
     * @return The target image file Uri object
     */
    public Uri getImageUri(Context context, String path) {
        if (path.contains(getResRootPath(context))) {
            return Uri.parse(path);
        } else {
            String full_path = this.getResRootPath(context) + File.separator + path;
            return Uri.parse(full_path);
        }
    }

    /**
     * Get target audio file Uri from drawable folder or external
     * folder
     * drawable: Apk internal raw resource folder
     * external: /mnt/sdcard/touch/raw folder
     *
     * @param context The application Context
     * @param path    The file path(Relative to raw/external directory)
     * @return The target audio file Uri object
     */
    public Uri getAudioUri(Context context, String path) {
        String full_path = this.getResRootPath(context) + File.separator + path;
        return Uri.parse(full_path);
    }
}
