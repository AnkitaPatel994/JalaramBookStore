package com.iterationtechnology.jalarambook.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BestSellingList {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("bestselling")
    private ArrayList<Product> bestsellingList;

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

    public ArrayList<Product> getBestsellingList() {
        return bestsellingList;
    }

    public void setBestsellingList(ArrayList<Product> bestsellingList) {
        this.bestsellingList = bestsellingList;
    }
}
