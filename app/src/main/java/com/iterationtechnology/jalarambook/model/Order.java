package com.iterationtechnology.jalarambook.model;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("invoice_no")
    private String invoice_no;
    @SerializedName("order_date")
    private String order_date;
    @SerializedName("total")
    private String total;
    @SerializedName("shipping_id")
    private String shipping_id;
    @SerializedName("order_status")
    private String order_status;

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
