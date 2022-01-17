package com.prography.sw.aloocustomer.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.ui.activity.LoginActivity;

import java.util.Locale;

public class AppUtils {
    private static final String TAG = "AppUtils";


    public static RequestManager initGlide(Context context) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground);
        return Glide.with(context)
                .setDefaultRequestOptions(requestOptions);
    }

    public static void unauthorized(Activity activity) {
        SharedPreferencesHelper.setUserToken(activity, "");
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public static String setTime(Long time) {
        Locale languageTag = Locale.forLanguageTag("en");
        TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(languageTag).build();
        return TimeAgo.using(time, messages);
    }

    //
//    public static Configuration initializeSelectedLanguage(Context context, String userLanguage) {
//        // Create a new Locale object
//        Locale locale = new Locale(userLanguage, "MA");
//        Locale.setDefault(locale);
//        // Create a new configuration object
//        Configuration config = new Configuration();
//        // Set the locale of the new configuration
//        config.locale = locale;
//        // Update the configuration of the App context
//        context.getResources().updateConfiguration(
//                config,
//                context.getResources().getDisplayMetrics()
//        );
//
//        return config;
//    }
//
//    public static void minimizeApp(Context context) {
//        ((Activity) context).moveTaskToBack(true);
//    }
//
//    public static boolean isGPSEnabled(Context context) {
//        LocationManager locationManager = (LocationManager)
//                context.getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//    }
//
//
//    public static String currencyFormat(double amount) {
//        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
//        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
//        symbols.setCurrencySymbol(""); // Don't use null.
//        formatter.setDecimalFormatSymbols(symbols);
//        return formatter.format(amount);
//    }
//
//
//    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {
//
//        if (tv.getTag() == null) {
//            tv.setTag(tv.getText());
//        }
//        ViewTreeObserver vto = tv.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//                String text;
//                int lineEndIndex;
//                ViewTreeObserver obs = tv.getViewTreeObserver();
//                obs.removeGlobalOnLayoutListener(this);
//                if (maxLine == 0) {
//                    lineEndIndex = tv.getLayout().getLineEnd(0);
//                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " ";
//                    tv.setText(text);
//                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
//                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
//                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() - 1) + " ";
//                    tv.setText(Html.fromHtml(text + "<font color='#ec0085'> <u>" + expandText + "</u></font>"));
//                } else {
//                    try {
//                        lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
//                        text = tv.getText().subSequence(0, lineEndIndex) + " ";
//                    } catch (Exception e) {
//                        text = tv.getText() + "";
//                    }
//                    tv.setText(text);
//
//                }
//            }
//        });
//    }
//
//
//    public static void shareApp(Context context) {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT,
//                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
//        sendIntent.setType("text/plain");
//        context.startActivity(sendIntent);
//    }
//
////    public static void shareItem(Context context, String name, int id) {
////        Intent sharingIntent = new Intent();
////        sharingIntent.setType("text/plain");
////        sharingIntent.putExtra(Intent.EXTRA_TITLE, name);
////        String shareLink = String.format(Locale.getDefault(), "%s %s %s %s",
////                context.getString(R.string.check_text),
////                name, context.getString(R.string.at_text),
////                "http://lghawees.advancedchoices.xyz/view/" + id);
////        sharingIntent.setAction(Intent.ACTION_SEND);
////        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareLink);
////        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
////        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share_via_text)));
////    }
//
//    public static void openWebSite(Context context, String url) {
//        try {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            context.startActivity(browserIntent);
//        } catch (Exception e) {
//            Toast.makeText(context, context.getString(R.string.cant_open_url), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    public static double distance(double lat1, double lon1, double lat2, double lon2) {
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1))
//                * Math.sin(deg2rad(lat2))
//                + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        return (dist);
//    }
//
//    private static double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private static double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }
//
//
//    public static String convertTimeTo12Scheme(String time) {
//        try {
//            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
//            final Date dateObj = sdf.parse(time);
//            time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(dateObj);
//        } catch (final ParseException e) {
//            e.printStackTrace();
//        }
//        return time;
//    }
//
//    public static void copyText(Context context, String text, String label) {
//        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData clip = ClipData.newPlainText(label, text);
//        Toast.makeText(context, label, Toast.LENGTH_SHORT).show();
//        if (clipboard != null) clipboard.setPrimaryClip(clip);
//    }
//
//    public static String convertDate(String serverDate) {
//        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//        try {
//            final Date dateObj = sdf.parse(serverDate);
//            serverDate = new SimpleDateFormat("dd MMM yyyy, hh:mm aa ", Locale.getDefault()).format(dateObj);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return serverDate;
//    }
//
//    public static String convertToServerDate(String date) {
//        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.ENGLISH);
//        try {
//            final Date dateObj = sdf.parse(date);
//            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(dateObj);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
//
//    public static void removeUserSession(Activity activity) {
//        SharedPrefs.clearCache(activity);
//        Intent intent = new Intent(activity, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        activity.startActivity(intent);
//        activity.finish();
//    }
//
//    public static String getTimeFromHourMinute(int hourOfDay, int minute) {
//        Calendar datetime = Calendar.getInstance();
//        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        datetime.set(Calendar.MINUTE, minute);
//        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
//        return formatter.format(datetime.getTime());
//    }
//
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(TAG, "isConnectedToInternet: " + (isWifiConn && isMobileConn));
        return (isWifiConn && isMobileConn);
    }

