package com.android.aksiem.eizzy.util;

import android.text.format.DateUtils;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by pdubey on 15/04/18.
 */

public class StringUtils {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String randomString(int len) {
        if (len < 1) return null;
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static long getDayStart(long timestamp) {
        Date date = new Date(timestamp - timestamp % (24 * 60 * 60 * 1000));
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

    public static RequestBody getPlainTextRequestBody(String string) {
        return RequestBody.create(MediaType.parse("text/plain"), string);
    }

    public static String titleize(String source) {
        boolean cap = true;
        char[] out = source.toCharArray();
        int i, len = source.length();
        for (i = 0; i < len; i++) {
            if (Character.isWhitespace(out[i])) {
                cap = true;
                continue;
            }
            if (cap) {
                out[i] = Character.toUpperCase(out[i]);
                cap = false;
            }
        }
        return new String(out);
    }

}
