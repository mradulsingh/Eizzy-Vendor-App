package com.android.aksiem.eizzy.app;

import com.android.aksiem.eizzy.vo.Store;
import com.google.gson.Gson;

public class EizzyAppState {

    public static class AccountCreated {

        public static final String ACCOUNT_CREATED_CHECK_KEY = "eizzy_account_created_check";
        public static final String ACCOUNT_DETAILS_KEY = "eizzy_account_key";

        public static boolean isAccountCreated(AppPrefManager manager) {
            return manager.getBoolean(ACCOUNT_CREATED_CHECK_KEY, false);
        }

        public static void setAccountCreated(AppPrefManager manager, boolean accountCreated) {
            manager.setPref(ACCOUNT_CREATED_CHECK_KEY, accountCreated);
        }

        public static Store getBasicAccountDetails(AppPrefManager manager) {
            return new Gson().fromJson(manager.getString(ACCOUNT_DETAILS_KEY), Store.class);
        }

        public static void setBasicAccountDetails(AppPrefManager manager, Store store) {
            manager.setPref(ACCOUNT_DETAILS_KEY, new Gson().toJson(store));
        }

    }

}
