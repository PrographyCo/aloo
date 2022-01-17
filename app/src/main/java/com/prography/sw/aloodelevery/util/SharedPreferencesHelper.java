package com.prography.sw.aloodelevery.util;

import static com.prography.sw.aloodelevery.util.Constants.ACCEPT_LANGUAGE;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.prography.sw.aloodelevery.R;


public class SharedPreferencesHelper {

    private SharedPreferencesHelper() {
    }

    private static SharedPreferences getSharedPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    /*<---------------Token---------------->*/
    public static String getUserToken(Context context) {
        return getSharedPref(context).getString(context.getString(R.string.TOKEN), "");
    }

    public static void setUserToken(Context context, String token) {
        getSharedPref(context).edit().putString(context.getString(R.string.TOKEN), token).apply();
    }

    /*<---------------DeviceToken---------------->*/
    public static boolean getFirstTime(Context context) {
        return getSharedPref(context).getBoolean(context.getString(R.string.FIRST_TIME), true);
    }

    public static void setFirstTime(Context context, boolean firstTime) {
        getSharedPref(context).edit().putBoolean(context.getString(R.string.FIRST_TIME), firstTime).apply();
    }

    /*<---------------clearCache---------------->*/
    public static void clearCache(Context context) {
        getSharedPref(context).edit().clear().apply();
    }

    /*<---------------AppLanguage---------------->*/
    public static void setAppLanguage(Context context, String appLanguge) {
        getSharedPref(context)
                .edit().putString(ACCEPT_LANGUAGE, appLanguge).apply();
    }

    public static String getAppLanguage(Context context) {
        return getSharedPref(context)
                .getString(ACCEPT_LANGUAGE, "en");

    }

    /*<---------------setSwitchLang---------------->*/
    public static void setSwitchLang(Context context, boolean appLanguge) {
        getSharedPref(context)
                .edit().putBoolean("SwitchLang", appLanguge).apply();
    }

    public static boolean getSwitchLang(Context context) {
        return getSharedPref(context)
                .getBoolean("SwitchLang", false);

    }

}
