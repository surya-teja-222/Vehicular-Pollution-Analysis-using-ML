package com.pollutionmonitor.helperclass;

public class userDetails {
    String Uid , Name , Email , Password , Mobile  ;

    public userDetails() {
    }

    public userDetails(String uid, String name, String email, String password, String mobile) {
        Uid = uid;
        Name = name;
        Email = email;
        Password = password;
        Mobile = mobile;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
