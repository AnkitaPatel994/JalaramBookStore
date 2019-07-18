package com.iterationtechnology.jalarambook.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SliderList {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("slider")
    private ArrayList<Slider> sliderList;

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

    public ArrayList<Slider> getSliderList() {
        return sliderList;
    }

    public void setSliderList(ArrayList<Slider> sliderList) {
        this.sliderList = sliderList;
    }
}
