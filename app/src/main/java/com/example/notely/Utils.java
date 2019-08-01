package com.example.notely;

import android.app.Activity;
import android.content.Intent;

public class Utils {

    private static int sTheme;
    public final static int THEME_RS = 0;
    public final static int THEME_RD = 1;
    public final static int THEME_RL = 2;
    public final static int THEME_SSS = 3;
    public final static int THEME_SSD = 4;
    public final static int THEME_SSL = 5;
    public final static int THEME_DS = 6;
    public final static int THEME_DD = 7;
    public final static int THEME_DL = 8;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
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
        }
    }


}
