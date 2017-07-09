package com.example.p.cityguide;

import java.io.Serializable;

/**
 * Created by P on 03-01-2017.
 */
public class BusinessData implements Serializable {

    String businessId;
    String name;
    String abs;
    String address;
    String mobile;
    String lat;
    String lng;
    String website;
    String city;
    String profilephoto;

    public BusinessData(String businessId, String name, String abs, String address, String mobile, String lat, String lng, String website, String city, String profilephoto, String[] displayphoto, String mon, String sat) {
        this.businessId = businessId;
        this.name = name;
        this.abs = abs;
        this.address = address;
        this.mobile = mobile;
        this.lat = lat;
        this.lng = lng;
        this.website = website;
        this.city = city;
        this.profilephoto = profilephoto;
        this.displayphoto = displayphoto;
        this.mon = mon;
        this.sat = sat;
    }

    String displayphoto[];


    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String[] getDisplayphoto() {
        return displayphoto;
    }

    public void setDisplayphoto(String[] displayphoto) {
        this.displayphoto = displayphoto;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    String mon;
    String sat;


}
