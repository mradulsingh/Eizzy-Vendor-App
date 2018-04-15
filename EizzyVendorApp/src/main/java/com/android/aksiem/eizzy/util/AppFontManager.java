package com.android.aksiem.eizzy.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

public class AppFontManager {

    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(
            8);

    public static Typeface getTypeface(Context context, int fontType) {
        Typeface typeface = mTypefaces.get(fontType);
        if (typeface == null) {
            typeface = createTypeface(context, FontType.values()[fontType]);
            mTypefaces.put(fontType, typeface);
        }
        return typeface;
    }

    private static Typeface createTypeface(Context context, FontType fontType)
            throws IllegalArgumentException {
        return Typeface.createFromAsset(context.getAssets(),
                fontType.getFontPath());
    }

    public enum FontType {
        MONTSERRAT_BOLD {
            @Override
            String getFontPath() {
                return "fonts/Montserrat-Bold.ttf";
            }
        },
        MONTSERRAT_LIGHT {
            @Override
            String getFontPath() {
                return "fonts/Montserrat-Light.ttf";
            }
        },
        MONTSERRAT_MEDIUM {
            @Override
            String getFontPath() {
                return "fonts/Montserrat-Medium.ttf";
            }
        },
        MONTSERRAT_REGULAR {
            @Override
            String getFontPath() {
                return "fonts/Montserrat-Regular.ttf";
            }
        },
        ROBOTO_THIN {
            @Override
            String getFontPath() {
                return "fonts/Roboto-Thin.ttf";
            }
        },
        ROBOTO_LIGHT {
            @Override
            String getFontPath() {
                return "fonts/Roboto-Light.ttf";
            }
        },
        ROBOTO_MEDIUM {
            @Override
            String getFontPath() {
                return "fonts/Roboto-Medium.ttf";
            }
        },
        ROBOTO_REGULAR {
            @Override
            String getFontPath() {
                return "fonts/Roboto-Regular.ttf";
            }
        };

        abstract String getFontPath();
    }
}
