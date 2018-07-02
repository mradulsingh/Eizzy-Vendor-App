package com.android.aksiem.eizzy.util;

import java.security.SecureRandom;

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
