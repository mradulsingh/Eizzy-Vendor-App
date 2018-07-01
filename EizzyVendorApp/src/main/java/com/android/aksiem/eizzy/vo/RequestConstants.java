package com.android.aksiem.eizzy.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestConstants {

    public static class Language {
        public static final String english = "0";
    }

    public static class Platform {
        public static final int android = 2;
    }

    public static class CountryCode {
        public static final String india = "+91";
    }

    public static class OrderItemsList {
        public static final long past = 1;
        public static final long onGoing = 2;
        public static final long all = 3;
    }

    public static class OrderToCustomerStrategy {
        public static final int delivery = 1;
        public static final int pickup = 2;
    }

    public static class OrderSchedule {
        public static final int scheduleDeliveryForNow = 1;
        public static final int scheduleDeliveryForLater = 2;

        public static int getOrderScheduleCode(Boolean isOrderScheduled) {
            if (isOrderScheduled == null || !isOrderScheduled) {
                return scheduleDeliveryForNow;
            }
            return scheduleDeliveryForLater;
        }

        public static String getOrderScheduledTime(Boolean isOrderScheduled, Long scheduledTime,
                                                   long defaultTime) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if (isOrderScheduled && scheduledTime != null) {
                return formatter.format(new Date(scheduledTime)).toString();
            } else {
                return formatter.format(new Date(defaultTime)).toString();
            }
        }

        public static String getOrderCreationTime(long defaultTime) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return formatter.format(new Date(defaultTime)).toString();
        }
    }

    public static class OrderPayment {
        public static final int card = 1;
        public static final int cash = 2;
        public static final int wallet = 3;

        public static int getOrderPaymentTypeCode(Boolean isCashOnDelivery) {
            if (isCashOnDelivery != null && isCashOnDelivery)
                return cash;
            return card;
        }
    }

    public static class StoreType {
        public static final int food = 0;
        public static final int grocery = 1;
        public static final int dailyNeeds = 2;
        public static final int online = 3;

        public static int getStoreType(String storeType) {
            try {
                if (storeType != null && storeType.length() == 1) {
                    int type = Integer.parseInt(storeType);
                    if (type >= 0 && type < 4)
                        return type;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return food;
        }
    }

    public static final String defaultPassword = "eizzypassword";

}
