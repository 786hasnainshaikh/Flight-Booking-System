package com.user;


import java.sql.Connection;
import java.sql.DriverManager;

public class user {

   private String passengerName;
   private String contactNumber;
   private String email;
   private String password;

    public user(String passengerName, String contactNumber, String email, String password) {
        this.passengerName = passengerName;
        this.contactNumber = contactNumber;
        this.email=email;
        this.password=password;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

}
