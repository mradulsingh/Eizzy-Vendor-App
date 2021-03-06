package com.android.aksiem.eizzy.util;

import android.text.format.DateUtils;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by napendersingh on 02/07/18.
 */

public class TimeUtils {

    public static long getDayStart(long timestamp) {
        Date date = new Date(timestamp - timestamp % (24 * 60 * 60 * 1000));
        return date.getTime() / 1000;
    }

    public static long getDateWeekBefore(long timestamp) {
        Date date = new Date(timestamp - 7 * 24 * 60 * 60 * 1000);
        return date.getTime() / 1000;
    }

    public static long getMonthStart(long timestamp) {
        Date date = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTimeInMillis() / 1000;
    }

    public static long getPreviousMonthStart(long timestamp) {
        Date date = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        return c.getTimeInMillis() / 1000;
    }

    public static long getYearStart(long timestamp) {
        Date date = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, 1);
        return c.getTimeInMillis() / 1000;
    }

    public static String getTimestamp(long timestamp, AppResourceManager resourceManager) {
        String toReturn = null;
        if (DateUtils.isToday(timestamp)) {
            toReturn = resourceManager.getString(R.string.ts_today);
        } else {
            Calendar c = Calendar.getInstance();
            long now = c.getTimeInMillis();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            long midnight = c.getTimeInMillis();
            c.add(Calendar.DAY_OF_MONTH, -1);
            long midnightBefore = c.getTimeInMillis();
            c.add(Calendar.DAY_OF_MONTH, -5);
            long lastWeek = c.getTimeInMillis();
            c.add(Calendar.DAY_OF_MONTH, -13);
            long weekBefore = c.getTimeInMillis();

            if (timestamp < midnight && timestamp > midnightBefore) {
                toReturn = resourceManager.getString(R.string.ts_yesterday);
            } else if (timestamp < midnightBefore && timestamp > lastWeek) {
                toReturn = resourceManager.getString(R.string.ts_thisweek);
            } else if (timestamp < lastWeek && timestamp > weekBefore) {
                toReturn = resourceManager.getString(R.string.ts_lastweek);
            } else {

                c.setTimeInMillis(now);
                Calendar ts = Calendar.getInstance();
                int year = ts.get(Calendar.YEAR);
                String m = ts.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
                String y = ts.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.ENGLISH);
                if (year < c.get(Calendar.YEAR)) {
                    toReturn = String.format("%s, %s", m, y);
                } else {
                    toReturn = String.format("%s", m);
                }

            }
        }
        return toReturn;
    }

}
