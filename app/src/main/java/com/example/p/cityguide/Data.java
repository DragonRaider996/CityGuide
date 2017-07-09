package com.example.p.cityguide;

/**
 * Created by P on 31-12-2016.
 */
public class Data {

    private String Business;
    private String Category;

    public String getBusiness() {
        return Business;
    }

    public void setBusiness(String business) {
        Business = business;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Data() {
    }

    public Data(String business, String category) {
        Business = business;
        Category = category;
    }
}
