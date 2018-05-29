package com.android.aksiem.eizzy.app;

import com.android.aksiem.eizzy.util.Logger;

/**
 * Created by napendersingh on 25/03/18.
 */

public enum AppEnv {
    DEV {
        @Override
        public String getBaseURL() {
            return "https://api.instacart-clone.com/";
        }

        @Override
        public String getMqttServerUri() {
            return "tcp://192.168.0.4:1883";
        }

        @Override
        public int getLogLevel() {
            return Logger.VERB;
        }

        @Override
        public boolean isHttpLoggerEnabled() {
            return true;
        }

    },

    PROD {
        @Override
        public String getBaseURL() {
            return "";
        }

        @Override
        public String getMqttServerUri() {
            return "";
        }

        @Override
        public int getLogLevel() {
            return Logger.NONE;
        }

        @Override
        public boolean isHttpLoggerEnabled() {
            return false;
        }

    };

    public abstract String getBaseURL();

    public abstract String getMqttServerUri();

    public abstract int getLogLevel();

    public abstract boolean isHttpLoggerEnabled();

}
