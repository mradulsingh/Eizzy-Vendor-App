package com.android.aksiem.eizzy.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.aksiem.eizzy.app.AppGlobal;

import timber.log.Timber;

/*
 * Author :- Napender Singh
 * Description :- Utility class for printing logs with different levels.
 * Date:- 25/03/18.
 */

public class Logger {

    public final static int NONE = 0;
    public final static int WARN = 1;
    public final static int INFO = 2;
    public final static int DEBUG = 3;
    public final static int VERB = 4;

    private static LoggerFacade mLogFacade;

    public static void init() {
        mLogFacade = new LoggerFacade(AppGlobal.APP_ENV.getLogLevel());
    }

    /**
     * Error
     */
    public static void e(@NonNull String message, Object... args) {
        mLogFacade.e(message, args);
    }

    public static void e(Throwable t, @NonNull String message, Object... args) {
        mLogFacade.e(t, message, args);
    }

    /**
     * Warn
     */
    public static void w(@NonNull String message, Object... args) {
        mLogFacade.w(message, args);
    }

    public static void w(Throwable t, @NonNull String message, Object... args) {
        mLogFacade.w(t, message, args);
    }

    /**
     * Info
     */
    public static void i(@NonNull String message, Object... args) {
        mLogFacade.i(message, args);
    }

    public static void i(Throwable t, @NonNull String message, Object... args) {
        mLogFacade.i(t, message, args);
    }

    /**
     * Debug
     */
    public static void d(@NonNull String message, Object... args) {
        mLogFacade.d(message, args);
    }

    public static void d(Throwable t, @NonNull String message, Object... args) {
        mLogFacade.d(t, message, args);
    }

    /**
     * Verbose
     */
    public static void v(@NonNull String message, Object... args) {
        mLogFacade.v(message, args);
    }

    public static void v(Throwable t, @NonNull String message, Object... args) {
        mLogFacade.v(t, message, args);
    }

    public static LoggerFacade tag(String tag) {
        mLogFacade.setTag(tag);
        return mLogFacade;
    }

    public static class LoggerFacade {
        private final static String DEFAULT_TAG = "EzyLog";
        private final ThreadLocal<String> providedTag = new ThreadLocal<>();
        private static int LOG_LEVEL;

        LoggerFacade(int logLevel) {
            this.LOG_LEVEL = logLevel;
            if (LOG_LEVEL > NONE) {
                Timber.plant(new Timber.DebugTree());
            }
        }

        public void e(@NonNull String message, Object... args) {
            if (LOG_LEVEL > NONE)
                Timber.tag(getTag()).e(message, args);
        }

        public void e(Throwable t, @NonNull String message, Object... args) {
            if (LOG_LEVEL > NONE)
                Timber.tag(getTag()).e(t, message, args);
        }

        public void w(@NonNull String message, Object... args) {
            if (LOG_LEVEL >= WARN)
                Timber.tag(getTag()).w(message, args);
        }

        public void w(Throwable t, @NonNull String message, Object... args) {
            if (LOG_LEVEL >= WARN)
                Timber.tag(getTag()).w(t, message, args);
        }

        public void i(@NonNull String message, Object... args) {
            if (LOG_LEVEL >= INFO)
                Timber.tag(getTag()).i(message, args);
        }

        public void i(Throwable t, @NonNull String message, Object... args) {
            if (LOG_LEVEL >= INFO)
                Timber.tag(getTag()).i(t, message, args);
        }

        public void d(@NonNull String message, Object... args) {
            if (LOG_LEVEL >= DEBUG)
                Timber.tag(getTag()).d(message, args);
        }

        public void d(Throwable t, @NonNull String message, Object... args) {
            if (LOG_LEVEL >= DEBUG)
                Timber.tag(getTag()).d(t, message, args);
        }

        public void v(@NonNull String message, Object... args) {
            if (LOG_LEVEL >= VERB)
                Timber.tag(getTag()).v(message, args);
        }

        public void v(Throwable t, @NonNull String message, Object... args) {
            if (LOG_LEVEL >= VERB)
                Timber.tag(getTag()).v(t, message, args);
        }

        @Nullable
        String getTag() {
            String tag = providedTag.get();
            if (tag != null) {
                providedTag.remove();
            } else {
                tag = DEFAULT_TAG;
            }
            return tag;
        }

        public void setTag(String tag) {
            providedTag.set(tag);
        }
    }
}
