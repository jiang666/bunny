package com.example.proshine001.webapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by proshine001 on 2018-08-17.
 */

public class SystemInfo {
    /**
     * Get watchdog feed whether choose
     *
     * @param context The application context
     * @return The usb status
     */
    public static String getCardNum(Context context) {
        SharedPreferences pref = context.getSharedPreferences("book", MODE_PRIVATE);
        return pref.getString("cardnum", "000000");
    }

    public static boolean setCardNum(Context context, String cardnum) {
        SharedPreferences sp=context.getSharedPreferences("book",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("cardnum", cardnum);
        editor.commit();
        return true;
    }
}
