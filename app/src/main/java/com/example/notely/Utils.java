package com.example.notely;

import android.app.Activity;
import android.content.Intent;

public class Utils {

    public static int currentTextTheme;
    public static String currentColorTheme;
    private static int sTheme;
    public static int currentTheme;

    public final static int THEME_RS = 0;
    public final static int THEME_RD = 1;
    public final static int THEME_RL = 2;
    public final static int THEME_SSS = 3;
    public final static int THEME_SSD = 4;
    public final static int THEME_SSL = 5;
    public final static int THEME_DS = 6;
    public final static int THEME_DD = 7;
    public final static int THEME_DL = 8;

    public final static int THEME_RS_D = 10;
    public final static int THEME_RD_D = 11;
    public final static int THEME_RL_D = 12;
    public final static int THEME_SSS_D = 13;
    public final static int THEME_SSD_D = 14;
    public final static int THEME_SSL_D = 15;
    public final static int THEME_DS_D = 16;
    public final static int THEME_DD_D = 17;
    public final static int THEME_DL_D = 18;


    public static int getCurrentTextTheme() {
        return currentTextTheme;
    }

    public static String getCurrentColorTheme() {
        return currentColorTheme;
    }

    public static int getCurrentTheme(){
        return currentTheme;
    }

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {

        currentTextTheme = sTheme;
        currentTheme = sTheme;

        if (sTheme >= 0 && sTheme <= 8)
            currentColorTheme = "Light";

        if (sTheme >= 10 && sTheme <= 18) {
            currentColorTheme = "Dark";
            currentTextTheme = currentTextTheme - 10;
        }

        System.out.println(sTheme);

        switch (sTheme) {
            default:
            case THEME_RS:
                activity.setTheme(R.style.SmallRoboto);
                break;
            case THEME_RD:
                activity.setTheme(R.style.DefaultRoboto);
                break;
            case THEME_RL:
                activity.setTheme(R.style.LargeRoboto);
                break;
            case THEME_SSS:
                activity.setTheme(R.style.SmallSansSerif);
                break;
            case THEME_SSD:
                activity.setTheme(R.style.DefaultSansSerif);
                break;
            case THEME_SSL:
                activity.setTheme(R.style.LargeSansSerif);
                break;
            case THEME_DS:
                activity.setTheme(R.style.DefaultSmall);
                break;
            case THEME_DD:
                activity.setTheme(R.style.DefaultDefault);
                break;
            case THEME_DL:
                activity.setTheme(R.style.DefaultLarge);
                break;

            case THEME_RS_D:
                activity.setTheme(R.style.SmallRobotoDark);
                break;
            case THEME_RD_D:
                activity.setTheme(R.style.DefaultRobotoDark);
                break;
            case THEME_RL_D:
                activity.setTheme(R.style.LargeRobotoDark);
                break;
            case THEME_SSS_D:
                activity.setTheme(R.style.SmallSansSerifDark);
                break;
            case THEME_SSD_D:
                activity.setTheme(R.style.DefaultSansSerifDark);
                break;
            case THEME_SSL_D:
                activity.setTheme(R.style.LargeSansSerifDark);
                break;
            case THEME_DS_D:
                activity.setTheme(R.style.DefaultSmallDark);
                break;
            case THEME_DD_D:
                activity.setTheme(R.style.DefaultDefaultDark);
                break;
            case THEME_DL_D:
                activity.setTheme(R.style.DefaultLargeDark);
                break;

        }

    }


}
