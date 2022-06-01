package com.tech.hospitalmanagement.Models;

public class AppointmentDetails {

    String id;
    String doctorName;
    String special;
    String time;
    String price;
    String userName;
    String userNIC;
    String userContact;
    String userAddress;

    public AppointmentDetails() {
    }

    public AppointmentDetails(String id, String doctorName, String special, String time, String price, String userName, String userNIC, String userContact, String userAddress) {
        this.id = id;
        this.doctorName = doctorName;
        this.special = special;
        this.time = time;
        this.price = price;
        this.userName = userName;
        this.userNIC = userNIC;
        this.userContact = userContact;
        this.userAddress = userAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
