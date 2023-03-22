package com.opgavedb_1.entities;

//Mikkel

public class User {

    private int id;
    private String email;
    private String phonenumber;
    private String firstname;
    private String lastname;
    private String address;
    private int pincode;
    private int team_id;
    private int user_type_id;
    private String team_title;

    public User(int id, String email, String phonenumber, String firstname, String lastname, String address, String team_title, int team_id) {
        this.id = id;
        this.email = email;
        this.phonenumber = phonenumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.team_title = team_title;
        this.team_id = team_id;
    }

    public User(String email, String phonenumber, String firstname, String lastname, String address, int team_id) {
        this.email = email;
        this.phonenumber = phonenumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.team_id = team_id;
    }

    //login constructor
    public User(int id, String email, int pincode, int user_type_id) {
        this.id = id;
        this.email = email;
        this.pincode = pincode;
        this.user_type_id = user_type_id;
    }

    //instructor constructor
    public User(int id, String email, String phonenumber, String firstname, String lastname, String address) {
        this.id = id;
        this.email = email;
        this.phonenumber = phonenumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }
    public User(String email, String phonenumber, String firstname, String lastname, String address) {
        this.email = email;
        this.phonenumber = phonenumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(int user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getTeam_title() {
        return team_title;
    }

    public void setTeam_title(String team_title) {
        this.team_title = team_title;
    }
}
