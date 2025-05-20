package com.lanji.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import java.util.Locale;


public class Utils {
    public static String ANDROID_ID;
    public static String countryCode;
    public static double lastPrice;
    public static String ticker;
    public static String name;
    public static final String PIRCE_privacy="https://stoxscope.top/privacy-policy";

    public static final String PRODUCT_ID="lanjing_ai_pro";

    public static String POPU_SHOW = "popup_shown";
    public static String Q_START = "quiz_start";
    public static String Q_END = "quiz_completed";
    public static String cta_line_click = "cta_line_click";
        public static String click_main_dialog = "stock_main_dialog_click";
    public static String click_main_banner = "picchatbox_main_banner_click";
    public  static String click_image = "picchatbox_image_click";
    public static String click_sound = "picchatbox_sound_click";
    public static String click_seeting = "picchatbox_setting_click";


    public static final String GOOGLE_PRODUCT_WEEK="google_product_week";
    public static final String GOOGLE_PRODUCT_MONTH="google_product_month";
    public static final String GOOGLE_PRODUCT_QUARTERLY="google_product_quarterly";
    public static final String GOOGLE_PRODUCT_YEAR="google_product_year";

    public static final String GOOGLE_PRODUCT_WEEK_PRICE="$2.99";
    public static final String GOOGLE_PRODUCT_MONTH_PRICE="$9.99";
    public static final String GOOGLE_PRODUCT_QUARTERLY_PRICE="$24.99";
    public static final String GOOGLE_PRODUCT_YEAR_PRICE="$89.99";


    public static void onLanguageChange(Context context) {
        String language = Utils.getLaun(context);
        Locale locale;
        if ("zh".equals(language)) {
            locale = Locale.CHINESE;
        } else if ("de".equals(language)) {
            locale = Locale.GERMAN;
        }else if ("es".equals(language)) {
            locale = new Locale("es", "");
        } else if ("pt".equals(language)) {
            locale = new Locale("pt", "");
        } else if ("fr".equals(language)) {
            locale = new Locale("fr", "");
        } else if ("it".equals(language)) {
            locale = new Locale("it", "");
        } else if ("ja".equals(language)) {
            locale = new Locale("ja", "");
        }  else if ("ko".equals(language)) {
            locale = new Locale("ko", "");
        }  else if ("en".equals(language)) {
            locale = new Locale("en", "");
        } else {
            locale =  Locale.getDefault();
        }
        Locale.setDefault(locale);
        Utils.createConfiguration(context, locale);
    }

    public static void createConfiguration(Context context, Locale locale) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.setLocale(locale);
            resources.updateConfiguration(config, dm);
        }
    }

    public static Context getLLL(Context cc){
        String language =getLaun(cc);
        Configuration config = cc.getResources().getConfiguration();
        config.setLocale(new Locale(language));
        Context context = cc.createConfigurationContext(config);
       return context;
    }

    public static void saveLaun(Context context, String laun) {
        SharedPreferences sp = context.getSharedPreferences("STOCK_LANJIN_LAUN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("stock_view_laun", laun);
        editor.commit();
    }

    public static String getLaun(Context context) {
        SharedPreferences sp = context.getSharedPreferences("STOCK_LANJIN_LAUN", Context.MODE_PRIVATE);
        return sp.getString("stock_view_laun",  Locale.getDefault().getLanguage());
    }

    public static void setFirstIn(Context context, boolean laun) {
        SharedPreferences sp = context.getSharedPreferences("STOCK_LANJIN_LAUN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("stock_view_first", laun);
        editor.commit();
    }

    public static boolean getFirstIn(Context context) {
        SharedPreferences sp = context.getSharedPreferences("STOCK_LANJIN_LAUN", Context.MODE_PRIVATE);
        return sp.getBoolean("stock_view_first", true);
    }

}
