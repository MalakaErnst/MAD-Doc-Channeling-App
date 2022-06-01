package com.tech.hospitalmanagement.Models;

public class DoctorDetails {

    String id;
    String name;
    String age;
    String contact;
    String address;
    String specialist;
    String time;
    String price;


    public DoctorDetails() {
    }

    public DoctorDetails(String id, String name, String age, String contact, String address, String specialist, String time, String price) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.address = address;
        this.specialist = specialist;
        this.time = time;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
