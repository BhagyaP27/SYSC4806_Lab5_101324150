package org.example;

import jakarta.persistence.*;

@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String phoneNumber;
    private String address;

    //JPA requires a no argument constructor
    public BuddyInfo() {}

    public BuddyInfo(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name;}
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {this.address = address;}

    @Override
    public String toString() {
        return name + " " + phoneNumber + " " + address;
    }
}
