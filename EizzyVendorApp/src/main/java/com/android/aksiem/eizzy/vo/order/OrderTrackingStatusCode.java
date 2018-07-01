package com.android.aksiem.eizzy.vo.order;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public enum OrderTrackingStatusCode {

    @SerializedName("1")
    NEW_ORDER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 1;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "New Order";
        }
    },

    @SerializedName("2")
    CANCELLED_BY_MANAGER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 2;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Cancelled by Manager";
        }
    },

    @SerializedName("3")
    REJECTED_BY_MANAGER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 3;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Rejected by Manager";
        }
    },

    @SerializedName("4")
    ACCEPTED_BY_MANAGER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 4;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Accepted by Manager";
        }
    },

    @SerializedName("5")
    ORDER_READY {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 5;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Order Ready";
        }
    },

    @SerializedName("6")
    ORDER_PICKED {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 6;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Order Picked";
        }
    },

    @SerializedName("7")
    ORDER_COMPLETE {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 7;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Order Complete";
        }
    },

    @SerializedName("8")
    ACCEPTED_BY_DRIVER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 8;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Accepted by Driver";
        }
    },

    @SerializedName("9")
    REJECTED_BY_DRIVER {
        @NonNull
        @Override
        public Integer getStatusCode() {
            return 9;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Rejected by Driver";
        }
    },

    @SerializedName("10")
    ENROUTE_TO_STORE {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 10;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Enroute to Store";
        }
    },

    @SerializedName("11")
    AT_STORE {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 11;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "At Store";
        }
    },

    @SerializedName("12")
    ENROUTE_TO_CUSTOMER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 12;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Enroute to Customer";
        }
    },

    @SerializedName("13")
    CUSTOMER_LOCATION {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 13;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "At Customer Location";
        }
    },

    @SerializedName("14")
    DELIVERED {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 14;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Delivered";
        }
    },

    @SerializedName("15")
    ORDER_COMPLETE_DRIVER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 15;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Order Completed";
        }
    },

    @SerializedName("16")
    CANCELLED_BY_CUSTOMER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 16;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Cancelled By Customer";
        }
    },

    @SerializedName("17")
    CANCELLED_BY_DRIVER {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 17;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Cancelled by Driver";
        }
    },

    @SerializedName("18")
    ORDER_DELAYED {
        @NonNull
        @Override
        public Integer getStatusCode() {
            return 18;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Order Delayed";
        }
    },

    @SerializedName("19")
    CENTRAL_DISPATCH {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 19;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Central Dispatch";
        }
    },

    @SerializedName("20")
    ORDER_EXPIRED {

        @NonNull
        @Override
        public Integer getStatusCode() {
            return 20;
        }

        @NonNull
        @Override
        public String getStatusMessage() {
            return "Order Expired";
        }
    };

    @NonNull
    public abstract Integer getStatusCode();

    @NonNull
    public abstract String getStatusMessage();
}
