package com.android.aksiem.eizzy.app;

import com.android.aksiem.eizzy.vo.Store;
import com.android.aksiem.eizzy.vo.StoreManager;
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

    public static class ManagerLoggedIn {

        public static final String MANAGER_LOGGED_IN_CKECK_KEY = "eizzy_manager_logged_in_check";
        public static final String MANAGER_DETAILS_KEY = "eizzy_manager_key";

        public static boolean isManagerLoggedIn(AppPrefManager prefManager) {
            return prefManager.getBoolean(MANAGER_LOGGED_IN_CKECK_KEY, false);
        }

        public static void setManagerLoggedIn(AppPrefManager prefManager, boolean loggedIn) {
            prefManager.setPref(MANAGER_LOGGED_IN_CKECK_KEY, loggedIn);
        }

        public static StoreManager getManagerDetails(AppPrefManager prefManager) {
            return new Gson().fromJson(prefManager.getString(MANAGER_DETAILS_KEY),
                    StoreManager.class);
        }

        public static void setManagerDetails(AppPrefManager prefManager, StoreManager manager) {
            prefManager.setPref(MANAGER_DETAILS_KEY, new Gson().toJson(manager,
                    StoreManager.class));
        }
    }

}