//    public static void setClickableAnimation(Context context, View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            TypedValue outValue = new TypedValue();
//            context.getTheme().resolveAttribute(
//                    android.R.attr.selectableItemBackground, outValue, true);
//            view.setForeground(getDrawable(context, outValue.resourceId));
////            view.setBackgroundResource(outValue.resourceId);
//        }
//    }
//
//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) activity.getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        if (inputMethodManager.isAcceptingText()) {
//            inputMethodManager.hideSoftInputFromWindow(
//                    activity.getCurrentFocus().getWindowToken(),
//                    0
//            );
//        }
//    }
//
//    public static void openMaps(Context context, String lat, String lng) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng));
//            context.startActivity(intent);
//        } catch (Exception e) {
//            openWebSite(context, "http://maps.google.com/maps?daddr=" + lat + "," + lng);
//        }
//    }
//
//
//    public static void openTwitterIntent(Context context, String url) {
//        try {
//            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.twitter.android", 0);
//            if (applicationInfo.enabled) {
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://" + url)));
//            }
//        } catch (Exception e) {
//            openWebSite(context, url);
//        }
//    }
//
//    public static void openFacebookIntent(Context context, String url) {
//        try {
//            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
//            if (applicationInfo.enabled) {
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url)));
//            }
//        } catch (Exception e) {
//            openWebSite(context, url);
//        }
//    }
//
//    public static void openYoutubeIntent(Context context, String url) {
//        Intent youtubeIntent = null;
//        try {
//            youtubeIntent = new Intent(Intent.ACTION_VIEW);
//            youtubeIntent.setPackage("com.google.android.youtube");
//            youtubeIntent.setData(Uri.parse(url));
//            context.startActivity(youtubeIntent);
//        } catch (Exception e) {
//            openWebSite(context, url);
//        }
//    }
//
//    public static void openGMailIntent(Context context, String email) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(email));
//            intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//            intent.putExtra(Intent.EXTRA_SUBJECT, "your subject goes here...");
//            intent.putExtra(Intent.EXTRA_TEXT, "Your message content goes here...");
//            context.startActivity(intent);
//        } catch (Exception e) {
//            copyText(context, email, context.getString(R.string.email_copied));
//        }
//    }

//    public static void openCallIntent(Context context, String phone) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:" + phone));
//            context.startActivity(intent);
//        } catch (Exception e) {
//            Toast.makeText(context, context.getString(R.string.unable_to_make_call), Toast.LENGTH_SHORT).show();
//        }
//    }
}
