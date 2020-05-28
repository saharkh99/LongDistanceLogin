package com.example.loginapp.Login;

import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("username")
    private  String username;
    @SerializedName("passwords")
    private String passwords;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }
}
