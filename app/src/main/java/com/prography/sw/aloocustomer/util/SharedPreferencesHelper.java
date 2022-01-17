package com.prography.sw.aloocustomer.util;

import static com.prography.sw.aloocustomer.util.Constants.ACCEPT_LANGUAGE;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.prography.sw.aloocustomer.R;


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

    /*<---------------itemCard---------------->*/
    public static int getCardItem(Context context) {
        return getSharedPref(context).getInt(context.getString(R.string.CARDITEM), 0);
    }

    public static void setCardItem(Context context, int cardItem) {
        getSharedPref(context).edit().putInt(context.getString(R.string.CARDITEM), cardItem).apply();
    }

    /*<---------------itemCard---------------->*/
    public static int getVendorId(Context context) {
        return getSharedPref(context).getInt(context.getString(R.string.VENDORID), 0);
    }

    public static void setVendorId(Context context, int cardItem) {
        getSharedPref(context).edit().putInt(context.getString(R.string.VENDORID), cardItem).apply();
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
/*<---------------lat---------------->*/
    public static void setLat(Context context, String lat) {
        getSharedPref(context)
                .edit().putString("lat",lat ).apply();
    }

    public static String getLat(Context context) {
        return getSharedPref(context)
                .getString("lat", "");

    }

    /*<---------------lon---------------->*/
    public static void setLon(Context context, String lon) {
        getSharedPref(context)
                .edit().putString("lon", lon).apply();
    }

    public static String getLon(Context context) {
        return getSharedPref(context)
                .getString("lon", "");

    }

    /*<---------------id---------------->*/
    public static void setId(Context context, int id) {
        getSharedPref(context)
                .edit().putInt("id", id).apply();
    }

    public static int getId(Context context) {
        return getSharedPref(context)
                .getInt("id", -1);

    }


}
