package com.example.boardgames.Model;

public class User {
    private String firstname, lastname, state, country, lastLogin, yearRegistered;

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setYearRegistered(String yearRegistered) {
        this.yearRegistered = yearRegistered;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getYearRegistered() {
        return yearRegistered;
    }
}
