package com.group08.demo.Entity;

public class Customer {
    String cusName;
    String cusNIC;
    String cusAddress;
    String cusPhone;
    String cusEmail;
    String cusPassword;

    public Customer() {}

    public Customer(String cusname,String cusnic, String cusaddress, String cusphone, String cusemail, String cuspassword) {
        this.cusName = cusname;
        this.cusNIC = cusnic;
        this.cusAddress = cusaddress;
        this.cusPhone = cusphone;
        this.cusEmail = cusemail;
        this.cusPassword = cuspassword;
    }

    public String getCusName() {
        return cusName;
    }
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
    public String getCusNIC() {
        return cusNIC;
    }
    public void setCusNIC(String cusNIC) {
        this.cusNIC = cusNIC;
    }
    public String getCusAddress() {
        return cusAddress;
    }
    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }
    public String getCusPhone() {
        return cusPhone;
    }
    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }
    public String getCusEmail() {
        return cusEmail;
    }
    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }
    public String getCusPassword() {
        return cusPassword;
    }
    public void setCusPassword(String cusPassword) {
        this.cusPassword = cusPassword;
    }

}
