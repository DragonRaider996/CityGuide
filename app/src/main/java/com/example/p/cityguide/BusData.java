package com.example.p.cityguide;

/**
 * Created by P on 03-01-2017.
 */
public class BusData {

    String image;
    String businessName;
    String address;
    String id;
    String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public BusData(String id, String name, String add, String img, String contact) {
        image = img;
        businessName = name;
        address = add;
        this.id = id;
        this.contact = contact;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
