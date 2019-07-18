package com.iterationtechnology.jalarambook.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductImgList {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("ProductImg")
    private ArrayList<ProductImg> ProductImgList;

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

    public ArrayList<ProductImg> getProductImgList() {
        return ProductImgList;
    }

    public void setProductImgList(ArrayList<ProductImg> productImgList) {
        ProductImgList = productImgList;
    }

}
