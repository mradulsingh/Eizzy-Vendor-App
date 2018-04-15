package com.android.aksiem.eizzy.ui.toolbar;

import java.util.Random;

public class ToolbarMenuUtil {

    private ToolbarMenuUtil() {
    }

    private static final Random rand = new Random();

    public static <T> T generateMenuFrom(T... variants) {
        int index = rand.nextInt(variants.length);
        return variants[index];
    }
}
