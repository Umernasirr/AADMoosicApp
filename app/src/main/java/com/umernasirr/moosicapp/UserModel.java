package com.umernasirr.moosicapp;

public class UserModel {

    public UserModel( String username, String password, String gender, String email) {

        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
    }

    private Integer id;
    private String username;
    private String password;
    private String gender;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
