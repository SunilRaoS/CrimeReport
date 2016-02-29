package com.happiestminds.civiccop.model.data;

/**
 * Created by sakthi on 18-01-2016.
 */
public class User {

    private String fullName;
    private int primaryNumber;
    private int secondaryNumber;
    private String gender;
    private String uid;
    private String email;
    private String address;

    public String getFullName() {
        return fullName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(int primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public int getSecondaryNumber() {
        return secondaryNumber;
    }

    public void setSecondaryNumber(int secondaryNumber) {
        this.secondaryNumber = secondaryNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
