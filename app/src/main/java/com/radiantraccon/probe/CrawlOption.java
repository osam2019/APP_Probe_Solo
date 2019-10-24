package com.radiantraccon.probe;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class CrawlOption {
    public static int pagesPerCrawl;
    public static float version = 0.1f;

    public CrawlOption() { }

    public static void loadPreferences(Context context) {
        String filename = context.getPackageName() + "_preferences";
        // PreferenceManager?

        SharedPreferences sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        pagesPerCrawl = sharedPreferences.getInt("pagesPerCrawl", 5);
    }
}
