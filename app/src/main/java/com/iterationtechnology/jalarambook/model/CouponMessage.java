package com.iterationtechnology.jalarambook.model;

import com.google.gson.annotations.SerializedName;

public class CouponMessage {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("discount_rate")
    private String discount_rate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(String discount_rate) {
        this.discount_rate = discount_rate;
    }
}
