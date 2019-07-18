package com.iterationtechnology.jalarambook.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Cart {

    @SerializedName("id")
    private String id;
    @SerializedName("pro_id")
    private String pro_id;
    @SerializedName("cate_id")
    private String cate_id;
    @SerializedName("pro_title")
    private String pro_title;
    @SerializedName("author_id")
    private String author_id;
    @SerializedName("author_name")
    private String author_name;
    @SerializedName("pro_oprice")
    private String pro_oprice;
    @SerializedName("pro_discount")
    private String pro_discount;
    @SerializedName("pro_price")
    private String pro_price;
    @SerializedName("pro_desc")
    private String pro_desc;
    @SerializedName("pro_quantity")
    private String pro_quantity;
    @SerializedName("cart_pro_quantity")
    private String cart_pro_quantity;
    @SerializedName("pro_date")
    private String pro_date;
    @SerializedName("rating")
    private String rating;
    @SerializedName("product_img")
    private String product_img;
    @SerializedName("pro_size")
    private ArrayList<ProductSize> pro_size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getPro_title() {
        return pro_title;
    }

    public void setPro_title(String pro_title) {
        this.pro_title = pro_title;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getPro_oprice() {
        return pro_oprice;
    }

    public void setPro_oprice(String pro_oprice) {
        this.pro_oprice = pro_oprice;
    }

    public String getPro_discount() {
        return pro_discount;
    }

    public void setPro_discount(String pro_discount) {
        this.pro_discount = pro_discount;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getPro_desc() {
        return pro_desc;
    }

    public void setPro_desc(String pro_desc) {
        this.pro_desc = pro_desc;
    }

    public String getPro_quantity() {
        return pro_quantity;
    }

    public void setPro_quantity(String pro_quantity) {
        this.pro_quantity = pro_quantity;
    }

    public String getCart_pro_quantity() {
        return cart_pro_quantity;
    }

    public void setCart_pro_quantity(String cart_pro_quantity) {
        this.cart_pro_quantity = cart_pro_quantity;
    }

    public String getPro_date() {
        return pro_date;
    }

    public void setPro_date(String pro_date) {
        this.pro_date = pro_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public ArrayList<ProductSize> getPro_size() {
        return pro_size;
    }

    public void setPro_size(ArrayList<ProductSize> pro_size) {
        this.pro_size = pro_size;
    }
}
