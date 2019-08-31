package com.iterationtechnology.jalarambook.model;

import com.google.gson.annotations.SerializedName;

public class CartTotal {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("cart_total")
    private String cart_total;
    @SerializedName("cart_total_weight")
    private String cart_total_weight;
    @SerializedName("shippingprice")
    private String shippingprice;

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

    public String getCart_total() {
        return cart_total;
    }

    public void setCart_total(String cart_total) {
        this.cart_total = cart_total;
    }

    public String getCart_total_weight() {
        return cart_total_weight;
    }

    public void setCart_total_weight(String cart_total_weight) {
        this.cart_total_weight = cart_total_weight;
    }

    public String getShippingprice() {
        return shippingprice;
    }

    public void setShippingprice(String shippingprice) {
        this.shippingprice = shippingprice;
    }
}
